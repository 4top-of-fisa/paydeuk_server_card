package tower_of_fisa.paydeuk_server_card.benefit_usage_count.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tower_of_fisa.paydeuk_server_card.benefit.repository.BenefitRepository;
import tower_of_fisa.paydeuk_server_card.benefit_condition.repository.BenefitConditionRepository;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionRequest;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionResponse;
import tower_of_fisa.paydeuk_server_card.config.redis.RedisService;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitCondition;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.issued_card_token.repository.IssuedCardTokenRepository;

@ExtendWith(MockitoExtension.class)
class BenefitUsageCountServiceTest {

  @InjectMocks private BenefitUsageCountService benefitUsageCountService;

  @Mock private IssuedCardTokenRepository issuedCardTokenRepository;

  @Mock private RedisTemplate<String, String> redisTemplate;

  @Mock private ValueOperations<String, String> valueOperations;

  @Mock private BenefitConditionRepository benefitConditionRepository;

  @Mock private BenefitRepository benefitRepository;

  @Mock private RedisService redisService;

  @Test
  @DisplayName("카드 토큰과 조건 ID로 혜택 사용 횟수 조회 성공")
  void checkCardCondition_success() {
    // given: 발급 카드 ID, 조건 ID, 카드 토큰을 준비하고 각 리포지토리 메서드의 반환값을 설정
    Long issuedCardId = 1L;
    Long conditionId = 10L;
    String cardToken = "token-abc";

    BenefitCondition condition =
        BenefitCondition.builder()
            .id(conditionId)
            .conditionCategory(BenefitConditionCategory.MONTHLY_DISCOUNT_LIMIT)
            .value(10000L)
            .build();

    given(issuedCardTokenRepository.findIssuedCardIdByCardToken(cardToken))
        .willReturn(Optional.of(issuedCardId));
    given(benefitConditionRepository.findAllById(List.of(conditionId)))
        .willReturn(List.of(condition));
    given(benefitRepository.findBenefitTitleByBenefitconditionId(conditionId))
        .willReturn("benefit");
    given(redisService.hasKey(anyString())).willReturn(true);
    given(redisService.getValue(anyString())).willReturn("3");
//    given(redisTemplate.opsForValue()).willReturn(valueOperations);
//    given(valueOperations.get(anyString())).willReturn("3");

    // 카드 조건 요청 생성
    CardConditionRequest request =
        CardConditionRequest.builder()
            .cardToken(cardToken)
            .conditionId(new Long[] {conditionId})
            .build();

    // when: 서비스 메서드 호출
    List<CardConditionResponse> result = benefitUsageCountService.checkCardCondition(request);

    // then: 반환값의 크기, 조건 ID, 사용 횟수가 예상과 일치하는지 검증
    assertEquals(1, result.size());
    assertEquals(conditionId, result.get(0).getConditionId());
    assertEquals(3, result.get(0).getValue());
  }

  @Test
  @DisplayName("카드 토큰에 해당하는 발급 카드가 없으면 예외 발생")
  void checkCardCondition_fail_cardNotFound() {
    // given: 존재하지 않는 카드 토큰에 대한 요청 생성
    String cardToken = "invalid-token";
    Long conditionId = 10L;

    given(issuedCardTokenRepository.findIssuedCardIdByCardToken(cardToken))
        .willReturn(Optional.empty());

    CardConditionRequest request =
        CardConditionRequest.builder()
            .cardToken(cardToken)
            .conditionId(new Long[] {conditionId})
            .build();

    // when & then: 카드 정보가 없으므로 예외가 발생해야 함
    assertThrows(
        NoSuchElementFoundException404.class,
        () -> benefitUsageCountService.checkCardCondition(request));
  }
}
