package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HopsModule {

  @JsonProperty("UNTERTITEL")
  private String UNTERTITEL;

  @JsonProperty("EMPFOHLENEVORAUSSETZUNG")
  private String EMPFOHLENEVORAUSSETZUNG;

  @JsonProperty("MODULBEZEICHNUNG")
  private String MODULBEZEICHNUNG;

  @JsonProperty("KUERZEL")
  private String KUERZEL;

  @JsonProperty("LEHRVERANSTALTUNGEN")
  private String LEHRVERANSTALTUNGEN;

  @JsonProperty("SPRACHE")
  private String SPRACHE;

  @JsonProperty("SWS_GESAMT")
  private String SWS_GESAMT;

  @JsonProperty("SWS_VORLESUNG")
  private String SWS_VORLESUNG;

  @JsonProperty("SWS_PRAKTIKUM")
  private String SWS_PRAKTIKUM;

  @JsonProperty("SWS_UEBUNG")
  private String SWS_UEBUNG;

  @JsonProperty("SWS_SEMINAR")
  private String SWS_SEMINAR;

  @JsonProperty("GRUPPENGROESSE_PRAKTIKUM")
  private String GRUPPENGROESSE_PRAKTIKUM;

  @JsonProperty("LEHRFORM")
  private String LEHRFORM;

  @JsonProperty("AUFWAND_STD_GESAMT")
  private String AUFWAND_STD_GESAMT;

  @JsonProperty("AUFWAND_STD_VORLESUNG")
  private String AUFWAND_STD_VORLESUNG;

  @JsonProperty("AUFWAND_STD_PRAKTIKUM")
  private String AUFWAND_STD_PRAKTIKUM;

  @JsonProperty("AUFWAND_STD_UEBUNG")
  private String AUFWAND_STD_UEBUNG;

  @JsonProperty("AUFWAND_STD_SEMINAR")
  private String AUFWAND_STD_SEMINAR;

  @JsonProperty("AUFWAND_STD_SELBSTSTUDIUM")
  private String AUFWAND_STD_SELBSTSTUDIUM;

  @JsonProperty("CREDITS")
  private String CREDITS;

  @JsonProperty("VORAUSSETZUNGEN")
  private String VORAUSSETZUNGEN;

  @JsonProperty("LERNZIELE_KOMPETENZEN")
  private String LERNZIELE_KOMPETENZEN;

  @JsonProperty("INHALT")
  private String INHALT;

  @JsonProperty("LEISTUNGEN")
  private String LEISTUNGEN;

  @JsonProperty("MEDIENFORMEN")
  private String MEDIENFORMEN;

  @JsonProperty("LITERATUR")
  private String LITERATUR;

  @JsonProperty("SCHWERPUNKTE")
  private String SCHWERPUNKTE;

  @JsonProperty("AKKREDITIERUNG")
  private String AKKREDITIERUNG;

  @JsonProperty("DATEVERSION")
  private String DATEVERSION;

  @JsonProperty("DAUER")
  private String DAUER;

  @JsonProperty("AUFWAND_KONTAKTZEIT")
  private String AUFWAND_KONTAKTZEIT;

  @JsonProperty("VORAUSSETZUNGEN_FUERCP")
  private String VORAUSSETZUNGEN_FUERCP;

  @JsonProperty("STELLENWERT_NOTE")
  private String STELLENWERT_NOTE;

  @JsonProperty("HAEUFIGKEIT_DES_ANGEBOTS")
  private String HAEUFIGKEIT_DES_ANGEBOTS;

  @JsonProperty("SONSTIGEINFO")
  private String SONSTIGEINFO;

  @JsonProperty("MODULKUERZEL")
  private String MODULKUERZEL;

  @JsonProperty("CREDITS_ZUSATZ")
  private String CREDITS_ZUSATZ;

  @JsonProperty("AUFWAND_GESAMT_ZUSATZ")
  private String AUFWAND_GESAMT_ZUSATZ;

  @JsonProperty("SWS_GESAMT_ZUSATZ")
  private String SWS_GESAMT_ZUSATZ;

  @JsonProperty("INDEXSPALTE")
  private String INDEXSPALTE;

  @JsonProperty("OBSOLETE")
  private String OBSOLETE;

  @JsonProperty("ZEITSTEMPEL")
  private String ZEITSTEMPEL;

}
