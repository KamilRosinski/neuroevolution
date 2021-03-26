package neuroevolution.logic.impl;

import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.EvolutionService;
import neuroevolution.web.dto.GenerationDto;
import neuroevolution.web.dto.SettingsDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Slf4j
@Service
public class EvolutionServiceImpl implements EvolutionService {


	@Override
	@Async
	public void evolve(final SettingsDto settings, final BooleanSupplier stopCondition, final Consumer<GenerationDto> updateNotification,
					   final Consumer<String> errorNotification) {

		final long range = settings.range;

		long generationId = 0;

		try {
			while (!stopCondition.getAsBoolean()) {
				Thread.sleep(1000);
				if (range == 42 && generationId == 5) {
					throw new Exception("range == 42 && generationId == 5");
				}
				final long score = Math.round(Math.random() * range);
				updateNotification.accept(new GenerationDto(generationId++, score));
			}
		} catch (final Exception exception) {
			errorNotification.accept(exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

}
