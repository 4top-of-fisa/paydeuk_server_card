package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.domain.enums.PaymentService;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;

@Getter
@Entity
@Table(name = "issued_card_token")
public class IssuedCardToken extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_service", nullable = false)
  private PaymentService paymentService;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "issued_card_id")
  private IssuedCard issuedCard;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "paydeuk_registered_card_id")
  private PaydeukRegisteredCard paydeukRegisteredCard;
}
