package io.archilab.prox.moduleservice.hops;

import io.archilab.prox.moduleservice.core.AbstractEntity;
import java.util.UUID;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HopsStudyCourseMapping extends AbstractEntity {

  private HopsStudyCourseId hopsId;

  private UUID studyCourseId;

  public HopsStudyCourseMapping(HopsStudyCourseId hopsId, UUID studyCourseId) {
    this.hopsId = hopsId;
    this.studyCourseId = studyCourseId;
  }

}
