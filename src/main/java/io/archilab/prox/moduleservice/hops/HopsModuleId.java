package io.archilab.prox.moduleservice.hops;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HopsModuleId {

  private String kuerzel;

  private String version;

  public HopsModuleId(String kuerzel, String version) {
    this.kuerzel = kuerzel;
    this.version = version;
  }

}
