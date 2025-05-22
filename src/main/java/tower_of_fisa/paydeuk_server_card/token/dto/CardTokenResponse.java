package tower_of_fisa.paydeuk_server_card.token.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "카드 토큰 응답")
public class CardTokenResponse {

  @Schema(description = "카드 ID", example = "1")
  private final Long cardId;

  @Schema(description = "카드 토큰", example = "RF1yH2cquzK5T2vJ7TLHs5ygCtQ1Fchd")
  private final String cardToken;
}
