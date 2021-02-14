package neuroevolution.logic.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.job.EvolutionJob;
import neuroevolution.logic.EvolutionSchedulingService;
import neuroevolution.web.dto.NewGenerationDTO;
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

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Slf4j
@AllArgsConstructor
public class EvolutionSchedulingServiceImpl implements EvolutionSchedulingService {

	private static final String EVOLUTION_JOB_GROUP = "evolution";

	private final Scheduler scheduler;

	@Override
	public void scheduleEvolutionJob(final long range, final String sessionId) throws SchedulerException {
		final JobDetail jobDetail = JobBuilder.newJob()
				.ofType(EvolutionJob.class)
				.withIdentity(UUID.randomUUID().toString(), EVOLUTION_JOB_GROUP)
				.usingJobData("range", Long.valueOf(range))
				.usingJobData("sessionId", sessionId)
				.build();

		final Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
		log.info("scheduled job: " + jobDetail.getKey());
	}

	@Override
	public void interruptEvolution(final String evolutionId) throws UnableToInterruptJobException {
		scheduler.interrupt(evolutionId);
	}

}
