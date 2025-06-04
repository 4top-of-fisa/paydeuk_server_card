package tower_of_fisa.paydeuk_server_card.card.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.mapper.CardMapper;
import tower_of_fisa.paydeuk_server_card.card.repository.CardRepository;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

  @Mock CardRepository cardRepository;

  @Mock CardMapper cardMapper;

  @InjectMocks CardService cardService;

  @Test
  @DisplayName("카드 전체 조회 성공")
  void getAllCardInfo_success() {
    // given
    Card card =
        Card.builder()
            .cardCompany(CardCompany.WOORI)
            .name("우리카드")
            .type(CardType.CREDIT)
            .annualFee(1000L)
            .imageUrl("image.jpg")
            .build();

    CardInfoResponse response =
        CardInfoResponse.builder()
            .cardName("우리카드")
            .cardCompany(CardCompany.WOORI)
            .cardType(CardType.CREDIT)
            .imageUrl("image.jpg")
            .annualFee(1000L)
            .build();

    given(cardRepository.findAll()).willReturn(List.of(card));
    given(cardMapper.toDto(card)).willReturn(response);

    // when
    List<CardInfoResponse> result = cardService.getAllCardInfo();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getCardName()).isEqualTo("우리카드");
  }

  @Test
  @DisplayName("카드사별 카드 조회 성공")
  void getCardInfoByCardCompany_success() {
    // given
    Card card =
        Card.builder()
            .cardCompany(CardCompany.WOORI)
            .name("우리카드")
            .type(CardType.CREDIT)
            .annualFee(1000L)
            .imageUrl("image.jpg")
            .build();

    CardInfoResponse response =
        CardInfoResponse.builder()
            .cardName("우리카드")
            .cardCompany(CardCompany.WOORI)
            .cardType(CardType.CREDIT)
            .imageUrl("image.jpg")
            .annualFee(1000L)
            .build();

    given(cardRepository.findByCardCompany(CardCompany.WOORI)).willReturn(List.of(card));
    given(cardMapper.toDto(card)).willReturn(response);

    // when
    List<CardInfoResponse> result = cardService.getCardInfoByCardCompany(CardCompany.WOORI);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getCardCompany()).isEqualTo(CardCompany.WOORI);
  }
}
