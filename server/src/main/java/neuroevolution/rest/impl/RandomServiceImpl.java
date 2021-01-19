package neuroevolution.rest.impl;

import lombok.extern.slf4j.Slf4j;
import neuroevolution.rest.RandomService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RandomServiceImpl implements RandomService {

	@Override
	public long generate(final long range) {
		return Math.round(Math.random() * range);
	}

}
