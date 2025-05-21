package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.global.common.BaseEntity;
import tower_of_fisa.paydeuk_server_card.domain.enums.MerchantCategory;

@Getter
@Entity
@Table(name = "merchant")
public class Merchant extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 20)
  @Column(name = "name", length = 20)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private MerchantCategory category;
}
