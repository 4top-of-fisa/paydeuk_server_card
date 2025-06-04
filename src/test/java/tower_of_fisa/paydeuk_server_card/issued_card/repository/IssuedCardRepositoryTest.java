package tower_of_fisa.paydeuk_server_card.issued_card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import tower_of_fisa.paydeuk_server_card.card.repository.CardRepository;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCard;
import tower_of_fisa.paydeuk_server_card.domain.entity.Users;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;

@ActiveProfiles("test")
@DataJpaTest
class IssuedCardRepositoryTest {

  @Autowired private IssuedCardRepository issuedCardRepository;
  @Autowired private CardRepository cardRepository;
  @Autowired private TestEntityManager em;

  @Test
  @DisplayName("카드 번호로 발급 카드 조회")
  void findByCardNumber_returnsCard() {
    Users user =
        Users.builder().name("tester").phoneNumber("010-1234-5678").birthdate("1990-01-01").build();
    em.persist(user);

    Card card =
        Card.builder()
            .name("card")
            .imageUrl("url")
            .annualFee(1000L)
            .type(CardType.CREDIT)
            .cardCompany(CardCompany.WOORI)
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
    em.flush();
    em.clear();

    Optional<IssuedCard> found = issuedCardRepository.findByCardNumber("1111");

    assertThat(found).isPresent();
    assertThat(found.get().getCardNumber()).isEqualTo("1111");
  }

  @Test
  @DisplayName("카드 번호로 발급 카드 조회 - 존재하지 않으면 빈 Optional")
  void findByCardNumber_notFound() {
    Optional<IssuedCard> found = issuedCardRepository.findByCardNumber("none");

    assertThat(found).isEmpty();
  }
}
