package org.challenge.batch.job;

import config.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {TestConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
class ChallengeBatchIntegrationTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	void testBatchJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();

		assertNotNull(jobExecution);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			assertEquals(ExitStatus.COMPLETED, stepExecution.getExitStatus());
		}
	}
}
