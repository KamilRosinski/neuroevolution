package neuroevolution.logic;

import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;

public interface EvolutionSchedulingService {

	void scheduleEvolutionJob(long range, String sessionId) throws SchedulerException;

	void interruptEvolution(String evolutionId) throws UnableToInterruptJobException;

}
