package neuroevolution.random;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RandomUtils {

	public static double generateInRange(final double min, final double max) {
		return RandomGenerator.generate() * (max - min) + min;
	}

	public static boolean generateWithProbability(final double trueProbability) {
		return RandomGenerator.generate() <= trueProbability;
	}

	public static <T> T pick(final List<T> values) {
		return values.get((int) (generateInRange(0, values.size())));
	}

	public static <T> T pickWithProbability(final Map<T, Integer> valueWeightMap) {
		final Map<T, Integer> positiveElements = valueWeightMap.entrySet().stream().filter(entry -> entry.getValue().intValue() > 0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		if (!positiveElements.isEmpty()) {
			final double randomValue = generateInRange(0, positiveElements.values().stream().mapToInt(Integer::intValue).sum());
			int sum = 0;
			for (final Map.Entry<T, Integer> entry : positiveElements.entrySet()) {
				sum += entry.getValue().intValue();
				if (sum > randomValue) {
					return entry.getKey();
				}
			}
		}
		throw new RandomUtilsException("Weights must contain at least one positive value.");
	}

}
