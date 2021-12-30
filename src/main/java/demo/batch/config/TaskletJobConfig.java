package demo.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.batch.BatchJobNotificationListener;
import demo.batch.step.CustomerTasklet;

@Configuration
public class TaskletJobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Tasklet customTasklet() {
		return new CustomerTasklet();
	}

	@Bean
	public Job taskletJob(BatchJobNotificationListener listener) {
		return jobBuilderFactory.get("job")//
				.incrementer(new RunIdIncrementer())//
				.listener(listener)//
				.flow(taskletStep())//
				.end()//
				.build();
	}

	public Step taskletStep() {
		return stepBuilderFactory.get("taskletStep")//
				.tasklet(customTasklet())//
				.build();
	}

}
