package tower_of_fisa.paydeuk_server_card.benefit_usage_count.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카드 조건 조회 요청")
public class CardConditionRequest {
  @Schema(description = "카드 토큰", example = "hyundai_m_sungjun")
  private String cardToken;

  @Schema(description = "조건 ID 배열", example = "[1]")
  private Long[] conditionId;
}
