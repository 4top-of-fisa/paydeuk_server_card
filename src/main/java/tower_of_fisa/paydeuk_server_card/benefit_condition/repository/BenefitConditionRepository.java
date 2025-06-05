package tower_of_fisa.paydeuk_server_card.benefit_condition.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tower_of_fisa.paydeuk_server_card.domain.entity.Benefit;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitCondition;

@Repository
public interface BenefitConditionRepository extends JpaRepository<BenefitCondition, Long> {

  @Query(
      "SELECT bc FROM BenefitCondition bc LEFT JOIN bc.spendingRange sr WHERE bc.benefit = :benefit"
          + " AND ( (sr.minSpending <= :previousMonthSpending AND sr.maxSpending >"
          + " :previousMonthSpending) OR (sr.minSpending <= :previousMonthSpending AND"
          + " sr.maxSpending IS NULL) OR (bc.spendingRange IS NULL))")
  List<BenefitCondition> findByBenefitIdAndPreviousMonthSpending(
      Benefit benefit, int previousMonthSpending);
}
