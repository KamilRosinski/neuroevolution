package neuroevolution.web.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	public ActionType action;
	public Object body;

	public static MessageDTO of(final GeneratorStartedDTO dto) {
		return new MessageDTO(ActionType.GENERATOR_STARTED, dto);
	}

	public static MessageDTO of(final ValueGeneratedDTO dto) {
		return new MessageDTO(ActionType.VALUE_GENERATED, dto);
	}

}
