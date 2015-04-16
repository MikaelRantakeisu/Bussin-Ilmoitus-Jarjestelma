package com.toimintalogiikka;

public class ViestiRule implements Rule{

	private Viesti viesti = Viesti.VIESTIVAARIN;
	private String mikaVaarin = "Viesti vaarin";

	@Override
	public void onkoValidi(TietoDto dto) throws TietoVaarinException {
		
		if(!(dto.getViesti()==Viesti.EIREITILLA||dto.getViesti()==Viesti.KAYNNISTA||dto.getViesti()==Viesti.LOPETA||dto.getViesti()==Viesti.PAIKKATIETOVAARIN||dto.getViesti()==Viesti.SIJAINTI||dto.getViesti()==Viesti.VIESTIVAARIN||dto.getViesti()==Viesti.VIRHE||dto.getViesti()==Viesti.PAIKKATIETO||dto.getViesti()==Viesti.UUSIPAIKKATIETO)){	
			this.mikaVaarin= "Viesti ep‰validi: "+ dto.getViesti().toString();
			throw new TietoVaarinException();
		}
		if(dto.getTeksti()==""||dto.getTeksti()==null){
			this.mikaVaarin= "Teksti v‰‰rin: "+ dto.getTeksti();
			throw new TietoVaarinException();
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
