package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "issued_card")
public class IssuedCard extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 20)
  @Column(name = "card_number", length = 20)
  private String cardNumber;

  @Size(max = 3)
  @Column(name = "cvc", length = 3)
  private String cvc;

  @Size(max = 4)
  @Column(name = "expiration_year", length = 4)
  private String expirationYear;

  @Size(max = 2)
  @Column(name = "expiration_month", length = 2)
  private String expirationMonth;

  @Size(max = 60)
  @Column(name = "card_password", length = 60)
  private String cardPassword;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  private Card card;

  @OneToMany(mappedBy = "issuedCard", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<IssuedCardToken> issuedCardTokenList = new ArrayList<>();
}
