package org.challenge.batch.listener.writer;

import org.challenge.batch.model.IstatCodiciEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

public class IstatItemWriterListener implements ItemWriteListener<IstatCodiciEntity> {

	private static final Logger logger = LoggerFactory.getLogger(IstatItemWriterListener.class);

	@Override
	public void beforeWrite(Chunk<? extends IstatCodiciEntity> input) {
		logger.info("[!] Starting the IstatItemWriter.");

		logger.info("[!] Records to insert/update: {}", input.size());
	}

	@Override
	public void afterWrite(Chunk<? extends IstatCodiciEntity> output) {
		logger.info("[!] {} items has been wrote with success.", output.size());
	}

	@Override
	public void onWriteError(Exception e, Chunk<? extends IstatCodiciEntity> errors) {
		logger.error("[x] Error while writing the current chunk of items. Description: " + e.toString());
	}
}