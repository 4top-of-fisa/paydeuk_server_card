package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "previous_month_spending")
public class PreviousMonthSpending extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_card_id")
    private IssuedCard issuedCard;

    @Column(name = "value")
    private Integer value;

}