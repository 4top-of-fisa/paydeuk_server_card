package tower_of_fisa.paydeuk_server_card.benefit_usage_count.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitUsageCount;

public interface BenefitUsageCountRepository extends JpaRepository<BenefitUsageCount, Long> {
  @Query(
      "SELECT buc FROM BenefitUsageCount buc WHERE buc.issuedCard.id = :issuedCardId AND"
          + " buc.condition.id IN :conditionIds")
  List<BenefitUsageCount> findByIssuedCardIdAndConditionIdIn(
      @Param("issuedCardId") Long issuedCardId, @Param("conditionIds") List<Long> conditionIds);
}
