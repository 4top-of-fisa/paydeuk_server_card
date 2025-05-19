package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;

@Getter
@Entity
@Table(name = "card_benefit")
public class CardBenefit extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  private Card card;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "benefit_id")
  private Benefit benefit;
}
