package org.challenge.ms.service;

import org.challenge.ms.model.IstatCodiciEntity;

import java.util.List;

public interface IstatCodiciService {

	/**
	 * Method do get information about all the cities in the DB
	 * @return all the IstatCodiciEntity in the DB
	 */
	List<IstatCodiciEntity> getCityList();

	/**
	 * Method do get information about a certain city in the DB given its codCatasto
	 * @param codCatasto uniquely identifies a city
	 * @return the specific IstatCodiciEntity the user requested
	 */
	IstatCodiciEntity getCityByCodCatasto(String codCatasto);

}
