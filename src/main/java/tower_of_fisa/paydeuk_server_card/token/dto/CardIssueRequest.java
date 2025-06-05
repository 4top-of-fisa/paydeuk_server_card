package tower_of_fisa.paydeuk_server_card.token.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카드 토큰 발급 요청")
public class CardIssueRequest {

  @Schema(description = "사용자 이름", example = "임지섭")
  private String userName;

  @Schema(description = "사용자 생년월일", example = "1999-09-01")
  private String userBirthDate;

  @Schema(description = "사용자 전화번호", example = "010-3164-6358")
  private String userPhone;

  @Schema(description = "카드 번호", example = "1111-2222-3333-4445")
  private String cardNumber;

  @Schema(description = "카드 만료 월", example = "12")
  private String month;

  @Schema(description = "카드 만료 연도", example = "26")
  private String year;

  @Schema(description = "카드 CVC", example = "123")
  private String cvc;

  @Schema(description = "카드 비밀번호 앞 두자리", example = "00")
  private String pinPrefix;
}
