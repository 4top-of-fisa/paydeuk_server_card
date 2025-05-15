package tower_of_fisa.paydeuk_server_card.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tower_of_fisa.paydeuk_server_card.common.BaseEntity;

@Entity
@Table(name = "EXAMPLE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Example extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "EXAM_ID")
  private Long examId;

  @Column(name = "NAME", nullable = false)
  private String name;
}
