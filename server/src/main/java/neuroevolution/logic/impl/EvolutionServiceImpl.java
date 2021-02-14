package neuroevolution.logic.impl;

import lombok.extern.slf4j.Slf4j;
import neuroevolution.logic.EvolutionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EvolutionServiceImpl implements EvolutionService {

	@Override
	public long nextGeneration(final long range) {
		return Math.round(Math.random() * range);
	}

}
