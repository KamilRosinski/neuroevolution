package neuroevolution.logic.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.job.RandomJob;
import neuroevolution.logic.SchedulingService;
import neuroevolution.web.dto.GeneratorStartedDTO;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.UnableToInterruptJobException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class SchedulingServiceImpl implements SchedulingService {

	private final Scheduler scheduler;

	@Override
	public String scheduleRandomJob(final long range, final String sessionId) throws SchedulerException {
		final JobDetail jobDetail = JobBuilder.newJob()
				.ofType(RandomJob.class)
				.withIdentity(UUID.randomUUID().toString(), "random")
				.usingJobData("range", Long.valueOf(range))
				.usingJobData("sessionId", sessionId)
				.build();

		final Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
		log.info("scheduled job: " + jobDetail.getKey());

		return jobDetail.getKey().getName();

	}

	@Override
	public void interruptRandomJob(final String jobId) throws UnableToInterruptJobException {
		final JobKey jobKey = JobKey.jobKey(jobId, "random");
		log.info("interrupting job: " + jobKey);
		scheduler.interrupt(jobKey);
	}
}
