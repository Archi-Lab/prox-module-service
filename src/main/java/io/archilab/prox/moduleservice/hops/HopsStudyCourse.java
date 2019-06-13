package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HopsStudyCourse {

  @Lob
  @Column(length = 100000)
  @JsonProperty("SG_KZ")
  private String SG_KZ;

  @Lob
  @Column(length = 100000)
  @JsonProperty("STUDIENGANG")
  private String STUDIENGANG;

  @Lob
  @Column(length = 100000)
  @JsonProperty("LE")
  private String LE;

  @Lob
  @Column(length = 100000)
  @JsonProperty("ABSCHLUSSART")
  private String ABSCHLUSSART;

}
