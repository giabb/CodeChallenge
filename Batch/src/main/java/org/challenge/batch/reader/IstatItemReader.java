package org.challenge.batch.reader;

import org.challenge.batch.model.IstatCodiciEntity;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.FileSystemResource;

/**
 * This class implements the reader for the istat csv file
 */
public class IstatItemReader extends FlatFileItemReader<IstatCodiciEntity> {

	/**
	 * Constructor to create reader for istat csv file downloaded in previous step
	 *
	 * @param csvPath is the path of the csv file
	 */
	public IstatItemReader(String csvPath) {
		this.setEncoding("Cp1252"); // Read non-latin characters
		this.setResource(new FileSystemResource(csvPath));
		this.setLineMapper(new IstatItemMapper());
		this.setLinesToSkip(3); // Skip the header
		this.setStrict(false);
	}

	public static class IstatItemMapper implements LineMapper<IstatCodiciEntity> {

		private final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		private final BeanWrapperFieldSetMapper<IstatCodiciEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

		public IstatItemMapper() {
			tokenizer.setNames("codRegione", "codUts", "codProvincia", "progComune", "codComuneAlfanum", "denomBilingue", "denomIta", "denomAltra", "codRipGeo", "ripartGeo", "denomRegione", "denomUts", "tipoUts", "flagCapoMetroCons", "siglaAuto", "codComuneNum", "codComune110", "codComune107", "codComune103", "codCatasto", "nuts12021", "nuts22021", "nuts32021", "nuts12024", "nuts22024", "nuts32024");
			tokenizer.setDelimiter(";");
			fieldSetMapper.setTargetType(IstatCodiciEntity.class);
		}

		@Override
		public IstatCodiciEntity mapLine(String line, int lineNumber) throws Exception {
			FieldSet fieldSet = tokenizer.tokenize(line);
			return fieldSetMapper.mapFieldSet(fieldSet);
		}
	}
}
