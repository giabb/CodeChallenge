package org.challenge.batch.job;

import org.challenge.batch.listener.processor.IstatItemProcessorListener;
import org.challenge.batch.listener.reader.IstatItemReaderListener;
import org.challenge.batch.listener.utils.ClassGenericSkipperLogger;
import org.challenge.batch.listener.utils.CustomChunkListener;
import org.challenge.batch.listener.utils.CustomStepListener;
import org.challenge.batch.listener.writer.IstatItemWriterListener;
import org.challenge.batch.model.IstatCodiciEntity;
import org.challenge.batch.processor.IstatItemProcessor;
import org.challenge.batch.reader.IstatItemReader;
import org.challenge.batch.tasklet.CsvExtractionTasklet;
import org.challenge.batch.tasklet.OldCsvDeletionTasklet;
import org.challenge.batch.utility.Utils;
import org.challenge.batch.writer.IstatItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class ChallengeBatchConfiguration {

	@Autowired
	public JobLauncher jobLauncher;

	String zipUrl = "https://www.istat.it/storage/codici-unita-amministrative/Elenco-codici-statistici-e-denominazioni-delle-unita-territoriali.zip";
	String path = "downloaded/";
	String csvPath = "downloaded/ISTAT" + Utils.getCurrentDate() + ".csv";

	private final IstatItemReader istatItemReader = new IstatItemReader(csvPath);

	@Autowired
	private IstatItemProcessor istatItemProcessor;

	@Autowired
	private IstatItemWriter istatItemWriter;

	@Bean
	public CsvExtractionTasklet csvExtractionTasklet() {
		return new CsvExtractionTasklet(zipUrl, path);
	}

	@Bean
	public OldCsvDeletionTasklet oldCsvDeletionTasklet() {
		return new OldCsvDeletionTasklet(path, csvPath.split("/")[1]);
	}

	@Bean
	public Step extractCsvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("extractCsvStep", jobRepository)
				.tasklet(csvExtractionTasklet(), transactionManager)
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	public Step populateDbStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		int chunkSize = 500;
		return new StepBuilder("populateDbStep", jobRepository)
				.<IstatCodiciEntity, IstatCodiciEntity>chunk(chunkSize, transactionManager)
				.reader(istatItemReader)
				.processor(istatItemProcessor)
				.writer(istatItemWriter)
				.listener(new CustomChunkListener(chunkSize))
				.listener(new CustomStepListener())
				.listener(new IstatItemReaderListener())
				.listener(new IstatItemProcessorListener())
				.listener(new IstatItemWriterListener())
				.listener(new ClassGenericSkipperLogger<IstatCodiciEntity>("populateDbStep"))
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	public Step deleteOldCsvsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("deleteOldCsvsStep", jobRepository)
				.tasklet(oldCsvDeletionTasklet(), transactionManager)
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	public Job challengeJob(JobRepository jobRepository, Step extractCsvStep, Step populateDbStep, Step deleteOldCsvsStep) {
		return new JobBuilder("challengeJob", jobRepository)
				.start(extractCsvStep)
				.next(populateDbStep)
				.next(deleteOldCsvsStep)
				.build();
	}

}

