package com.toimintalogiikka;

import java.util.ArrayList;

public class BrokenRuleLahde {
	
	private ArrayList<BrokenRuleKuuntelija> kuuntelijaLista = new ArrayList<BrokenRuleKuuntelija> ();
	
	public void kaynnistaTapahtuma(Rule rule){
		
		for (BrokenRuleKuuntelija BrokenRuleKuuntelija : this.kuuntelijaLista) {
			BrokenRuleKuuntelija.brokenRuleTapahtuma(rule);
		}
		
	}
	
	public void lisaaKuuntelijaListaan(BrokenRuleKuuntelija kuuntelija){
		this.kuuntelijaLista.add(kuuntelija);
	}
	
	public void poistaKuuntelijaListasta(BrokenRuleKuuntelija kuuntelija){
		this.kuuntelijaLista.remove(kuuntelija);
	}

}
