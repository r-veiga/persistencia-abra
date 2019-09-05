package com.persistencia2.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tramite")
public class Tramite {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTrami;
	
	@Column(name="tipoTram")   // Optativo, porque los nombres coinciden en BD 
	private String tipoTram;
	
	private Timestamp fhcTram;
	
	public Tramite() {
	}
	
	public Tramite(int idTrami, String tipoTram, Timestamp fhcTram) {
		this.idTrami = idTrami;
		this.tipoTram = tipoTram;
		this.fhcTram = fhcTram;
	}
	
	public Tramite(String tipoTram, Timestamp fhcTram) {
		this.tipoTram = tipoTram;
		this.fhcTram = fhcTram;
	}

	public int getIdTrami() {
		return idTrami;
	}
	public void setIdTrami(int idTrami) {
		this.idTrami = idTrami;
	}
	public String getTipoTram() {
		return tipoTram;
	}
	public void setTipoTram(String tipoTram) {
		this.tipoTram = tipoTram;
	}
	public Timestamp getFhcTram() {
		return fhcTram;
	}
	public void setFhcTram(Timestamp fhcTram) {
		this.fhcTram = fhcTram;
	}

	@Override
	public String toString() {
		return "\nTramite [idTrami=" + idTrami + ", tipoTram=" + tipoTram + ", fhcTram=" + fhcTram + "]";
	}

}
