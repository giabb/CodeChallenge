package org.challenge.batch.listener.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class CustomChunkListener implements ChunkListener {
	private static final Logger logger = LoggerFactory.getLogger(CustomChunkListener.class);
	private int chunkSize;

	public CustomChunkListener() {
	}

	public CustomChunkListener(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	@Override
	public void beforeChunk(ChunkContext chunkContext) {
		logger.info("[!] {} is processing a new chunk of items ", chunkContext.getStepContext().getStepExecution().getStepName());
	}

	@Override
	public void afterChunk(ChunkContext chunkContext) {
		logger.info("[!] {} chunk processing completed. Items processed so far: {}", chunkContext.getStepContext().getStepExecution().getStepName(), chunkContext.getStepContext().getStepExecution().getReadCount());
	}

	@Override
	public void afterChunkError(ChunkContext chunkContext) {
		//   logger.error("[x] {}: Error while processing the current chunk. Failure:\n {}", chunkContext.getStepContext().getStepName(), chunkContext.getAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY));
		int chunkNumberError = (int) ((3 / chunkSize) + ((chunkContext.getStepContext().getStepExecution().getReadCount() - 1) / chunkSize));
		int minRange = (chunkNumberError * chunkSize);
		int maxRange = ((chunkNumberError + 1) * chunkSize);
		logger.error("[x] {}: Error while processing the current chunk number :{} , range [{},{}], error : {}", chunkContext.getStepContext().getStepName(), chunkNumberError, minRange, maxRange, chunkContext.getStepContext().getAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY));
	}
}
