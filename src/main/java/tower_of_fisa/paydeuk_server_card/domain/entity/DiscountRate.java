package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;
import tower_of_fisa.paydeuk_server_card.domain.enums.DiscountApplyType;

@Getter
@Entity
@Table(name = "discount_rate")
public class DiscountRate extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "discount_amount")
  private Long discountAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private DiscountApplyType discountApplyType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "benefit_id")
  private Benefit benefit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "previous_month_spending_range_id")
  private PreviousMonthSpendingRange previousMonthSpendingRange;
}
