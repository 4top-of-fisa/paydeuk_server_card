package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "issued_card")
public class IssuedCard extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Customer user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  private Card card;

  @Size(max = 20)
  @Column(name = "card_number", length = 20)
  private String cardNumber;

  @Size(max = 3)
  @Column(name = "cvc", length = 3)
  private String cvc;

  @Size(max = 2)
  @Column(name = "expiration_year", length = 2)
  private String expirationYear;

  @Size(max = 2)
  @Column(name = "expiration_month", length = 2)
  private String expirationMonth;

  @Column(name = "card_password")
  private Integer cardPassword;
}
