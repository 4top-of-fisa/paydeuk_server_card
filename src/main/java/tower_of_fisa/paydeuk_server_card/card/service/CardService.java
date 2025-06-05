package tower_of_fisa.paydeuk_server_card.card.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tower_of_fisa.paydeuk_server_card.benefit.repository.BenefitRepository;
import tower_of_fisa.paydeuk_server_card.benefit_condition.repository.BenefitConditionRepository;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.PaymentRequest;
import tower_of_fisa.paydeuk_server_card.card.dto.PaymentResponse;
import tower_of_fisa.paydeuk_server_card.card.mapper.CardMapper;
import tower_of_fisa.paydeuk_server_card.card.repository.CardRepository;
import tower_of_fisa.paydeuk_server_card.config.redis.RedisService;
import tower_of_fisa.paydeuk_server_card.discount_rate.repository.DiscountRateRepository;
import tower_of_fisa.paydeuk_server_card.domain.entity.*;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.DiscountApplyType;
import tower_of_fisa.paydeuk_server_card.issued_card.repository.IssuedCardRepository;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.repository.PreviousMonthSpendingRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

  private final CardRepository cardRepository;
  private final CardMapper cardMapper;
  private final BenefitRepository benefitRepository;
  private final IssuedCardRepository issuedCardRepository;
  private final PreviousMonthSpendingRepository previousMonthSpendingRepository;
  private final DiscountRateRepository discountRateRepository;
  private final BenefitConditionRepository benefitConditionRepository;
  private final RedisService redisService;

  public List<CardInfoResponse> getAllCardInfo() {
    return cardRepository.findAll().stream().map(cardMapper::toDto).toList();
  }

  public List<CardInfoResponse> getCardInfoByCardCompany(CardCompany cardCompany) {
    return cardRepository.findByCardCompany(cardCompany).stream().map(cardMapper::toDto).toList();
  }

  public PaymentResponse paymentProcessing(PaymentRequest paymentRequest) {

    // 현재 시간 가져오기
    LocalDateTime now = LocalDateTime.now();

    // 원하는 포맷으로 시간을 형식화
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String formattedTime = now.format(formatter);

    long issuedCardId =
        issuedCardRepository.findIssuedCardIdByCardToken(paymentRequest.getCardToken());
    Benefit benefit =
        benefitRepository
            .findByMerchantIdAndCardToken(paymentRequest.getMerchantId(), issuedCardId)
            .orElse(null);
    int previousMonthSpending =
        previousMonthSpendingRepository.findValueByIssuedCardId(issuedCardId).orElse(0);

    // 혜택이 없는 경유 바로 결제 진행
    if (benefit == null) {
      log.info(
          "카드 {}는 가맹점 {}에서 받을 수 있는 혜택이 없습니다. 결제 진행.", issuedCardId, paymentRequest.getMerchantId());
      return new PaymentResponse(formattedTime);
    }

    // 전월 실적에 맞는 할인율을 찾기
    DiscountRate discountRate =
        discountRateRepository
            .findByBenefitAndPreviousMonthSpending(benefit, previousMonthSpending)
            .orElse(null); // 할인율이 없으면 null 반환

    // 할인율이 없을 경우 결제 진행
    if (discountRate == null) {
      log.info(
          "카드 {}에서 전월 실적이 {}이지만, 적용 가능한 할인율이 없습니다. 결제 진행.", issuedCardId, previousMonthSpending);
      return new PaymentResponse(formattedTime);
    }

    // 혜택 추가 조건 처리
    if (Boolean.FALSE.equals(benefit.getHasAdditionalConditions())) {
      log.info("혜택 {}에 추가 조건이 없습니다. 결제 진행.", benefit.getBenefitTitle());
      return new PaymentResponse(formattedTime);
    }

    // 할인 금액 계산
    double discountAmount = calculateDiscountAmount(paymentRequest.getAmount(), discountRate);

    // 혜택 조건에 맞게 Redis에 값 저장
    saveBenefitConditionsToRedis(benefit, issuedCardId, previousMonthSpending, discountAmount);

    // 결제 완료 응답 반환
    return new PaymentResponse(formattedTime);
  }

  private void saveBenefitConditionsToRedis(
      Benefit benefit, long issuedCardId, int previousMonthSpending, double discountAmount) {
    List<BenefitCondition> conditions =
        benefitConditionRepository.findByBenefitIdAndPreviousMonthSpending(
            benefit, previousMonthSpending);

    for (BenefitCondition condition : conditions) {
      if (shouldSaveBenefitToRedis(condition)) {
        String redisKey = buildRedisKey(benefit, issuedCardId, condition);
        long ttl = calculateTTL(condition);

        if (redisService.getValue(redisKey).isEmpty()) {
          log.info("Redis에 값이 없으므로 새로 저장: {}", redisKey);
          saveBenefitToRedis(redisKey, condition, discountAmount, ttl);
        } else {
          log.info("Redis에 값이 있어 기존 값을 갱신: {}", redisKey);
          updateBenefitInRedis(redisKey, condition, discountAmount);
        }
      }
    }
  }

  private boolean shouldSaveBenefitToRedis(BenefitCondition condition) {
    return condition.getConditionCategory() != BenefitConditionCategory.PER_TRANSACTION_LIMIT;
  }

  private String buildRedisKey(Benefit benefit, long issuedCardId, BenefitCondition condition) {
    String benefitName = benefit.getBenefitTitle();
    String dateKey = condition.getConditionCategory().getDateKey(); // YYMMDD or YYMM

    // LimitType에 따라 Redis 키의 접두사를 다르게 설정
    String prefix =
        condition.getConditionCategory().getLimitType() == BenefitConditionCategory.LimitType.COUNT
            ? "BenefitCount" // COUNT일 경우 BenefitCount
            : "BenefitSum"; // LIMIT일 경우 BenefitSum

    return String.format("%s:%d:%s:%s", prefix, issuedCardId, benefitName, dateKey);
  }

  private long calculateTTL(BenefitCondition condition) {
    if (condition.getConditionCategory().getDateCategory()
        == BenefitConditionCategory.DateCategory.DAILY) {
      return redisService.getRemainingSecondsUntilMidnight(); // 자정까지 남은 시간
    } else {
      return redisService.getRemainingSecondsUntilNextMonthFirstDay(); // 다음 달 1일까지 남은 시간
    }
  }

  private void saveBenefitToRedis(
      String redisKey, BenefitCondition condition, double discountAmount, long ttl) {
    if (condition.getConditionCategory().getLimitType()
        == BenefitConditionCategory.LimitType.COUNT) {
      redisService.saveValue(redisKey, "1", ttl, TimeUnit.SECONDS);
    } else {
      redisService.saveValue(redisKey, String.valueOf(discountAmount), ttl, TimeUnit.SECONDS);
    }
  }

  private void updateBenefitInRedis(
      String redisKey, BenefitCondition condition, double discountAmount) {
    if (condition.getConditionCategory().getLimitType()
        == BenefitConditionCategory.LimitType.COUNT) {
      redisService.incrementValue(redisKey);
    } else {
      redisService.addValue(redisKey, discountAmount);
    }
  }

  private double calculateDiscountAmount(int amount, DiscountRate discountRate) {
    double discount = 0;
    if (discountRate.getDiscountApplyType() == DiscountApplyType.RATE) {
      discount = amount * discountRate.getDiscountAmount() / 100.0;
    } else if (discountRate.getDiscountApplyType() == DiscountApplyType.AMOUNT) {
      discount = discountRate.getDiscountAmount();
    }
    return discount;
  }
}
