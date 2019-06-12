package io.archilab.projektboerse.moduleservice.hops;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleHOPS {

//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("UNTERTITEL")
//	private String UNTERTITEL;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("EMPFOHLENEVORAUSSETZUNG")
//	private String EMPFOHLENEVORAUSSETZUNG;

	@Lob
	@Column( length = 100000 )
	@JsonProperty("MODULBEZEICHNUNG")
	private String MODULBEZEICHNUNG;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("KUERZEL")
//	private String KUERZEL;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("LEHRVERANSTALTUNGEN")
//	private String LEHRVERANSTALTUNGEN;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SPRACHE")
//	private String SPRACHE;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SWS_GESAMT")             
//	private String SWS_GESAMT;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SWS_VORLESUNG")         
//	private String SWS_VORLESUNG;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SWS_PRAKTIKUM")         
//	private String SWS_PRAKTIKUM;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SWS_UEBUNG")         
//	private String SWS_UEBUNG;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SWS_SEMINAR")         
//	private String SWS_SEMINAR;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("GRUPPENGROESSE_PRAKTIKUM")
//	private String GRUPPENGROESSE_PRAKTIKUM;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("LEHRFORM")
//	private String LEHRFORM;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_STD_GESAMT")
//	private String AUFWAND_STD_GESAMT;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_STD_VORLESUNG")
//	private String AUFWAND_STD_VORLESUNG;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_STD_PRAKTIKUM")
//	private String AUFWAND_STD_PRAKTIKUM;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_STD_UEBUNG")
//	private String AUFWAND_STD_UEBUNG;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_STD_SEMINAR")
//	private String AUFWAND_STD_SEMINAR;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_STD_SELBSTSTUDIUM")
//	private String AUFWAND_STD_SELBSTSTUDIUM;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("CREDITS")
//	private String CREDITS;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("VORAUSSETZUNGEN")
//	private String VORAUSSETZUNGEN;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("LERNZIELE_KOMPETENZEN")
//	private String LERNZIELE_KOMPETENZEN;
//
	@Lob
	@Column( length = 100000 )
	@JsonProperty("INHALT")
	private String INHALT;

//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("LEISTUNGEN")
//	private String LEISTUNGEN;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("MEDIENFORMEN")
//	private String MEDIENFORMEN;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("LITERATUR")
//	private String LITERATUR;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SCHWERPUNKTE")
//	private String SCHWERPUNKTE;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AKKREDITIERUNG")
//	private String AKKREDITIERUNG;

	@Lob
	@Column( length = 100000 )
	@JsonProperty("DATEVERSION")
	private String DATEVERSION;

//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("DAUER")
//	private String DAUER;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_KONTAKTZEIT")
//	private String AUFWAND_KONTAKTZEIT;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("VORAUSSETZUNGEN_FUERCP")
//	private String VORAUSSETZUNGEN_FUERCP;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("STELLENWERT_NOTE")
//	private String STELLENWERT_NOTE;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("HAEUFIGKEIT_DES_ANGEBOTS")
//	private String HAEUFIGKEIT_DES_ANGEBOTS;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SONSTIGEINFO")
//	private String SONSTIGEINFO;

	@Lob
	@Column( length = 100000 )
	@JsonProperty("MODULKUERZEL")
	private String MODULKUERZEL;

//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("CREDITS_ZUSATZ")
//	private String CREDITS_ZUSATZ;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("AUFWAND_GESAMT_ZUSATZ")
//	private String AUFWAND_GESAMT_ZUSATZ;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SWS_GESAMT_ZUSATZ")
//	private String SWS_GESAMT_ZUSATZ;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("INDEXSPALTE")
//	private String INDEXSPALTE;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("OBSOLETE")
//	private String OBSOLETE;
//
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("ZEITSTEMPEL")
//	private String ZEITSTEMPEL;


}
