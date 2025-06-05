package tower_of_fisa.paydeuk_server_card.issued_card.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCard;

@Repository
public interface IssuedCardRepository extends JpaRepository<IssuedCard, Long> {

  // 카드 번호로 카드 정보 찾기
  Optional<IssuedCard> findByCardNumber(String cardNumber);

  @Query(
      "SELECT ict.issuedCard.id FROM IssuedCardToken ict "
          + "JOIN ict.paydeukRegisteredCard prc "
          + "WHERE prc.cardToken = :cardToken")
  Long findIssuedCardIdByCardToken(String cardToken);
}
