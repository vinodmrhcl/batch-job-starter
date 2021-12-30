package demo.batch.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import demo.batch.Record;

public class BatchItemProcessor implements ItemProcessor<Record, Record> {

	private static final Logger log = LoggerFactory.getLogger(BatchItemProcessor.class);

	@Override
	public Record process(Record input) throws Exception {

		log.info("Input : " + input);

		// Business Logic Start

		Record output = input;

		// Business Logic End

		log.info("Output : " + output);

		return output;
	}

}
