package tower_of_fisa.paydeuk_server_card.previous_month_spending.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingRequest;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.repository.PreviousMonthSpendingRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PreviousMonthSpendingService {

  private final PreviousMonthSpendingRepository previousMonthSpendingRepository;

  /**
   * [전월 실적 조회] 해당 카드의 전월 실적을 조회한다.
   *
   * @param previousMonthSpendingRequest PreviousMonthSpendingRequest - 전월 실적 조회 [요청 DTO]
   * @return previousMonthSpendingResponse - 전월 실적 조회 [응답 DTO]
   */
  public PreviousMonthSpendingResponse getRecord(
      PreviousMonthSpendingRequest previousMonthSpendingRequest) {
    return previousMonthSpendingRepository
            .findValueByCardToken(previousMonthSpendingRequest.getCardToken())
            .orElse(new PreviousMonthSpendingResponse(0));
  }
}
