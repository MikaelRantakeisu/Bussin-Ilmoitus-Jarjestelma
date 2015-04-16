package com.toimintalogiikka;

public interface Rule {

	public void onkoValidi(TietoDto dto) throws TietoVaarinException;

	public Viesti getViesti();
	
	public String getTeksti();
}
