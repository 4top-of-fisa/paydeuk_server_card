package tower_of_fisa.paydeuk_server_card.config.exception.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tower_of_fisa.paydeuk_server_card.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.config.exception.custom.BasicCustomException500;

/** 503 : 네트워크 오류가 발생했을 경우 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NetworkException503 extends BasicCustomException500 {
  public NetworkException503(ErrorDefineCode code) {
    super(code);
  }
}
