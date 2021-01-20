package neuroevolution.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.rest.dto.GeneratorStartedDTO;
import neuroevolution.rest.dto.StartGeneratorDTO;
import neuroevolution.rest.dto.StopGeneratorDTO;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.UnableToInterruptJobException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/random")
@Slf4j
@RequiredArgsConstructor
public class RandomController {

	private final Scheduler scheduler;

	@PostMapping("/start")
	public GeneratorStartedDTO startGenerator(@RequestBody final StartGeneratorDTO request) throws SchedulerException {
		final JobDetail jobDetail = JobBuilder.newJob()
				.ofType(RandomJob.class)
				.withIdentity(UUID.randomUUID().toString(), "random")
				.usingJobData("range", Long.valueOf(request.getRange()))
				.build();

		final Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
		log.info("scheduled job: " + jobDetail.getKey());

		return new GeneratorStartedDTO(jobDetail.getKey().getName());
	}

	@PostMapping("/stop")
	public void stopGenerator(@RequestBody final StopGeneratorDTO request) throws UnableToInterruptJobException {
		final JobKey jobKey = JobKey.jobKey(request.getJobId(), "random");
		log.info("interrupting job: " + jobKey);
		scheduler.interrupt(jobKey);
	}


}
