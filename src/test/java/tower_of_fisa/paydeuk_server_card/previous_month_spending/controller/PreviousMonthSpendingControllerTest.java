package tower_of_fisa.paydeuk_server_card.previous_month_spending.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.util.ReflectionTestUtils;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingRequest;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.service.PreviousMonthSpendingService;

@WebMvcTest(PreviousMonthSpendingController.class)
class PreviousMonthSpendingControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private PreviousMonthSpendingService previousMonthSpendingService;

  @Test
  @DisplayName("CARD_01 : 전월 실적 조회")
  void getRecord_returnsValue() throws Exception {
    PreviousMonthSpendingRequest request = new PreviousMonthSpendingRequest();
    ReflectionTestUtils.setField(request, "cardToken", "token");

    when(previousMonthSpendingService.getRecord(any()))
        .thenReturn(new PreviousMonthSpendingResponse(5000));

    ResultActions result =
        mockMvc.perform(
            post("/api/card/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.response.value").value(5000));
  }
}
