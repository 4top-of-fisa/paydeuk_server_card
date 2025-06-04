package tower_of_fisa.paydeuk_server_card.token.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCard;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCardToken;
import tower_of_fisa.paydeuk_server_card.domain.entity.PaydeukRegisteredCard;
import tower_of_fisa.paydeuk_server_card.domain.entity.Users;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;
import tower_of_fisa.paydeuk_server_card.domain.enums.PaymentService;
import tower_of_fisa.paydeuk_server_card.issued_card.repository.IssuedCardRepository;
import tower_of_fisa.paydeuk_server_card.card.repository.CardRepository;
import tower_of_fisa.paydeuk_server_card.issued_card_token.repository.IssuedCardTokenRepository;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class IssuedCardTokenRepositoryTest {

  @Autowired private IssuedCardTokenRepository issuedCardTokenRepository;

  @Autowired private IssuedCardRepository issuedCardRepository;

  @Autowired private CardRepository cardRepository;

  @Autowired private TestEntityManager entityManager;

  @Test
  @DisplayName("카드 토큰으로 발급된 카드 ID 조회")
  void findIssuedCardIdByCardToken_returnsId() {
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
    issuedCardTokenRepository.save(token);
    entityManager.flush();

    Optional<Long> found = issuedCardTokenRepository.findIssuedCardIdByCardToken("token");

    assertThat(found).contains(issuedCard.getId());
  }

  @Test
  @DisplayName("카드 토큰으로 발급된 카드 ID 조회 - 카드 토큰이 존재하지 않을 때")
  void findIssuedCardIdByCardToken_notFound() {
    Optional<Long> found = issuedCardTokenRepository.findIssuedCardIdByCardToken("none");

    assertThat(found).isEmpty();
  }
}
