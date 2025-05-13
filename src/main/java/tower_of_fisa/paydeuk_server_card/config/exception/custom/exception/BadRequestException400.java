package tower_of_fisa.paydeuk_server_card.config.exception.custom.exception;

import tower_of_fisa.paydeuk_server_card.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.config.exception.custom.BasicCustomException500;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 400 : 잘못된 요청 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException400 extends BasicCustomException500 {
  public BadRequestException400(ErrorDefineCode code) {
    super(code);
  }
}
