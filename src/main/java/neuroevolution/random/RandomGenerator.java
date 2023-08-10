package neuroevolution.random;

import java.util.Random;

public class RandomGenerator {

    private final Random random;

    public RandomGenerator(final Random random) {
        this.random = random;
    }

    public double generateUniform(final double lowerBound, final double upperBound) {
        if (lowerBound > upperBound) {
            throw new RandomException("Cannot generate uniform number. Lower bound: %s, upper bound: %s."
                .formatted(Double.valueOf(lowerBound), Double.valueOf(upperBound)));
        }

        return random.nextDouble(lowerBound, upperBound);
    }

    public double generateGaussian(final double mean, final double standardDeviation) {
        if (standardDeviation < 0) {
            throw new RandomException("Cannot generate gaussian number. Standard deviation must be non-negative but was %s."
                .formatted(Double.valueOf(standardDeviation)));
        }

        return random.nextGaussian(mean, standardDeviation);
    }

}
