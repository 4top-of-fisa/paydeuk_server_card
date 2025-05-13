package tower_of_fisa.paydeuk_server_card.config.exception.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tower_of_fisa.paydeuk_server_card.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.config.exception.custom.BasicCustomException500;

/** 404 : 파일을 찾지 못해 다운로드에 실패한 경우 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException404 extends BasicCustomException500 {
  public FileNotFoundException404(ErrorDefineCode code) {
    super(code);
  }
}
