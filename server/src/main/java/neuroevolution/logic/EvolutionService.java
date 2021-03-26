package neuroevolution.logic;

import neuroevolution.web.dto.GenerationDto;
import neuroevolution.web.dto.SettingsDto;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public interface EvolutionService {

	void evolve(SettingsDto settings, BooleanSupplier stopCondition, Consumer<GenerationDto> updateNotification, Consumer<String> errorNotification);

}
