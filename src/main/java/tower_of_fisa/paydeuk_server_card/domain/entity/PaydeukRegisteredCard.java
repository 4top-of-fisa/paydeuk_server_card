package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "paydeuk_registered_card")
public class PaydeukRegisteredCard extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 100)
  @Column(name = "card_token", length = 100)
  private String cardToken;

  @OneToOne(mappedBy = "paydeukRegisteredCard")
  private IssuedCardToken issuedCardToken;
}
