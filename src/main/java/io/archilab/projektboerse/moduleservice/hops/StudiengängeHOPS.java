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
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudiengängeHOPS {
	  
	@Lob
	@Column( length = 100000 )
	@JsonProperty("SG_KZ")	
	private String SG_KZ;
	
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("SR_KZ")	
//	private String SR_KZ;
//	
	@Lob
	@Column( length = 100000 )
	@JsonProperty("STUDIENGANG")	
	private String STUDIENGANG;
	
	@Lob
	@Column( length = 100000 )
	@JsonProperty("LE")	
	private String LE;
	
	@Lob
	@Column( length = 100000 )
	@JsonProperty("ABSCHLUSSART")	
	private String ABSCHLUSSART;
	
//	@Lob
//	@Column( length = 100000 )
//	@JsonProperty("STUDIENRICHTUNG")	
//	private String STUDIENRICHTUNG;
	  	
}
