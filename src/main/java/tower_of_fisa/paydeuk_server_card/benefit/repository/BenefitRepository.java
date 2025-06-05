package tower_of_fisa.paydeuk_server_card.benefit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tower_of_fisa.paydeuk_server_card.domain.entity.Benefit;

import java.util.Optional;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {

  @Query(
      "SELECT b.benefitTitle FROM BenefitCondition bc JOIN bc.benefit b WHERE bc.id = :conditionId")
  String findBenefitTitleByBenefitconditionId(Long conditionId);

  @Query(
      "SELECT b FROM Benefit b "
          + "JOIN Merchant m ON m.id = b.merchant.id "
          + "JOIN CardBenefit cb ON cb.benefit.id = b.id "
          + "JOIN Card c ON c.id = cb.card.id "
          + "JOIN IssuedCard ic ON ic.card.id = c.id "
          + "WHERE m.id = :merchantId AND ic.id = :issuedCardId")
  Optional<Benefit> findByMerchantIdAndCardToken(long merchantId, long issuedCardId);
}
