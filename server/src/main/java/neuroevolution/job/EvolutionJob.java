package neuroevolution.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.EvolutionService;
import neuroevolution.web.dto.EvolutionStartedDTO;
import neuroevolution.web.dto.MessageDTO;
import neuroevolution.web.dto.NewGenerationDTO;
import neuroevolution.web.websocket.EvolutionWebSocketHandler;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class EvolutionJob implements InterruptableJob {

	private final EvolutionWebSocketHandler evolutionWebSocketHandler;
	private final EvolutionService evolutionService;

	private boolean interrupted = false;

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		log.info("job started");

		final long range = context.getMergedJobDataMap().getLong("range");
		final String sessionId = context.getMergedJobDataMap().getString("sessionId");
		try {
			evolutionWebSocketHandler.sendMessageToSession(sessionId, MessageDTO.of(new EvolutionStartedDTO(context.getFireInstanceId())));
			long id = 0;
			while (!interrupted) {
				Thread.sleep(1000);
				++id;
				final long value = evolutionService.nextGeneration(range);
				log.info("new generation: " + value);
				evolutionWebSocketHandler.sendMessageToSession(sessionId, MessageDTO.of(new NewGenerationDTO(id, value)));
			}
			evolutionWebSocketHandler.closeSession(sessionId);
			log.info("job finished, session closed");
		} catch (final InterruptedException | IOException exception) {
			throw new JobExecutionException(exception);
		}
	}

	@Override
	public void interrupt() {
		interrupted = true;
	}

}
