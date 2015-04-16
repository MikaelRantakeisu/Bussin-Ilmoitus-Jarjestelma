package com.toimintalogiikka;

import java.util.EventListener;

public interface BrokenRuleKuuntelija extends EventListener{
	public void brokenRuleTapahtuma(Rule rule); 

}
