package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.domain.enums.DiscountApplyType;

@Getter
@Entity
@Table(name = "discount_rate")
public class DiscountRate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "amount")
  private Long discountAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "apply_type", nullable = false)
  private DiscountApplyType discountApplyType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "benefit_id")
  private Benefit benefit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spending_range_id")
  private SpendingRange spendingRange;
}
