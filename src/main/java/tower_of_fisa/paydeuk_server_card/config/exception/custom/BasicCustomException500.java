package tower_of_fisa.paydeuk_server_card.config.exception.custom;

import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.common.ErrorDefineCode;

@Getter
public class BasicCustomException500 extends RuntimeException {
  private final ErrorDefineCode code;

  public BasicCustomException500(ErrorDefineCode code) {
    super(code.getMessage());
    this.code = code;
  }
}
