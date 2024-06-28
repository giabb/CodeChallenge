package org.challenge.batch.listener.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;

public class ClassGenericSkipperLogger<T> implements SkipListener<T, T> {
	private static final Logger logger = LoggerFactory.getLogger(ClassGenericSkipperLogger.class);
	private final String stepName;

	public ClassGenericSkipperLogger(String stepName) {
		this.stepName = stepName;
	}

	@Override
	public void onSkipInRead(Throwable throwable) {
		if (throwable instanceof FlatFileParseException flatFileParseException) {
			String message = flatFileParseException.getCause() + "-" + flatFileParseException.getLineNumber();
			logger.error("{}-{}", this.stepName, message);
		}
	}

	@Override
	public void onSkipInWrite(T t, Throwable throwable) {
		logger.error("{} - {} {}  was skipped by the item writer because of the following error:\n\n {}", this.stepName, t.getClass().getSimpleName(), t, throwable.getCause());
	}

	@Override
	public void onSkipInProcess(T t, Throwable throwable) {
		logger.error("{} - {} {} was skipped by the item processor because of the following error:\n\n {}", this.stepName, t.getClass().getSimpleName(), t, throwable.getCause());

	}
}
