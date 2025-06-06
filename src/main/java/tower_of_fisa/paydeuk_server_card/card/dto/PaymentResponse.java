package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PaymentResponse : 카드 결제 [응답 DTO]")
public class PaymentResponse {
  @Schema(description = "결제 시점", example = "2025-05-22-T10:15:30")
  @NotNull(message = "결제 시점은 필수입니다.")
  private String paymentMoment;

  private Long benefitId;

  @NotNull(message = "할인 금액이 없을 경우 0원.")
  private Double discountAmount;
}
