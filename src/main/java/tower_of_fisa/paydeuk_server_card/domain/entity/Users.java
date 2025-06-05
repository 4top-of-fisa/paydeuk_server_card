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
@Table(name = "users")
public class Users extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 10)
  @Column(name = "name", length = 10)
  private String name;

  @Size(max = 20)
  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(name = "birth_date")
  private String birthdate;
}
