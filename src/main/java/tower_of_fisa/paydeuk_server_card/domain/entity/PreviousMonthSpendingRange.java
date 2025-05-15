package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "previous_month_spending_range")
public class PreviousMonthSpendingRange extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "min_spending")
    private Long minSpending;

    @Column(name = "max_spending")
    private Long maxSpending;

}