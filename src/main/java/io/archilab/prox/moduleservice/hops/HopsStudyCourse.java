package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HopsStudyCourse {

  @JsonProperty("SG_KZ")
  private String SG_KZ;

  @JsonProperty("SR_KZ")
  private String SR_KZ;

  @JsonProperty("STUDIENGANG")
  private String STUDIENGANG;

  @JsonProperty("LE")
  private String LE;

  @JsonProperty("ABSCHLUSSART")
  private String ABSCHLUSSART;

  @JsonProperty("STUDIENRICHTUNG")
  private String STUDIENRICHTUNG;

}
