package com.toimintalogiikka;

import java.util.ArrayList;

public class ValinatorBean {

	private ArrayList<Rule> rulet = new ArrayList<Rule>();
	private BrokenRuleLahde brokenRuleLahde = null;

	public ValinatorBean(BrokenRuleLahde brokenRuleLahde){
		this.brokenRuleLahde = brokenRuleLahde;
	}

	public boolean add(Rule arg0) {
		return rulet.add(arg0);
	}

	public boolean onkoValidi(TietoDto dto){

		for (Rule rule : this.rulet) {
			try {
				rule.onkoValidi(dto);
			} catch (TietoVaarinException e) {
				this.brokenRuleLahde.kaynnistaTapahtuma(rule);
				return false;
			}
		}

		return true;
	}

}
