package tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카드 조건 조회 요청")
public class CardConditionRequest {
  @Schema(description = "카드 토큰", example = "shinhan_mr_life_sungjun")
  private String cardToken;

  @Schema(description = "조건 ID 배열", example = "[16,17,18,19,20]")
  private Long[] conditionId;
}
