package tower_of_fisa.paydeuk_server_card.benefit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tower_of_fisa.paydeuk_server_card.domain.entity.Benefit;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {

  @Query(
      "SELECT b.benefitTitle FROM BenefitCondition bc JOIN bc.benefit b WHERE bc.id = :conditionId")
  String findBenefitTitleByBenefitconditionId(Long conditionId);
}
