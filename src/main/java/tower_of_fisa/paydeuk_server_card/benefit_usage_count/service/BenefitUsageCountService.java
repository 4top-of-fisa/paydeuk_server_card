// benefitUsageCountServcie

package tower_of_fisa.paydeuk_server_card.benefit_usage_count.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tower_of_fisa.paydeuk_server_card.benefit_condition.repository.BenefitConditionRepository;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionRequest;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionResponse;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitCondition;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.issued_card_token.repository.IssuedCardTokenRepository;

@Service
@RequiredArgsConstructor
public class BenefitUsageCountService {
  private final IssuedCardTokenRepository issuedCardTokenRepository;
  private final RedisTemplate<String, String> redisTemplate;
  private final BenefitConditionRepository benefitConditionRepository;

  /**
   * [카드 조건 조회] 사용자가 해당 카드로 이용한 혜택의 누적 사용 횟수와 할인 금액을 조회합니다.
   *
   * @param request CardConditionRequest - 조건을 조회할 카드 토큰과 조건 ID
   * @return List<BenefitUsageCount> - 조건 ID와 누적 사용 횟수와 할인 금액
   */
  public List<CardConditionResponse> checkCardCondition(CardConditionRequest request) {
    Long issuedCardId =
        issuedCardTokenRepository
            .findIssuedCardIdByCardToken(request.getCardToken())
            .orElseThrow(() -> new NoSuchElementFoundException404(ErrorDefineCode.CARD_NOT_FOUND));

    List<BenefitCondition> conditions =
        benefitConditionRepository.findAllById(List.of(request.getConditionId()));

    List<CardConditionResponse> responses = new ArrayList<>();

    for (BenefitCondition condition : conditions) {
      Long conditionId = condition.getId();
      String redisPrefix =
          isAmountBased(condition.getConditionCategory()) ? "BenefitSum" : "BenefitCount";
      String redisKey = String.format("%s:%d:%d", redisPrefix, issuedCardId, conditionId);

      if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
        continue;
      }

      String valueStr = redisTemplate.opsForValue().get(redisKey);

      int value = Integer.parseInt(valueStr);
      responses.add(new CardConditionResponse(conditionId, value));
    }

    return responses;
  }

  // 카테고리가 금액 기반인지 여부 판단
  private boolean isAmountBased(BenefitConditionCategory category) {
    return category == BenefitConditionCategory.DAILY_DISCOUNT_LIMIT
        || category == BenefitConditionCategory.MONTHLY_DISCOUNT_LIMIT;
  }
}
