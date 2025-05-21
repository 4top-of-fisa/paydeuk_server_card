package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;

@Getter
@Builder
@Schema(description = "혜택 조건")
public class BenefitConditionResponse {

  @Schema(description = "조건 카테고리", example = "할인")
  private final BenefitConditionCategory conditionCategory;

  @Schema(description = "조건 값", example = "10000")
  private final Long value;

  @Schema(description = "조건 범위")
  private final SpendingRangeResponse spendingRange;
}
