package org.challenge.batch.tasklet;

import org.challenge.batch.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Tasklet to download and unzip the csv to be processed
 */
public class CsvExtractionTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(CsvExtractionTasklet.class);

	private final String url;
	private final String destinationDirectory;

	public CsvExtractionTasklet(String url, String destinationDirectory) {
		this.url = url;
		this.destinationDirectory = destinationDirectory;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		downloadZip(url, destinationDirectory);
		return RepeatStatus.FINISHED;
	}

	/**
	 * Method used to download the zip file from ISTAT archive
	 *
	 * @param url                  is the url where the zip file is stored
	 * @param destinationDirectory is the directory where the zip file will be saved
	 * @throws IOException if the directory cannot be created
	 */
	private void downloadZip(String url, String destinationDirectory) throws IOException {
		// Create directory path if not exists
		Path dirPath = Paths.get(destinationDirectory);
		if (!Files.exists(dirPath)) {
			Files.createDirectories(dirPath);
		}

		// Download zip
		URL zipUrl = new URL(url);
		Resource resource = new UrlResource(zipUrl);

		try (InputStream inputStream = resource.getInputStream()) {
			String destinationPath = destinationDirectory + "ISTAT" + Utils.getCurrentDate() + ".zip";
			Path zipFilePath = Paths.get(destinationPath);
			Files.copy(inputStream, zipFilePath, StandardCopyOption.REPLACE_EXISTING);
			logger.info("Downloaded file: {}, start unzip.", zipFilePath);
			unzip(zipFilePath, destinationDirectory);
		}
	}

	/**
	 * Method used to unzip only the csv file from the zip downloaded through downloadZip
	 * this method also deletes all the downloaded zip (we just need the most recent csv)
	 *
	 * @param zipFilePath          is the path where the zip file is stored
	 * @param destinationDirectory is the directory where the CSV file will be saved
	 * @throws IOException if there is no file to unzip or delete
	 */
	private void unzip(Path zipFilePath, String destinationDirectory) throws IOException {
		boolean deleteZip = false;
		try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFilePath), Charset.forName("Cp437"))) {
			ZipEntry entry;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				String entryName = entry.getName();
				// Extracting only the CSV file
				if (!entry.isDirectory() && entryName.toLowerCase().endsWith(".csv")) {
					Path entryPath = Paths.get(destinationDirectory, entryName);
					Files.copy(zipInputStream, Path.of((destinationDirectory + "ISTAT" + Utils.getCurrentDate() + ".csv")), StandardCopyOption.REPLACE_EXISTING);
					deleteZip = true;
					logger.info("CSV file extracted: {}", entryPath);
				}
			}
		}

		// Delete the zip file after succeeded extraction
		if (deleteZip) {
			Files.delete(zipFilePath);
			logger.info("ZIP file deleted: {}", zipFilePath);
		}
	}

}
