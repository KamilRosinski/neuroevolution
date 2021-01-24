package neuroevolution.web.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.SchedulingService;
import neuroevolution.web.dto.ActionType;
import neuroevolution.web.dto.StartGeneratorDTO;
import neuroevolution.web.dto.StopGeneratorDTO;
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

	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
		log.info("received: " + message.getPayload());

		final ObjectMapper objectMapper = new ObjectMapper();
		final JsonNode tree = objectMapper.readTree(message.getPayload());
		switch (ActionType.valueOf(tree.get("action").asText())) {
			case START_GENERATOR -> {
				final StartGeneratorDTO request = objectMapper.treeToValue(tree.get("body"), StartGeneratorDTO.class);
				schedulingService.scheduleRandomJob(request.range, session.getId());
			}
			case STOP_GENERATOR -> {
				final StopGeneratorDTO request = objectMapper.treeToValue(tree.get("body"), StopGeneratorDTO.class);
				schedulingService.interruptRandomJob(request.jobId);
			}
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

	public void sendMessageToSession(final String sessionId, final Object message) throws IOException {
		sessions.get(sessionId).sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(message)));
	}

}
