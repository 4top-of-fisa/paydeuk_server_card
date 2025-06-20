package tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.BasicCustomException500;

/** 403 : 접근 권한이 부족할 때 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException403 extends BasicCustomException500 {
  public ForbiddenException403(ErrorDefineCode code) {
    super(code);
  }
}
