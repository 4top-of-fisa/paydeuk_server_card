package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "paydeuk_registered_card")
public class PaydeukRegisteredCard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "card_token", length = 30)
    private String cardToken;

}