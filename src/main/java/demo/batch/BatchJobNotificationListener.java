package demo.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class BatchJobNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(BatchJobNotificationListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.STARTED) {
			log.info("!! JOB STARTED !!");
		} else {
			log.info("!! JOB STATUS : " + jobExecution.getStatus());
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!! JOB FINISHED !!");
		} else {
			log.info("!! JOB STATUS : " + jobExecution.getStatus());
		}
	}
}
