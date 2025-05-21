package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;

@Getter
@Entity
@Table(name = "card")
public class Card extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 30)
  @Column(name = "name", length = 30)
  private String name;

  @Size(max = 255)
  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "annual_fee")
  private Long annualFee;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private CardType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "company", nullable = false)
  private CardCompany cardCompany;
}
