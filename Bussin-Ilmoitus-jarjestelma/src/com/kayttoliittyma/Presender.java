package com.kayttoliittyma;

import net.sf.marineapi.nmea.util.Position;

import com.toimintalogiikka.TietoDto;
import com.toimintalogiikka.TietoFacade;
import com.toimintalogiikka.ToimintaLogiiikkaRajaPinta;
import com.toimintalogiikka.Viesti;

public class Presender implements KayttoLiittymaRajaPinta{

	private KayttoLiittyma kayttoLiittyma = new KayttoLiittyma(this);
	private ToimintaLogiiikkaRajaPinta tietoFacade = new TietoFacade(this);


	/**
	 * Metodi, joka kapseloi Viesti tyyppisen tiedon 
	 * ja l�hett�� sen toimintalogiikka kerrokseen 
	 * tietofacade oliolle
	 * 
	 * @param viesti
	 */

	public void kapseloiTietoDto(Viesti viesti){
		
		TietoDto dto = new TietoDto();
		dto.setViesti(viesti);
		dto.setTeksti(viesti.toString());

		this.tietoFacade.toimintaLogiikkaTietoDto(dto);
	}

	/**
	 * Metodi kapseloi Position tyyppisen tiedon
	 * TietoDto olioon ja l�hett�� sen Toiminta 
	 * logiikka kerrokseen TietoFacade oliolle
	 * @param position
	 */

	public void kapseloiTietoDto(Position position){
		TietoDto dto = new TietoDto();
		dto.setLeveysAste(position.getLatitude());
		dto.setPituusAste(position.getLongitude());
		dto.setViesti(Viesti.UUSIPAIKKATIETO);
		dto.setTeksti(dto.getViesti().toString());

		this.tietoFacade.toimintaLogiikkaTietoDto(dto);
	}
	/**
	 * Metodi jonka kautta l�hetet��n tietoa
	 * k�ytt�liittym� kerrokseen
	 * metodi l�hett�� tiedon k�ytt�liittym� oliolle
	 */

	@Override
	public void kayttoLiittymaTietoDto(TietoDto dto) {
		this.kayttoLiittyma.kayttoLiittymaTietoDto(dto);
	}


}
