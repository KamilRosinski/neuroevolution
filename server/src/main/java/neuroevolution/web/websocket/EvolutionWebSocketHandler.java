package neuroevolution.web.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.EvolutionService;
import neuroevolution.web.dto.GenerationDto;
import neuroevolution.web.dto.SettingsDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class EvolutionWebSocketHandler extends TextWebSocketHandler {

	private final EvolutionService evolutionService;

	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) throws Exception {
		final String message = textMessage.getPayload();

		final SettingsDto settings = new ObjectMapper().readValue(message, SettingsDto.class);
		evolutionService.evolve(
				settings,
				() -> !session.isOpen(),
				generationDto -> sentMessage(session, generationDto),
				errorDto -> sentMessage(session, errorDto));
	}

	private void sentMessage(final WebSocketSession session, final GenerationDto generationDto) {
		try {
			session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(generationDto)));
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void sentMessage(final WebSocketSession session, final String message) {
		try {
			session.close(CloseStatus.SERVER_ERROR.withReason(message));
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
