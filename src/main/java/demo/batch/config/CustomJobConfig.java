package demo.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.batch.BatchJobNotificationListener;
import demo.batch.Record;
import demo.batch.step.BatchItemProcessor;
import demo.batch.step.CustomReader;
import demo.batch.step.CustomWriter;

@Configuration
public class CustomJobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<Record> customReader() {
		return new CustomReader<Record>();
	}

	@Bean
	public BatchItemProcessor customProcessor() {
		return new BatchItemProcessor();
	}

	@Bean
	public ItemWriter<Record> customWriter() {
		return new CustomWriter<Record>();
	}

	@Bean
	public Job customJob(BatchJobNotificationListener listener) {
		return jobBuilderFactory.get("customJob")//
				.incrementer(new RunIdIncrementer())//
				.listener(listener)//
				.flow(customStep())//
				.end()//
				.build();
	}

	@Bean
	public Step customStep() {
		return stepBuilderFactory.get("customStep")//
				.<Record, Record>chunk(10)//
				.reader(customReader())//
				.processor(customProcessor())//
				.writer(customWriter())//
				.build();
	}

}
