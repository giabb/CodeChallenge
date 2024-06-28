package org.challenge.batch.listener.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomStepListener implements StepExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger(CustomStepListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("[!] {} completed. Items processed: {} - Items written: {} - Items skipped: {} - Commits: {}",
				stepExecution.getStepName(), stepExecution.getReadCount(), stepExecution.getWriteCount(), stepExecution.getSkipCount(), stepExecution.getCommitCount());

		return stepExecution.getExitStatus();
	}
}
