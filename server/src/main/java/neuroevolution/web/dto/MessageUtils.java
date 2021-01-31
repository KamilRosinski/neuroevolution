package neuroevolution.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

	public ActionType parseMessageAction(final String message) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode action = mapper.readTree(message).get("action");
		return ActionType.valueOf(action.asText());
	}

	public <T> T parseMessageBody(final String message, final Class<T> clazz) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode body = mapper.readTree(message).get("body");
		return mapper.treeToValue(body, clazz);
	}

	public String stringifyMessage(final MessageDTO message) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(message);
	}

}
