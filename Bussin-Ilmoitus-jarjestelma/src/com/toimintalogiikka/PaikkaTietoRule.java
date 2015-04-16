package com.toimintalogiikka;

public class PaikkaTietoRule implements Rule{

	private Viesti viesti = Viesti.PAIKKATIETOVAARIN;
	private String mikaVaarin = "Paikkatieto vaarin";

	@Override
	public void onkoValidi(TietoDto dto) throws TietoVaarinException {

		if(dto.getViesti().equals(Viesti.UUSIPAIKKATIETO)){	

			if(dto.getLeveysAste()<=0||dto.getLeveysAste()>100){
				this.mikaVaarin= "Leveysaste ep‰validi";
				throw new TietoVaarinException();
			}
			if(dto.getPituusAste()<=0||dto.getPituusAste()>100){
				this.mikaVaarin= "Pituusaste ep‰validi";
				throw new TietoVaarinException();
			}
			if(dto.getTeksti()==""||dto.getTeksti()==null){
				this.mikaVaarin= "Teksti v‰‰rin";
				throw new TietoVaarinException();
			}
		}
	}

	@Override
	public Viesti getViesti() {
		return this.viesti;
	}

	@Override
	public String getTeksti() {
		// TODO Auto-generated method stub
		return this.mikaVaarin;
	}

}
