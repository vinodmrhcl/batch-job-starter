package demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class BatchApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("fileToDBJob")
	private Job fileToDBJob;

	@Autowired
	@Qualifier("customJob")
	private Job customJob;

	@Autowired
	@Qualifier("taskletJob")
	private Job taskletJob;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void fileToDBJob() throws Exception {
		JobParameters params = new JobParametersBuilder().//
				addString("JobID", String.valueOf(System.currentTimeMillis())).//
				toJobParameters();
		jobLauncher.run(fileToDBJob, params);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void customJob() throws Exception {
		JobParameters params = new JobParametersBuilder().//
				addString("JobID", String.valueOf(System.currentTimeMillis())).//
				toJobParameters();
		jobLauncher.run(customJob, params);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void taskletJob() throws Exception {
		JobParameters params = new JobParametersBuilder().//
				addString("JobID", String.valueOf(System.currentTimeMillis())).//
				toJobParameters();
		jobLauncher.run(taskletJob, params);
	}
}
