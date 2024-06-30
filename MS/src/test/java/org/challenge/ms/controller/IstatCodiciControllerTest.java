package org.challenge.ms.controller;

import org.challenge.ms.BaseTest;
import org.challenge.ms.model.IstatCodiciEntity;
import org.challenge.ms.repository.IstatCodiciRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IstatCodiciControllerTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(IstatCodiciControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private IstatCodiciRepository istatCodiciRepository;

	/**
	 * Test to assure the correct population of list of cities returned by correspondent API
	 * @throws Exception because of mockMvc operations
	 */
	@Test
	void testRetrieveCityList_200() throws Exception {

		logger.info("Testing testRetrieveCityList_200");

		String mockedResourcePath = BaseTest.MOCKED_CONTROLLER_RESOURCES_PATH + "testRetrieveCityList_200.json";

		IstatCodiciEntity testCase1 = new IstatCodiciEntity();
		testCase1.setCodCatasto("X00");
		testCase1.setDenomBilingue("Testate sul Muricciolo");

		IstatCodiciEntity testCase2 = new IstatCodiciEntity();
		testCase2.setCodCatasto("X01");
		testCase2.setDenomBilingue("Vergate al Tramezzo");

		istatCodiciRepository.saveAll(Arrays.asList(testCase1, testCase2));

		this.mockMvc.perform(get("/api/city/list"))
			.andExpect(status().isOk())
			.andExpect(content().json(getMockedResponse(mockedResourcePath)));

		logger.info("testRetrieveCityList_200 completed");
	}

	/**
	 * Test to assure the correct population of input city's details returned by correspondent API
	 * @throws Exception because of mockMvc operations
	 */
	@Test
	void testRetrieveCityByCodCatasto_200() throws Exception {

		logger.info("Testing testRetrieveCityByCodCatasto_200");

		String mockedResourcePath = BaseTest.MOCKED_CONTROLLER_RESOURCES_PATH + "testRetrieveCityByCodCatasto_200.json";

		IstatCodiciEntity testCase = new IstatCodiciEntity();
		testCase.setDenomBilingue("Rimembrate sul Corniglio");
		testCase.setCodCatasto("X02");
		testCase.setCodRegione(1);
		testCase.setCodUts(1);
		testCase.setCodProvincia(1);
		testCase.setProgComune(1);
		testCase.setCodComuneAlfanum(1);
		testCase.setDenomIta("Rimembrate sul Corniglio");
		testCase.setDenomAltra("");
		testCase.setCodRipGeo(1);
		testCase.setRipartGeo("Fronte-Retro");
		testCase.setDenomRegione("Lonza");
		testCase.setDenomUts("Milo");
		testCase.setTipoUts(1);
		testCase.setFlagCapoMetroCons(0);
		testCase.setSiglaAuto("LO");
		testCase.setCodComuneNum(1);
		testCase.setCodComune103(1);
		testCase.setCodComune107(1);
		testCase.setCodComune110(1);
		testCase.setNuts12021("a");
		testCase.setNuts22021("aa");
		testCase.setNuts32021("aaa");
		testCase.setNuts12024("a");
		testCase.setNuts22024("aa");
		testCase.setNuts32024("aaa");

		istatCodiciRepository.save(testCase);

		this.mockMvc.perform(get("/api/city/X02"))
			.andExpect(status().isOk())
				.andExpect(content().json(getMockedResponse(mockedResourcePath)));

		logger.info("testRetrieveCityByCodCatasto_200 completed");
	}

	/**
	 * Test to assure 404 in case the city is not found
	 * @throws Exception because of mockMvc operations
	 */
	@Test
	void testRetrieveCityByCodCatasto_404() throws Exception {

		logger.info("Testing testRetrieveCityByCodCatasto_404");

		this.mockMvc.perform(get("/api/city/NOT"))
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Cannot find city with codCatasto")));

		logger.info("testRetrieveCityByCodCatasto_404 completed");
	}
}
