package com.tiedontalletus;

import com.toimintalogiikka.EntityBean;

public interface TiedonTalletusRajaPinta {
	
	public EntityBean talletaTietoKantaan(EntityBean entity);
	public EntityBean haeTietoKannasta(int indeksi);
	public EntityBean alustaTietoKanta();

}
