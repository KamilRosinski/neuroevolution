package neuroevolution.web.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO<T> {

	public ActionType action;
	public T body;

	public static MessageDTO<NewGenerationDTO> of(final NewGenerationDTO dto) {
		return new MessageDTO<>(ActionType.NEW_GENERATION, dto);
	}

	public static MessageDTO<EvolutionStartedDTO> of(final EvolutionStartedDTO dto) {
		return new MessageDTO<>(ActionType.EVOLUTION_STARTED, dto);
	}

}
