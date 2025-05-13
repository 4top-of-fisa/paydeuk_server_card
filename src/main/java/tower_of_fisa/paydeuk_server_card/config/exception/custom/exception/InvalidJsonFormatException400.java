package tower_of_fisa.paydeuk_server_card.config.exception.custom.exception;

import tower_of_fisa.paydeuk_server_card.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.config.exception.custom.BasicCustomException500;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 400 : 잘못된 요청 - JSON 파싱 오류 등 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJsonFormatException400 extends BasicCustomException500 {
  public InvalidJsonFormatException400(ErrorDefineCode code) {
    super(code);
  }
}
