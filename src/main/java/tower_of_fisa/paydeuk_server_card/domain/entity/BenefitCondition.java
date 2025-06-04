package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitConditionCategory;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "benefit_condition")
public class BenefitCondition extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "`value`")
  private Long value;

  @Enumerated(EnumType.STRING)
  @Column(name = "condition_category", nullable = false)
  private BenefitConditionCategory conditionCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "benefit_id")
  private Benefit benefit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spending_range_id", nullable = true)
  private transient SpendingRange spendingRange;
}
