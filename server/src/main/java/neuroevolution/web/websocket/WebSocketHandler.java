package neuroevolution.web.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

//	private final Map<String, WebSocketSession> sessions = new HashMap();

	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
		log.info("received: " + message.getPayload());
		session.sendMessage(message);
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
		log.info("new session: " + session.getId());
//		sessions.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
		log.info("session closed: " + session.getId() + "; status: " + status);
	}
}
