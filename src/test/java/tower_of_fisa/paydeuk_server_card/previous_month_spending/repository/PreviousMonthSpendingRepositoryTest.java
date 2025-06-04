package tower_of_fisa.paydeuk_server_card.previous_month_spending.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import tower_of_fisa.paydeuk_server_card.card.repository.CardRepository;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCard;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCardToken;
import tower_of_fisa.paydeuk_server_card.domain.entity.PaydeukRegisteredCard;
import tower_of_fisa.paydeuk_server_card.domain.entity.PreviousMonthSpending;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse;
import tower_of_fisa.paydeuk_server_card.domain.entity.Users;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;
import tower_of_fisa.paydeuk_server_card.domain.enums.PaymentService;
import tower_of_fisa.paydeuk_server_card.issued_card.repository.IssuedCardRepository;

@DataJpaTest
@ActiveProfiles("test")
class PreviousMonthSpendingRepositoryTest {

  @Autowired private PreviousMonthSpendingRepository previousMonthSpendingRepository;

  @Autowired private IssuedCardRepository issuedCardRepository;

  @Autowired private CardRepository cardRepository;

  @Autowired private TestEntityManager entityManager;

  @Test
  @DisplayName("카드 토큰으로 전월 실적 조회 성공")
  void findValueByCardToken_returnsValue() {
    Users user =
        Users.builder().name("tester").phoneNumber("010-1234-5678").birthdate("1990-01-01").build();
    entityManager.persist(user);

    Card card =
        Card.builder()
            .name("card")
            .imageUrl("url")
            .annualFee(1000L)
            .type(CardType.CREDIT)
            .cardCompany(CardCompany.HYUNDAI)
            .build();
    cardRepository.save(card);

    IssuedCard issuedCard =
        IssuedCard.builder()
            .cardNumber("1111")
            .cvc("123")
            .expirationYear("2026")
            .expirationMonth("12")
            .cardPassword("00")
            .user(user)
            .card(card)
            .build();
    issuedCardRepository.save(issuedCard);

    PaydeukRegisteredCard registeredCard =
        PaydeukRegisteredCard.builder().cardToken("token").build();
    entityManager.persist(registeredCard);

    IssuedCardToken token =
        IssuedCardToken.builder()
            .paymentService(PaymentService.PAYDEUK)
            .issuedCard(issuedCard)
            .paydeukRegisteredCard(registeredCard)
            .build();
    ReflectionTestUtils.setField(token, "createdAt", LocalDateTime.now());
    ReflectionTestUtils.setField(token, "updatedAt", LocalDateTime.now());
    entityManager.persist(token);

    PreviousMonthSpending spending =
        PreviousMonthSpending.builder().issuedCard(issuedCard).value(5000).build();
    entityManager.persist(spending);
    entityManager.flush();

    Optional<PreviousMonthSpendingResponse> found =
        previousMonthSpendingRepository.findValueByCardToken("token");

    assertThat(found).isPresent();
    assertThat(found.get().getValue()).isEqualTo(5000);
  }

  @Test
  @DisplayName("카드 토큰으로 전월 실적 조회 실패 - 해당 카드 토큰이 없음")
  void findValueByCardToken_notFound() {
    Optional<PreviousMonthSpendingResponse> found =
        previousMonthSpendingRepository.findValueByCardToken("none");

    assertThat(found).isEmpty();
  }
}
