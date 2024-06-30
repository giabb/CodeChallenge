package org.challenge.batch.processor;

import org.challenge.batch.model.IstatCodiciEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class IstatItemProcessor implements ItemProcessor<IstatCodiciEntity, IstatCodiciEntity> {

	@Override
	public IstatCodiciEntity process(IstatCodiciEntity item) {
		// According to istat, cod_catasto cannot be null and it is unique
		if (item.getCodCatasto() == null) return null;
		// Otherwise the item is going to be returned to the writer
		return item;
	}

}
