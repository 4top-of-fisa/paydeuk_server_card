package tower_of_fisa.paydeuk_server_card.issuedCardToken.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCardToken;

public interface IssuedCardTokenRepository extends JpaRepository<IssuedCardToken, Long> {
  @Query(
      "SELECT i.issuedCard.id FROM IssuedCardToken i "
          + "JOIN i.paydeukRegisteredCard p "
          + "WHERE p.cardToken = :cardToken")
  Optional<Long> findIssuedCardIdByCardToken(@Param("cardToken") String cardToken);
}
