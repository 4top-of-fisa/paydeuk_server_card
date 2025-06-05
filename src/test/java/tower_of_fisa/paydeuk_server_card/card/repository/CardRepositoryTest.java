package tower_of_fisa.paydeuk_server_card.card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;

@ActiveProfiles("test")
@DataJpaTest
class CardRepositoryTest {

  @Autowired private CardRepository cardRepository;

  @DisplayName("CARD_REPO_01: 카드사 기준 카드 조회")
  @Test
  void findByCardCompany_success() {
    // given
    Card card =
        Card.builder()
            .name("우리카드")
            .type(CardType.CREDIT)
            .imageUrl("img.jpg")
            .annualFee(1000L)
            .cardCompany(CardCompany.WOORI)
            .build();

    cardRepository.save(card);

    // when
    List<Card> result = cardRepository.findByCardCompany(CardCompany.WOORI);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("우리카드");
    assertThat(result.get(0).getCardCompany()).isEqualTo(CardCompany.WOORI);
  }
}
