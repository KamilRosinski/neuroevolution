package neuroevolution.web.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.SchedulingService;
import neuroevolution.web.dto.ActionType;
import neuroevolution.web.dto.MessageDTO;
import neuroevolution.web.dto.MessageUtils;
import neuroevolution.web.dto.StartGeneratorDTO;
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
public class WebSocketHandler extends TextWebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final SchedulingService schedulingService;
	private final MessageUtils messageUtils;

	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) throws Exception {
		final String message = textMessage.getPayload();
		log.info("session: " + session.getId() + ", received: " + message);

		if (messageUtils.parseMessageAction(message) == ActionType.START_GENERATOR) {
			final StartGeneratorDTO request = messageUtils.parseMessageBody(message, StartGeneratorDTO.class);
			schedulingService.scheduleRandomJob(request.range, session.getId());
		}
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) {
		log.info("new session: " + session.getId());
		sessions.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
		log.info("session closed: " + session.getId() + "; status: " + status);
		sessions.remove(session.getId());
	}

	public void sendMessageToSession(final String sessionId, final MessageDTO message) throws IOException {
		sessions.get(sessionId).sendMessage(new TextMessage(messageUtils.stringifyMessage(message)));
	}

	public boolean isSessionActive(final String sessionId) {
		final WebSocketSession session = sessions.get(sessionId);
		return session != null && session.isOpen();
	}

}
