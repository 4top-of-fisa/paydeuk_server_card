package tower_of_fisa.paydeuk_server_card.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDefineCode {
  UNCAUGHT("ERR_00", "Uncaught Exception"),
  VALID_ERROR("ERR_01", "Field Validation fail"),
  EXAMPLE_OCCURER_ERROR("ERR_02", "예제 코드에서 그냥 발생시킨 오류랍니다"),
  DUPLICATE_EXAMPLE_NAME("ERR_03", "Example로 중복된 이름을 사용할 수 없습니다"),
  INVALID_JSON("ERR_04", "요청 형식이 올바르지 않습니다."),
  AUTHORIZATION_FAIL("AUT_01", "해당 권한이 없습니다."),
  AUTHENTICATE_FAIL("AUT_02", "권한 인증에 실패했습니다."),
  ACCESSTOKEN_EXPIRED("AUT_03", "Access Token이 만료되었습니다."),
  CARD_COMPANY_NOT_FOUND("CC_ERR_01", "해당 카드사가 존재하지 않습니다."),
  CARD_NOT_FOUND("CARD_ERR_01", "카드 정보가 존재하지 않습니다."),
  CARD_OWNER_MISMATCH("CARD_ERR_02", "카드 소유자가 아닙니다."),
  INVALID_CARD("CARD_ERR_03", "카드 정보가 유효하지 않습니다."),
  ;

  private final String code;
  private final String message;
}
