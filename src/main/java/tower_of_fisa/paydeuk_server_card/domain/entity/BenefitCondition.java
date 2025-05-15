package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;
import tower_of_fisa.paydeuk_server_card.domain.enums.ValueType;

@Getter
@Setter
@Entity
@Table(name = "benefit_condition")
public class BenefitCondition extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id")
    private Benefit benefit;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_category", nullable = false)
    private BenefitConditionCategory conditionCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_month_spending_range_id")
    private PreviousMonthSpendingRange previousMonthSpendingRange;


    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false)
    private ValueType valueType;

    @Column(name = "value")
    private Long value;

}