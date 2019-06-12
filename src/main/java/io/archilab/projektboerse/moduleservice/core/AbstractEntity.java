package io.archilab.projektboerse.moduleservice.core;

import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@MappedSuperclass
@Data
@Setter(AccessLevel.NONE)
public abstract class AbstractEntity {

  @Id
  private UUID id;

  protected AbstractEntity() {
    this.id = UUID.randomUUID();
  }

}
