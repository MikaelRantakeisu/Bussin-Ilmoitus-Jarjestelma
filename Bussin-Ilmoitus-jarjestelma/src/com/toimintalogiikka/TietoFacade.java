package com.toimintalogiikka;

import com.kayttoliittyma.Presender;
import com.tiedontalletus.TiedonTalletusRajaPinta;

public class TietoFacade implements ToimintaLogiiikkaRajaPinta, BrokenRuleKuuntelija, TiedonTalletusRajaPinta{

	private Presender presender = null;
	private BrokenRuleLahde brokenRuleLahde = new BrokenRuleLahde();
	private TiedonTalletusRajaPinta toimintaLogiikkaCRUD = new ToimintaLogiikkaCRUD(); 
	private Sijainti sijainti = new Sijainti(this);
	private ValinatorBean valinator = new ValinatorBean(this.brokenRuleLahde);
	public DtoEntityMapper mapper = new DtoEntityMapper();

	public TietoFacade(Presender presender) {
		this.presender = presender;
		this.brokenRuleLahde.lisaaKuuntelijaListaan(this);
		this.valinator.add(new PaikkaTietoRule());
		this.valinator.add(new ViestiRule());
	}
	/**
	 * Metodi jolla l‰hetet‰‰n tietoa kayttˆliittym‰ kerroksesta 
	 * toimintalogiikka kerrokseen.
	 * 
	 * Ensimm‰isen‰ tutkitaan onko l‰hetetty tieto validia
	 * ValinatorBean luokan toteuttavassa valinator oliossa
	 * jos tieto ei ole validia siirtyy suoritus brokenRuleLahde
	 * olioon lis‰ttyyn BrokenRuleKuuntelija rajapinnan toteuttaviin 
	 * olioihin, jotka on lis‰tty brokenRuleLahde olion kuuntelijat 
	 * listaan. T‰ss‰ tapauksessa suoritus siirtyy
	 * TietoFacaden brokenRuleTapahtuma metodiin. ??
	 * 
	 * Vai k‰sitell‰‰nkˆ metodi kutsu aina samoin ilman ett‰ 
	 * metodi kutsu ketju muuttuu ?? vaikka ValinatorBean tapahtuisikin
	 * TietoVaarin poikkeus ?? Elikk‰ palautuuko ohjelman suoritus takaisin
	 * kutsuvaan metodiin kun vaikka tapahtuisikin TietoVaarinExeption??
	 * 
	 */

	@Override
	public int toimintaLogiikkaTietoDto(TietoDto dto) {
		/**
		 * Tutkitaan on toiminta logiikka kerrokseen l‰hetetty
		 * TietoDto validi. T‰ytt‰‰kˆ se liiketoiminta kerroksen ehdot ?
		 * palautuuko ohjelman suoritus takaisin
		 * kutsuvaan metodiin kun vaikka tapahtuisikin TietoVaarinExeption
		 * ??
		 */
		if(!this.valinator.onkoValidi(dto))
			return 0;

		if(this.sijainti==null||this.toimintaLogiikkaCRUD==null||this.mapper==null){
			if(dto.getViesti().equals(Viesti.KAYNNISTA)){
				this.toimintaLogiikkaCRUD = new ToimintaLogiikkaCRUD();
				this.sijainti = new Sijainti(this);
				this.mapper = new DtoEntityMapper();
			}else{
				dto.setViesti(Viesti.LOPETA);
				dto.setTeksti("J‰rjestelm‰ sammutettu");
				this.presender.kayttoLiittymaTietoDto(dto);
				return 3;
			}
		}

		if(dto.getViesti().equals(Viesti.LOPETA)){
			this.sijainti=null;
			this.toimintaLogiikkaCRUD=null;
			this.mapper=null;
			dto.setViesti(Viesti.LOPETA);
			dto.setTeksti("J‰rjestelm‰ sammutettu");
			this.presender.kayttoLiittymaTietoDto(dto);
			return 4;
		}
		/**
		 * Tutkitaan miss‰ kohdassa reitti‰ ollaan menossa
		 */
		if(dto.getViesti().equals(Viesti.UUSIPAIKKATIETO)){
			this.sijainti.vertaa(dto);
			return 2;
		}

		if(dto.getViesti().equals(Viesti.SIJAINTI)){
			this.presender.kayttoLiittymaTietoDto(this.sijainti.mikaSijainti());
			return 5;
		}

		return 1;
	}

	/**
	 * Jos esimerkiksi TietoDto validiutta Toimintalogiikka Ruleilla
	 * tutkittaessa tapahtuu poikkeus (tieto ei t‰yt‰ Rulea-> TietoVaarinException). 
	 * Kutsutaan BrokenRuleLahde luokasta tehty‰ oliota joka kutsuu taas siihen 
	 * liitettyj‰ kuuntelijoita (esim TietoFacade), jotka toteuttavat 
	 * brokenRuleTapahtuma(Rule rule) metodin. brokenRuleTapahtuma(Rule rule) metodille 
	 * v‰litett‰‰n parametrina Poikkeuksen synnytt‰nyt Rule
	 * 
	 */

	@Override
	public void brokenRuleTapahtuma(Rule rule) {

		TietoDto dto =  new TietoDto();
		dto.setTeksti(rule.getTeksti());
		dto.setViesti(rule.getViesti());

		this.presender.kayttoLiittymaTietoDto(dto);
	}

	public EntityBean muunnaTietoDtoEntityJaTarkistukset(TietoDto dto){

		if(dto.getIndeksi()==-1){
			this.presender.kayttoLiittymaTietoDto(dto);
			return null;
		}
		if(this.valinator.onkoValidi(dto)){
			return null;
		}
		return this.mapper.muunnaDtoEntity(dto);
	}

	public TietoDto muunnaEntityTietoDtoJaTarkistukset(EntityBean entity){

		TietoDto dto = this.mapper.muunnaEntityDto(entity);
		if(entity.getIndeksi()==-1){
			this.presender.kayttoLiittymaTietoDto(dto);
			return null;
		}
		if(this.valinator.onkoValidi(dto)){
			return null;
		}
		return dto;
	}

	@Override
	public EntityBean talletaTietoKantaan(EntityBean entity) {
		return this.toimintaLogiikkaCRUD.talletaTietoKantaan(entity);
	}
	@Override
	public EntityBean haeTietoKannasta(int indeksi) {
		return this.toimintaLogiikkaCRUD.haeTietoKannasta(indeksi);
	}
	@Override
	public EntityBean alustaTietoKanta() {
		return toimintaLogiikkaCRUD.alustaTietoKanta();		
	}

	public void lahetaViestiKayttoLiittymaan(TietoDto dto) {
		this.presender.kayttoLiittymaTietoDto(dto);

	}

}
