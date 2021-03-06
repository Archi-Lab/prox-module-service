package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HopsCurriculum {

  @JsonProperty("ZUORDNUNG_CURRICULUM")
  private String ZUORDNUNG_CURRICULUM;

  @JsonProperty("SG_KZ")
  private String SG_KZ;

  @JsonProperty("SR_KZ")
  private String SR_KZ;

  @JsonProperty("SEMESTER")
  private String SEMESTER;

  @JsonProperty("DATEVERSION")
  private String DATEVERSION;

  @JsonProperty("ID")
  private String ID;

  @JsonProperty("MODULKUERZEL")
  private String MODULKUERZEL;

}
