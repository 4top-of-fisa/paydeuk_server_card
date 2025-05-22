package tower_of_fisa.paydeuk_server_card.benefitUsageCount.service;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tower_of_fisa.paydeuk_server_card.benefitUsageCount.dto.CardConditionRequest;
import tower_of_fisa.paydeuk_server_card.benefitUsageCount.dto.CardConditionResponse;
import tower_of_fisa.paydeuk_server_card.benefitUsageCount.repository.BenefitUsageCountRepository;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitUsageCount;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.issuedCardToken.repository.IssuedCardTokenRepository;

@Service
@RequiredArgsConstructor
public class BenefitUsageCountService {
  private final BenefitUsageCountRepository benefitUsageCountRepository;
  private final IssuedCardTokenRepository issuedCardTokenRepository;

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

    List<BenefitUsageCount> usageCounts =
        benefitUsageCountRepository.findByIssuedCardIdAndConditionIdIn(
            issuedCardId, Arrays.asList(request.getConditionId()));

    return usageCounts.stream()
        .map(
            usageCount ->
                new CardConditionResponse(usageCount.getCondition().getId(), usageCount.getValue()))
        .toList();
  }
}
