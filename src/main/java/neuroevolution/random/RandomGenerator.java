package neuroevolution.random;

import java.util.Random;

public class RandomGenerator {

	private final static Random RANDOM = new Random();

	public static double generate() {
		return RANDOM.nextDouble();
	}

}
