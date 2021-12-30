package demo.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import demo.batch.BatchJobNotificationListener;
import demo.batch.Record;
import demo.batch.step.BatchItemProcessor;

@Configuration
public class FileToDBJobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public ItemReader<Record> fileReader() {
		return new FlatFileItemReaderBuilder<Record>()//
				.name("personItemReader")//
				.resource(new ClassPathResource("input-data.csv"))//
				.delimited()//
				.names(new String[] { "field1", "field2" })//
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Record>() {
					{
						setTargetType(Record.class);
					}
				})//
				.build();
	}

	@Bean
	public BatchItemProcessor fileProcessor() {
		return new BatchItemProcessor();
	}

	@Bean
	public ItemWriter<Record> dbWriter() {
		return new JdbcBatchItemWriterBuilder<Record>()//
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())//
				.sql("INSERT INTO records (field1, field2) VALUES (:field1, :field2)")//
				.dataSource(dataSource)//
				.build();
	}

	@Bean
	public Job fileToDBJob(BatchJobNotificationListener listener) {
		return jobBuilderFactory.get("fileToDB")//
				.incrementer(new RunIdIncrementer())//
				.listener(listener)//
				.flow(fileToDBStep())//
				.end()//
				.build();
	}

	@Bean
	public Step fileToDBStep() {
		return stepBuilderFactory.get("fileToDBStep")//
				.<Record, Record>chunk(10)//
				.reader(fileReader())//
				.processor(fileProcessor())//
				.writer(dbWriter())//
				.build();
	}

}
