package tower_of_fisa.paydeuk_server_card.benefit_usage_count.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto.CardConditionResponse;
import tower_of_fisa.paydeuk_server_card.benefit_usage_count.service.BenefitUsageCountService;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;

@WebMvcTest(controllers = BenefitUsageCountController.class)
@MockBean(JpaMetamodelMappingContext.class)
class BenefitUsageCountControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private BenefitUsageCountService benefitUsageCountService;

  @DisplayName("CARD_01: 카드 조건 조회 성공")
  @Test
  void checkCardCondition_success() throws Exception {
    // given
    Map<String, Object> requestMap =
        Map.of("cardToken", "hyundai_m_test", "conditionId", new Long[] {1L, 2L, 3L, 4L, 5L});

    List<CardConditionResponse> response = List.of(new CardConditionResponse(1L, 2));

    BDDMockito.given(benefitUsageCountService.checkCardCondition(any())).willReturn(response);

    // when & then
    mockMvc
        .perform(
            post("/api/card/check/condition")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestMap)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @DisplayName("CARD_01: 카드 조건 조회 실패 - 존재하지 않는 카드")
  @Test
  void checkCardCondition_fail() throws Exception {
    // given
    Map<String, Object> requestMap =
        Map.of("cardToken", "invalid_token", "conditionId", new Long[] {1L, 2L, 3L, 4L, 5L});

    // 서비스가 NoSuchElementFoundException을 던지도록 모킹
    BDDMockito.given(benefitUsageCountService.checkCardCondition(any()))
        .willThrow(new NoSuchElementFoundException404(ErrorDefineCode.CARD_NOT_FOUND));

    // when & then
    mockMvc
        .perform(
            post("/api/card/check/condition")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestMap)))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
