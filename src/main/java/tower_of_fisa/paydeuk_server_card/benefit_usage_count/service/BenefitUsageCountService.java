// benefitUsageCountServcie

package tower_of_fisa.paydeuk_server_card.benefit_usage_count.service;

import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tower_of_fisa.paydeuk_server_card.benefit.repository.BenefitRepository;
import tower_of_fisa.paydeuk_server_card.benefit_condition.repository.BenefitConditionRepository;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionRequest;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionResponse;
import tower_of_fisa.paydeuk_server_card.config.redis.RedisService;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitCondition;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.issued_card_token.repository.IssuedCardTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class BenefitUsageCountService {
  private final IssuedCardTokenRepository issuedCardTokenRepository;
  private final BenefitConditionRepository benefitConditionRepository;
  private final BenefitRepository benefitRepository;
  private final RedisService redisService;

  /**
   * [카드 조건 조회] 사용자가 해당 카드로 이용한 혜택의 누적 사용 횟수와 할인 금액을 조회합니다.
   *
   * @param request CardConditionRequest - 조건을 조회할 카드 토큰과 조건 ID
   * @return List<CardConditionResponse> - 조건 ID와 누적 사용 횟수와 할인 금액
   */
  public List<CardConditionResponse> checkCardCondition(CardConditionRequest request) {
    Long issuedCardId =
        issuedCardTokenRepository
            .findIssuedCardIdByCardToken(request.getCardToken())
            .orElseThrow(() -> new NoSuchElementFoundException404(ErrorDefineCode.CARD_NOT_FOUND));

    List<BenefitCondition> conditions =
        benefitConditionRepository.findAllById(List.of(request.getConditionId()));

    return conditions.stream()
        .map(condition -> getConditionResponseIfExists(condition, issuedCardId))
        .flatMap(Optional::stream)
        .toList();
  }

  private Optional<CardConditionResponse> getConditionResponseIfExists(
      BenefitCondition condition, Long issuedCardId) {
    String redisKey =
        buildRedisKey(condition.getConditionCategory(), issuedCardId, condition.getId());
    log.info("카드 조건 사용 조회 redisKey: {}", redisKey);

    if (!redisService.hasKey(redisKey)) {
      return Optional.empty();
    }

    String valueStr = redisService.getValue(redisKey);
    log.info("vlueStr: {}", valueStr);
    if (valueStr == null) {
      return Optional.empty();
    }

    try {
      double value = Double.parseDouble(valueStr);
      int intValue = (int) value;
      log.info("Integer value: {}", intValue);
      return Optional.of(new CardConditionResponse(condition.getId(), intValue));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  private String buildRedisKey(
      BenefitConditionCategory category, Long issuedCardId, Long conditionId) {

    String prefix = category.getPrefix();
    String dateKey = category.getDateKey();

    String benefitName = benefitRepository.findBenefitTitleByBenefitconditionId(conditionId);
    return String.format("%s:%d:%s:%s", prefix, issuedCardId, benefitName, dateKey);
  }
}
