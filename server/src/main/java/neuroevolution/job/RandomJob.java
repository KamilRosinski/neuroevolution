package neuroevolution.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.RandomService;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@RequiredArgsConstructor
public class RandomJob implements InterruptableJob {

	private final RandomService randomService;
	private boolean interrupted = false;

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		log.info("job started");
		final long range = context.getMergedJobDataMap().getLong("range");
		try {
			while (!interrupted) {
				Thread.sleep(1000);
				log.info("random value: " + randomService.generate(range));
			}
		} catch (final InterruptedException exception) {
			throw new JobExecutionException(exception);
		}
		log.info("job finished");
	}

	@Override
	public void interrupt() {
		log.info("stopping job");
		interrupted = true;
	}

}
