package org.challenge.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;

/**
 * Tasklet to delete old csv files already parsed.
 * In a real case scenario, we would like to create an old folder and move these old files there.
 * In this sample scenario, we will simply delete old files.
 */
public class OldCsvDeletionTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(OldCsvDeletionTasklet.class);

	private final String directoryPath;
	private final String csvPath;

	public OldCsvDeletionTasklet(String directoryPath, String csvPath) {
		this.directoryPath = directoryPath;
		this.csvPath = csvPath;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		deleteOldCsvs(directoryPath, csvPath);
		return RepeatStatus.FINISHED;
	}

	/**
	 * Method to delete all the old files except the newest one
	 *
	 * @param directoryPath the path of the directory where the csvs are stored
	 * @param csvPath       the path of the file NOT to be deleted
	 */
	private void deleteOldCsvs(String directoryPath, String csvPath) {
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".csv") && !file.getName().equals(csvPath)) {
					if (file.delete()) {
						logger.info("Deleted file: {}", file.getName());
					} else {
						logger.error("Failed to delete file: {}", file.getName());
					}
				}
			}
		} else {
			logger.error("Directory does not exist or is not accessible.");
		}
	}
}
