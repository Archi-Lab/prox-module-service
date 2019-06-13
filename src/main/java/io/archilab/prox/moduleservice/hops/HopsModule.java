package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HopsModule {

  @Lob
  @Column(length = 100000)
  @JsonProperty("MODULBEZEICHNUNG")
  private String MODULBEZEICHNUNG;

  @Lob
  @Column(length = 100000)
  @JsonProperty("INHALT")
  private String INHALT;

  @Lob
  @Column(length = 100000)
  @JsonProperty("DATEVERSION")
  private String DATEVERSION;

  @Lob
  @Column(length = 100000)
  @JsonProperty("MODULKUERZEL")
  private String MODULKUERZEL;

}
