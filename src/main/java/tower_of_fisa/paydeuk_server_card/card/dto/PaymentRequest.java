package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "PaymentRequest : 카드 결제 [요청 DTO]", description = "요청 데이터")
public class PaymentRequest {
  @Schema(description = "카드 토큰", example = "tok_1234567890abcdef")
  @NotNull(message = "cardToken은 필수입니다.")
  private String cardToken;

  @Schema(description = "결제 금액", example = "20000")
  @NotNull(message = "결제 금액은 필수입니다.")
  private int amount;
}
