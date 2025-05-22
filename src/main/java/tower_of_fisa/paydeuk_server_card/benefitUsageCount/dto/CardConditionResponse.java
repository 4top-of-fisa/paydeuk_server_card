package tower_of_fisa.paydeuk_server_card.benefitUsageCount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "카드 조건 응답")
public class CardConditionResponse {

  @Schema(description = "조건 ID(PK)", example = "1")
  private Long conditionId;

  @Schema(description = "조건 값 (사용 횟수 또는 금액)", example = "3")
  private int value;
}
