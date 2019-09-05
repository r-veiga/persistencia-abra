package com.persistencia1.domain;

import java.sql.Timestamp;

public class Tramite {
	
	private int idTrami;
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

}
