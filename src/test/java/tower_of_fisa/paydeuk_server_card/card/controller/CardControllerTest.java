package tower_of_fisa.paydeuk_server_card.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.service.CardService;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;

@WebMvcTest(controllers = CardController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CardControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CardService cardService;

  @DisplayName("CARD_01: 전체 카드 조회 성공")
  @Test
  void getAllCardInfo_success() throws Exception {
    CardInfoResponse card1 =
        CardInfoResponse.builder()
            .cardName("카드1")
            .cardType(CardType.CREDIT)
            .imageUrl("img1.jpg")
            .annualFee(1000L)
            .cardCompany(CardCompany.WOORI)
            .cardBenefits(List.of())
            .build();

    CardInfoResponse card2 =
        CardInfoResponse.builder()
            .cardName("카드2")
            .cardType(CardType.DEBIT)
            .imageUrl("img2.jpg")
            .annualFee(0L)
            .cardCompany(CardCompany.WOORI)
            .cardBenefits(List.of())
            .build();

    List<CardInfoResponse> response = List.of(card1, card2);

    BDDMockito.given(cardService.getAllCardInfo()).willReturn(response);

    mockMvc
        .perform(get("/api/card/info").accept(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("카드 정보 조회 성공"))
        .andExpect(jsonPath("$.response.length()").value(2));
  }

  @DisplayName("CARD_02: 카드사별 카드 조회 성공")
  @Test
  void getCardInfoByCardCompany_success() throws Exception {
    CardInfoResponse card =
        CardInfoResponse.builder()
            .cardName("카드1")
            .cardType(CardType.CREDIT)
            .imageUrl("img1.jpg")
            .annualFee(1000L)
            .cardCompany(CardCompany.WOORI)
            .cardBenefits(List.of())
            .build();

    BDDMockito.given(cardService.getCardInfoByCardCompany(CardCompany.WOORI))
        .willReturn(List.of(card));

    mockMvc
        .perform(get("/api/card/info/WOORI").accept(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("카드사별 카드 정보 조회 성공"))
        .andExpect(jsonPath("$.response[0].cardCompany").value("WOORI"));
  }

  @DisplayName("CARD_02: 카드사별 카드 조회 실패")
  @Test
  void getCardInfoByCardCompany_fail() throws Exception {
    BDDMockito.given(cardService.getCardInfoByCardCompany(CardCompany.KOOKMIN))
        .willThrow(new NoSuchElementFoundException404(ErrorDefineCode.CARD_COMPANY_NOT_FOUND));

    mockMvc
        .perform(get("/api/card/info/KB").accept(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
