package org.challenge.batch.listener.processor;

import org.challenge.batch.model.IstatCodiciEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class IstatItemProcessorListener implements ItemProcessListener<IstatCodiciEntity, IstatCodiciEntity> {

	private static final Logger logger = LoggerFactory.getLogger(IstatItemProcessorListener.class);

	@Override
	public void beforeProcess(IstatCodiciEntity input) {
		logger.debug("[!] Starting the IstatItemProcessor.");
	}

	@Override
	public void afterProcess(IstatCodiciEntity input, IstatCodiciEntity output) {

		logger.debug("[!] The item has been processed correctly.");
	}

	@Override
	public void onProcessError(IstatCodiciEntity output, Exception e) {
		logger.error("[x] Error while processing the current item. Description: " + e.toString());
	}
}