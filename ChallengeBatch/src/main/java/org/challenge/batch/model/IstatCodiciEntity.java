package org.challenge.batch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "istat_codici")
@Data
public class IstatCodiciEntity {

	@Column
	private Integer codRegione;

	@Column
	private Integer codUts;

	@Column
	private Integer codProvincia;

	@Column
	private Integer progComune;

	@Column
	private Integer codComuneAlfanum;

	@Column
	private String denomBilingue;

	@Column
	private String denomIta;

	@Column
	private String denomAltra;

	@Column
	private Integer codRipGeo;

	@Column
	private String ripartGeo;

	@Column
	private String denomRegione;

	@Column
	private String denomUts;

	@Column
	private Integer tipoUts;

	@Column
	private Integer flagCapoMetroCons;

	@Column
	private String siglaAuto;

	@Column
	private Integer codComuneNum;

	@Column
	private Integer codComune110;

	@Column
	private Integer codComune107;

	@Column
	private Integer codComune103;

	@Id
	@Column
	private String codCatasto;

	@Column
	private String nuts12021;

	@Column
	private String nuts22021;

	@Column
	private String nuts32021;

	@Column
	private String nuts12024;

	@Column
	private String nuts22024;

	@Column
	private String nuts32024;

}
