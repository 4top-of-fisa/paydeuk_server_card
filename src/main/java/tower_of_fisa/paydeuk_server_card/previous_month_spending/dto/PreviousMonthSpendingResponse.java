package tower_of_fisa.paydeuk_server_card.previous_month_spending.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PreviousMonthSpendingResponse : 전월 실적 조회 [응답 DTO]")
public class PreviousMonthSpendingResponse {
  @Schema(description = "전월 실적 값", example = "10000")
  private int value;
}
