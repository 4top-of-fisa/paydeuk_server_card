package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "할인율")
public class DiscountRateResponse {

  @Schema(description = "할인 타입", example = "RATE")
  private final String discountApplyType;

  @Schema(description = "할인 금액", example = "10000")
  private final Long discountAmount;

  @Schema(description = "전월 사용 금액 구간")
  private final SpendingRangeResponse spendingRange;
}
