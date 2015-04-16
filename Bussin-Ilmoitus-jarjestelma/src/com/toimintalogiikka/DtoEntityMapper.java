package com.toimintalogiikka;

public class DtoEntityMapper {

	public EntityBean muunnaDtoEntity(TietoDto dto){

		EntityBean entity = new EntityBean();

		entity.setLeveysAste(dto.getLeveysAste());
		entity.setPituusAste(dto.getPituusAste());
		entity.setTeksti(dto.getTeksti());
		entity.setIndeksi(dto.getIndeksi());

		return entity;
	}

	public TietoDto muunnaEntityDto(EntityBean entity){

		TietoDto dto = new TietoDto();

		dto.setLeveysAste(entity.getLeveysAste());
		dto.setPituusAste(entity.getPituusAste());
		dto.setTeksti(entity.getTeksti());
		dto.setIndeksi(entity.getIndeksi());
		dto.setViesti(Viesti.PAIKKATIETO);

		return dto;
	}


}
