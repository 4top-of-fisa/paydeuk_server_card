package tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.BasicCustomException500;

/** 415 : 요청 파일이 허용되지 않는 미디어 타입일 경우 (사진만 허용되는데 다른거 보낸경우) */
@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaTypeException415 extends BasicCustomException500 {

  public UnsupportedMediaTypeException415(ErrorDefineCode code) {
    super(code);
  }
}
