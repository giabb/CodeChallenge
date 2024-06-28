package org.challenge.ms;

import org.challenge.ms.config.TestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
@ContextConfiguration(classes = {TestConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public abstract class BaseTest {

	public static final String MOCKED_CONTROLLER_RESOURCES_PATH = "src/test/resources/mocks/";

	@Autowired
	protected MockMvc mockMvc;

	protected String getMockedResponse(String mockedResourcePath) throws Exception {
		com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		File file = new File(mockedResourcePath);

		return objectMapper.readTree(file).toString();
	}

}
