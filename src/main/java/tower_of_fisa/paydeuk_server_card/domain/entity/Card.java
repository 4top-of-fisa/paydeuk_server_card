package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CardBenefit> cardBenefits = new ArrayList<>();
}
