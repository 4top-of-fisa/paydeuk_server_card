package tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.BasicCustomException500;

/** 404 : 해당 자원을 찾을 수 없는 경우 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException404 extends BasicCustomException500 {
  public NoSuchElementFoundException404(ErrorDefineCode code) {
    super(code);
  }
}
