package neuroevolution.logic;

import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;

public interface SchedulingService {

	String scheduleRandomJob(long range, String sessionId) throws SchedulerException;

	void interruptRandomJob(String jobId) throws UnableToInterruptJobException;

}
