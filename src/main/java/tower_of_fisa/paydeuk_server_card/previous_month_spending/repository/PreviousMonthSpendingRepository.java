package tower_of_fisa.paydeuk_server_card.previous_month_spending.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tower_of_fisa.paydeuk_server_card.domain.entity.PreviousMonthSpending;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse;

public interface PreviousMonthSpendingRepository
    extends JpaRepository<PreviousMonthSpending, Long> {
  @Query(
      """
          SELECT new tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse(pms.value)
          FROM PreviousMonthSpending pms
          JOIN pms.issuedCard i
          JOIN i.issuedCardTokenList ict
          JOIN ict.paydeukRegisteredCard prc
          WHERE prc.cardToken = :cardToken
      """)
  Optional<PreviousMonthSpendingResponse> findValueByCardToken(
      @Param("cardToken") String cardToken);

  @Query("SELECT pms.value FROM PreviousMonthSpending pms WHERE pms.issuedCard.id = :issuedCardId")
  Optional<Integer> findValueByIssuedCardId(long issuedCardId);
}
/*
 paydeuk_registerd_card에서 카드 토큰으로 id 찾고
 그거로 issued_card_token에서 issued_card_id 찾고
 그거로 previous_month_spending에서 issued_card_id랑 같은거 value값 뱉어내기


*/
