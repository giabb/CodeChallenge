package org.challenge.ms.service;

import org.challenge.ms.model.IstatCodiciEntity;
import org.challenge.ms.repository.IstatCodiciRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IstatCodiciServiceImpl implements IstatCodiciService {

	private static final Logger logger = LoggerFactory.getLogger(IstatCodiciServiceImpl.class);

	@Autowired
	private IstatCodiciRepository istatCodiciRepository;

	/**
	 * Method do get information about all the cities in the DB
	 *
	 * @return all the IstatCodiciEntity in the DB
	 */
	@Override
	public List<IstatCodiciEntity> getCityList() {
		logger.info("Getting list of all the cities in the db");
		return istatCodiciRepository.findAll();
	}

	/**
	 * Method do get information about a certain city in the DB given its codCatasto
	 *
	 * @param codCatasto uniquely identifies a city
	 * @return the specific IstatCodiciEntity the user requested
	 */
	@Override
	public IstatCodiciEntity getCityByCodCatasto(String codCatasto) {
		logger.info("Getting details about city with codCatasto: {}", codCatasto);
		return istatCodiciRepository.findById(codCatasto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find city with codCatasto: " + codCatasto));
	}
}
