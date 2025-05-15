package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;
import tower_of_fisa.paydeuk_server_card.domain.enums.PaymentService;

@Getter
@Setter
@Entity
@Table(name = "issued_card_token")
public class IssuedCardToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_card_id")
    private IssuedCard issuedCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_service", nullable = false)
    private PaymentService paymentService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paydeuk_registered_card_id")
    private PaydeukRegisteredCard paydeukRegisteredCard;

}