package tower_of_fisa.paydeuk_server_card.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity implements Serializable {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt; // 생성일

  @LastModifiedDate private LocalDateTime updatedAt; // 수정일
}
