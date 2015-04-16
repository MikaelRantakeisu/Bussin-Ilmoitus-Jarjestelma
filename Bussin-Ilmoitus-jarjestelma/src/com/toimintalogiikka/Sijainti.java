package com.toimintalogiikka;

import java.util.ArrayList;

public class Sijainti {

	private TietoFacade tietoFacade = null;
	private ArrayList<TietoDto> sijainnit = new ArrayList<TietoDto>();
	private double marginaali = 0.01;
	private int indeksi = 0;

	public Sijainti(TietoFacade tietoFacade) {
		this.tietoFacade = tietoFacade;
		//this.haePaikkaTietoTietoKannasta();
	}
	/**
	 * Metodi joka hakee kaiken paikkatiedon tietokannasta
	 * ja asettaa sen ArrayList
	 */

	private int haePaikkaTietoTietoKannasta() {
		/**
		 * Tietokannan taulun indeksointi alkaa 1:tä
		 * ensimmäisenä haetaan taulun rivien määrä
		 * antamalla indeksiksi 0
		 */
		TietoDto dto = this.tietoFacade.muunnaEntityTietoDtoJaTarkistukset(this.tietoFacade.haeTietoKannasta(0));
		/**
		 * haetaan tiedot tietokannasta. Taulun indeksi alkaa 1 :sta
		 * 
		 */
		if(dto==null)
			return 0;
		
		int luku = Integer.parseInt(dto.getTeksti());
		
		if(luku==0)
			return 0;

		for (int i = 1; i < luku; i++) {
			dto = this.tietoFacade.mapper.muunnaEntityDto(this.tietoFacade.haeTietoKannasta(i));
			
			if(dto==null)
				return 0;
			
			this.sijainnit.add(dto);
		}
		
		return 1;

	}

	public void vertaa(TietoDto dto){

		if(this.sijainnit.size()==0)
			this.haePaikkaTietoTietoKannasta();

		int i = 1;

		for (TietoDto dto2 : this.sijainnit) {
			if(dto.getPituusAste()>dto2.getPituusAste()-this.marginaali&&dto.getPituusAste()<dto2.getPituusAste()+this.marginaali){
				if(dto.getLeveysAste()>dto2.getLeveysAste()-this.marginaali&&dto.getLeveysAste()<dto2.getLeveysAste()+this.marginaali){
					this.indeksi = i;
					i = 0;
					break;
				}
			}
			i++;
		}

		if(i!=0){
			dto.setViesti(Viesti.EIREITILLA);
			dto.setTeksti("ei olla reitilla");
			this.tietoFacade.lahetaViestiKayttoLiittymaan(dto);
		}else{
			dto.setViesti(Viesti.SIJAINTI);
			dto.setTeksti(this.sijainnit.get(i).getTeksti());
			this.tietoFacade.lahetaViestiKayttoLiittymaan(dto);
		}

	}
	public TietoDto mikaSijainti() {

		TietoDto dto = null;

		if(sijainnit.size()>0){
			dto = sijainnit.get(this.indeksi);
		}else{
			dto = new TietoDto();
			dto.setViesti(Viesti.EIREITILLA);
			dto.setTeksti(" paikkatietoa ei löytynyt !!");
		}
		return dto;
	}

}
