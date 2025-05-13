package tower_of_fisa.paydeuk_server_card.config.exception.custom;

import com.tower_of_fisa.paydeuk_server_service.common.ErrorDefineCode;
import lombok.Getter;

@Getter
public class BasicCustomException500 extends RuntimeException {
  private final ErrorDefineCode code;

  public BasicCustomException500(ErrorDefineCode code) {
    super(code.getMessage());
    this.code = code;
  }
}
