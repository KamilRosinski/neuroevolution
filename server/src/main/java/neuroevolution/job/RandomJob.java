package neuroevolution.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.RandomService;
import neuroevolution.web.dto.GeneratorStartedDTO;
import neuroevolution.web.dto.MessageDTO;
import neuroevolution.web.dto.ValueGeneratedDTO;
import neuroevolution.web.websocket.WebSocketHandler;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class RandomJob implements Job {

	private final RandomService randomService;
	private final WebSocketHandler webSocketHandler;


	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		log.info("job started");

		final long range = context.getMergedJobDataMap().getLong("range");
		final String sessionId = context.getMergedJobDataMap().getString("sessionId");
		try {
			webSocketHandler.sendMessageToSession(sessionId, MessageDTO.of(new GeneratorStartedDTO(context.getJobDetail().getKey().getName())));
			long id = 0;
			while (true) {
				Thread.sleep(1000);
				++id;
				final long value = randomService.generate(range);
				log.info("new value: " + value);
				if (webSocketHandler.isSessionActive(sessionId)) {
					webSocketHandler.sendMessageToSession(sessionId, MessageDTO.of(new ValueGeneratedDTO(id, value)));
				} else {
					break;
				}
			}
			log.info("job finished");
		} catch (final InterruptedException | IOException exception) {
			throw new JobExecutionException(exception);
		}
	}

}
