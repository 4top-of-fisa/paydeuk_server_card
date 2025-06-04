package tower_of_fisa.paydeuk_server_card.token.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.BadRequestException400;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.ForbiddenException403;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.token.dto.CardIssueRequest;
import tower_of_fisa.paydeuk_server_card.token.dto.CardTokenResponse;
import tower_of_fisa.paydeuk_server_card.token.service.IssuedCardTokenService;

@WebMvcTest(IssuedCardTokenController.class)
@MockBean(JpaMetamodelMappingContext.class)
class IssuedCardTokenControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private IssuedCardTokenService issuedCardTokenService;

  @Test
  @DisplayName("CARD_TOKEN_01: 카드 토큰 발급 성공")
  void issueCardToken_success() throws Exception {
    // given
    CardIssueRequest request =
        CardIssueRequest.builder()
            .userName("임지섭")
            .userBirthDate("1999-09-01")
            .userPhone("010-3164-6358")
            .cardNumber("1111-2222-3333-4445")
            .month("12")
            .year("26")
            .cvc("123")
            .pinPrefix("00")
            .build();

    CardTokenResponse response = CardTokenResponse.builder().cardToken("token-abc123").build();

    given(issuedCardTokenService.issueCardToken(any())).willReturn(response);

    // when & then
    mockMvc
        .perform(
            post("/api/card/token/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("카드 토큰 발급 성공"))
        .andExpect(jsonPath("$.response.cardToken").value("token-abc123"));
  }

  @Test
  @DisplayName("CARD_TOKEN_02: 카드 정보 유효하지 않음 (400)")
  void issueCardToken_invalidRequest_400() throws Exception {
    // given
    CardIssueRequest request =
        CardIssueRequest.builder()
            .userName("임지섭")
            .userBirthDate("1999-09-01")
            .userPhone("010-3164-6358")
            .cardNumber("invalid-card")
            .month("13") // 잘못된 월
            .year("99")
            .cvc("000")
            .pinPrefix("00")
            .build();

    given(issuedCardTokenService.issueCardToken(any()))
        .willThrow(new BadRequestException400(ErrorDefineCode.INVALID_CARD));

    // when & then
    mockMvc
        .perform(
            post("/api/card/token/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("카드 정보가 유효하지 않습니다."));
  }

  @Test
  @DisplayName("CARD_TOKEN_03: 카드 소유자 아님 (403)")
  void issueCardToken_forbidden_403() throws Exception {
    // given
    CardIssueRequest request =
        CardIssueRequest.builder()
            .userName("임지섭")
            .userBirthDate("1999-09-01")
            .userPhone("010-3164-6358")
            .cardNumber("1111-2222-3333-4445")
            .month("12")
            .year("26")
            .cvc("123")
            .pinPrefix("00")
            .build();

    given(issuedCardTokenService.issueCardToken(any()))
        .willThrow(new ForbiddenException403(ErrorDefineCode.CARD_OWNER_MISMATCH));

    // when & then
    mockMvc
        .perform(
            post("/api/card/token/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("카드 소유자가 아닙니다."));
  }

  @Test
  @DisplayName("CARD_TOKEN_04: 카드 정보 없음 (404)")
  void issueCardToken_cardNotFound_404() throws Exception {
    // given
    CardIssueRequest request =
        CardIssueRequest.builder()
            .userName("임지섭")
            .userBirthDate("1999-09-01")
            .userPhone("010-3164-6358")
            .cardNumber("9999-9999-9999-9999")
            .month("12")
            .year("26")
            .cvc("123")
            .pinPrefix("00")
            .build();

    given(issuedCardTokenService.issueCardToken(any()))
        .willThrow(new NoSuchElementFoundException404(ErrorDefineCode.CARD_NOT_FOUND));

    // when & then
    mockMvc
        .perform(
            post("/api/card/token/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("해당 카드를 찾을 수 없습니다."));
  }
}
