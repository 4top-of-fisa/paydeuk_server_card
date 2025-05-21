package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitType;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;

@Getter
@Entity
@Table(name = "benefit")
public class Benefit extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 100)
  @Column(name = "title", length = 100)
  private String benefitTitle;

  @Size(max = 100)
  @Column(name = "description", length = 100)
  private String benefitDescription;

  @Column(name = "has_additional_conditions")
  private Boolean hasAdditionalConditions;

  @Enumerated(EnumType.STRING)
  @Column(name = "benefit_type", nullable = false)
  private BenefitType benefitType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "merchant_id")
  private Merchant merchant;

  @OneToMany(mappedBy = "benefit", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BenefitCondition> benefitConditions = new ArrayList<>();

  @OneToMany(mappedBy = "benefit", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DiscountRate> discountRates = new ArrayList<>();
}
