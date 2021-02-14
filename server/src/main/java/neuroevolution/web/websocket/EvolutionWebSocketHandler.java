package neuroevolution.web.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.EvolutionSchedulingService;
import neuroevolution.web.dto.ActionType;
import neuroevolution.web.dto.MessageDTO;
import neuroevolution.web.dto.MessageUtils;
import neuroevolution.web.dto.StartEvolutionDTO;
import neuroevolution.web.dto.StopEvolutionDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class EvolutionWebSocketHandler extends TextWebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final EvolutionSchedulingService evolutionSchedulingService;
	private final MessageUtils messageUtils;

	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) throws Exception {
		final String message = textMessage.getPayload();
		log.info("session: " + session.getId() + ", received: " + message);

		final ActionType actionType = messageUtils.parseMessageAction(message);
		if (actionType == ActionType.START_EVOLUTION) {
			final StartEvolutionDTO request = messageUtils.parseMessageBody(message, StartEvolutionDTO.class);
			evolutionSchedulingService.scheduleEvolutionJob(request.range, session.getId());
		} else if (actionType == ActionType.STOP_EVOLUTION) {
			final StopEvolutionDTO request = messageUtils.parseMessageBody(message, StopEvolutionDTO.class);
			evolutionSchedulingService.interruptEvolution(request.evolutionId);
		}
	}

	public void sendMessageToSession(final String sessionId, final MessageDTO<?> message) throws IOException {
		sessions.get(sessionId).sendMessage(new TextMessage(messageUtils.stringifyMessage(message)));
	}

	public void closeSession(final String sessionId) throws IOException {
		sessions.get(sessionId).close();
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) {
		sessions.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
		sessions.remove(session.getId());
	}
}
