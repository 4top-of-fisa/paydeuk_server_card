package tower_of_fisa.paydeuk_server_card.previous_month_spending.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "PreviousMonthSpendingRequest : 전월 실적 조회 [요청 DTO]",
        description = "요청 데이터")
public class PreviousMonthSpendingRequest {
    @Schema(description = "카드 토큰", example = "tok_1234567890abcdef")
    @NotNull(message = "cardToken은 필수입니다.")
    private String cardToken;
}
