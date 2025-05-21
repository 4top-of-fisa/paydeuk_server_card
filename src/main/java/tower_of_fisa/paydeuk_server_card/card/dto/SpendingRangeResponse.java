package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "금액 범위")
public class SpendingRangeResponse {

  @Schema(description = "최소 금액", example = "10000")
  private final Long minSpending;

  @Schema(description = "최대 금액", example = "50000")
  private final Long maxSpending;
}
