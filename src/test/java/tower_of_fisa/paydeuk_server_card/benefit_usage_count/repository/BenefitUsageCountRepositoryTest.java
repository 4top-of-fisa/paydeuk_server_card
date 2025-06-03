package tower_of_fisa.paydeuk_server_card.benefit_usage_count.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tower_of_fisa.paydeuk_server_card.domain.entity.*;
import tower_of_fisa.paydeuk_server_card.domain.enums.*;

@ActiveProfiles("test")
@DataJpaTest
class BenefitUsageCountRepositoryTest {

  @Autowired private BenefitUsageCountRepository benefitUsageCountRepository;
  @Autowired private EntityManager em;

  @Test
  @DisplayName("발급 카드 ID와 조건 ID로 혜택 사용 횟수 조회")
  void findByIssuedCardIdAndConditionIdIn_success() {
    // 하나의 발급 카드에 대해 두 개의 혜택 조건이 존재하고,
    // 각 조건에 대한 혜택 사용 횟수가 BenefitUsageCount 테이블에 저장되어 있음.
    // 해당 발급 카드 ID와 조건 ID 목록으로 조회 시,
    // 정확히 등록된 두 건의 혜택 사용 기록이 반환되는지 검증.

    // 1. 테스트용 사용자, 카드, 발급 카드 데이터 생성 및 저장
    Users user =
        Users.builder().name("테스터").phoneNumber("010-0000-0000").birthdate("1990.01.01").build();
    em.persist(user);

    Card card =
        Card.builder()
            .name("테스트 카드")
            .type(CardType.CREDIT)
            .imageUrl("test.jpg")
            .annualFee(0L)
            .cardCompany(CardCompany.WOORI)
            .build();
    em.persist(card);

    IssuedCard issuedCard =
        IssuedCard.builder()
            .user(user)
            .card(card)
            .cardNumber("1111222233334441")
            .cvc("123")
            .expirationYear("2026")
            .expirationMonth("12")
            .cardPassword("00")
            .build();
    em.persist(issuedCard);

    // 2. 테스트용 혜택과 그 조건 2개 생성 및 저장
    Benefit benefit =
        Benefit.builder()
            .benefitTitle("혜택 제목")
            .benefitDescription("설명")
            .benefitType(BenefitType.DISCOUNT)
            .hasAdditionalConditions(false)
            .merchant(null)
            .build();
    em.persist(benefit);

    BenefitCondition condition1 =
        BenefitCondition.builder()
            .value(10000L)
            .conditionCategory(BenefitConditionCategory.MONTHLY_DISCOUNT_LIMIT)
            .benefit(benefit)
            .build();
    em.persist(condition1);

    BenefitCondition condition2 =
        BenefitCondition.builder()
            .value(5000L)
            .conditionCategory(BenefitConditionCategory.MONTHLY_DISCOUNT_LIMIT)
            .benefit(benefit)
            .build();
    em.persist(condition2);

    // 3. 위 발급 카드와 조건 ID 조합으로 혜택 사용 횟수 데이터 2건 저장
    BenefitUsageCount usageCount1 =
        BenefitUsageCount.builder().issuedCard(issuedCard).condition(condition1).value(3).build();
    em.persist(usageCount1);

    BenefitUsageCount usageCount2 =
        BenefitUsageCount.builder().issuedCard(issuedCard).condition(condition2).value(5).build();
    em.persist(usageCount2);

    // 영속성 컨텍스트 초기화 (조회 정확성 확보)
    em.flush();
    em.clear();

    // 4. 테스트 대상 메서드 실행
    // 입력: 발급 카드 ID + 조건 ID 리스트
    // 기대 결과: 조건 2개에 대응하는 혜택 사용 횟수 2건 반환
    List<BenefitUsageCount> result =
        benefitUsageCountRepository.findByIssuedCardIdAndConditionIdIn(
            issuedCard.getId(), List.of(condition1.getId(), condition2.getId()));

    // 5. 검증: 반환된 목록이 2건이며, value 값과 condition ID가 정확히 일치하는지 확인
    assertThat(result).hasSize(2);
    assertThat(result).extracting("value").containsExactlyInAnyOrder(3, 5);
    assertThat(result)
        .extracting("condition.id")
        .containsExactlyInAnyOrder(condition1.getId(), condition2.getId());
  }
}
