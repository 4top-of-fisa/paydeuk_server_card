package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "benefit_usage_count")
public class BenefitUsageCount extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "`value`")
  private Integer value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "issued_card_id")
  private IssuedCard issuedCard;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "condition_id")
  private BenefitCondition condition;
}
