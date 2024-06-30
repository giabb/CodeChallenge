package org.challenge.batch.listener.reader;

import org.challenge.batch.model.IstatCodiciEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class IstatItemReaderListener implements ItemReadListener<IstatCodiciEntity> {

	private static final Logger logger = LoggerFactory.getLogger(IstatItemReaderListener.class);

	@Override
	public void beforeRead() {
		logger.debug("[!] Starting the IstatItemReader.");
	}

	@Override
	public void afterRead(IstatCodiciEntity input) {
		if (input.getCodCatasto() != null)
			logger.debug("[!] The item has been read with success. Item: {}", input);
	}

	@Override
	public void onReadError(Exception e) {
		logger.error("[x] Error while reading the current item. Description: {}", e.toString());
	}
}