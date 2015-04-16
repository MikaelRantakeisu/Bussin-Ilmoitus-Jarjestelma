package com.toimintalogiikka;

public class EntityBean {

	private int indeksi=0;
	private double pituusAste=0.0;
	private double leveysAste=0.0;
	private String teksti="";

	public EntityBean(){
	}

	public int getIndeksi() {
		return indeksi;
	}

	public void setIndeksi(int indeksi) {
		this.indeksi = indeksi;
	}

	public double getPituusAste() {
		return pituusAste;
	}

	public void setPituusAste(double pituusAste) {
		this.pituusAste = pituusAste;
	}

	public double getLeveysAste() {
		return leveysAste;
	}

	public void setLeveysAste(double leveysAste) {
		this.leveysAste = leveysAste;
	}

	public String getTeksti() {
		return teksti;
	}

	public void setTeksti(String teksti) {
		this.teksti = teksti;
	}

	
}

