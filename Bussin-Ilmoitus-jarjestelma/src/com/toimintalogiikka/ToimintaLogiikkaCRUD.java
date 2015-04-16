package com.toimintalogiikka;

import com.tiedontalletus.Repository;
import com.tiedontalletus.TiedonTalletusRajaPinta;



public class ToimintaLogiikkaCRUD implements TiedonTalletusRajaPinta {

	private TiedonTalletusRajaPinta repository = new Repository();

	@Override
	public EntityBean talletaTietoKantaan(EntityBean entity) {
		return this.repository.talletaTietoKantaan(entity);
	}

	@Override
	public EntityBean haeTietoKannasta(int indeksi) {
		return this.repository.haeTietoKannasta(indeksi);
	}

	@Override
	public EntityBean alustaTietoKanta() {
		return repository.alustaTietoKanta();
	}
}
