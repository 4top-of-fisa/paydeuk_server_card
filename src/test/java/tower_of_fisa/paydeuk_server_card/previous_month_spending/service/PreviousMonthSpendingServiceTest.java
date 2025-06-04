package tower_of_fisa.paydeuk_server_card.previous_month_spending.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingRequest;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.repository.PreviousMonthSpendingRepository;

@ExtendWith(MockitoExtension.class)
class PreviousMonthSpendingServiceTest {

  @InjectMocks private PreviousMonthSpendingService previousMonthSpendingService;

  @Mock private PreviousMonthSpendingRepository previousMonthSpendingRepository;

  @DisplayName("카드 토큰으로 전월 실적 조회 성공")
  @Test
  void getRecord_success() {
    // given
    PreviousMonthSpendingRequest request = new PreviousMonthSpendingRequest();
    ReflectionTestUtils.setField(request, "cardToken", "token");

    PreviousMonthSpendingResponse response = new PreviousMonthSpendingResponse(10000);
    given(previousMonthSpendingRepository.findValueByCardToken("token"))
        .willReturn(Optional.of(response));

    // when
    PreviousMonthSpendingResponse result = previousMonthSpendingService.getRecord(request);

    // then
    assertThat(result.getValue()).isEqualTo(10000);
  }

  @DisplayName("카드 토큰에 해당하는 전월 실적이 없으면 예외 발생")
  @Test
  void getRecord_notFound() {
    // given
    PreviousMonthSpendingRequest request = new PreviousMonthSpendingRequest();
    ReflectionTestUtils.setField(request, "cardToken", "token");
    given(previousMonthSpendingRepository.findValueByCardToken("token"))
        .willReturn(Optional.empty());

    // when & then
    assertThrows(
        NoSuchElementFoundException404.class,
        () -> previousMonthSpendingService.getRecord(request));
  }
}
