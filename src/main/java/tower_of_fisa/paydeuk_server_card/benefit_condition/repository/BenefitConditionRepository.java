package tower_of_fisa.paydeuk_server_card.benefit_condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitCondition;

@Repository
public interface BenefitConditionRepository extends JpaRepository<BenefitCondition, Long> {}
