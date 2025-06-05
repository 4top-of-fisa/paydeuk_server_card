package tower_of_fisa.paydeuk_server_card.discount_rate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tower_of_fisa.paydeuk_server_card.domain.entity.Benefit;
import tower_of_fisa.paydeuk_server_card.domain.entity.DiscountRate;

import java.util.Optional;

public interface DiscountRateRepository extends JpaRepository<DiscountRate, Long> {

  @Query(
      "SELECT dr FROM DiscountRate dr "
          + "LEFT JOIN dr.spendingRange sr "
          + "WHERE dr.benefit = :benefit AND ("
          + "(sr.minSpending <= :previousMonthSpending AND sr.maxSpending > :previousMonthSpending) "
          + "OR (sr.maxSpending IS NULL AND :previousMonthSpending >= sr.minSpending)"
          + "OR dr.spendingRange IS NULL)")
  Optional<DiscountRate> findByBenefitAndPreviousMonthSpending(
      Benefit benefit, int previousMonthSpending);
}
