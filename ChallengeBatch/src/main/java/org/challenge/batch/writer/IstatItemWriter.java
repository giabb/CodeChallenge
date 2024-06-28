package org.challenge.batch.writer;

import org.challenge.batch.model.IstatCodiciEntity;
import org.challenge.batch.repository.IstatCodiciRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IstatItemWriter implements ItemWriter<IstatCodiciEntity> {

	@Autowired
	private IstatCodiciRepository istatCodiciRepository;

	@Override
	public void write(Chunk<? extends IstatCodiciEntity> chunk) {
		istatCodiciRepository.saveAll(chunk);
	}
}
