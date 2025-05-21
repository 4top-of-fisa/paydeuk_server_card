package tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.BasicCustomException500;

/** 412 : 시간이 만료된 자원에 접근할 때 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ExpireException412 extends BasicCustomException500 {
  public ExpireException412(ErrorDefineCode code) {
    super(code);
  }
}
