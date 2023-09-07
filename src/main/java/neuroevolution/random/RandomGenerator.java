package neuroevolution.random;

import java.util.OptionalDouble;

public class RandomGenerator {

    public static final long DOUBLE_BASE = 1L << Double.PRECISION;
    private static final int DOUBLE_1ST_HALF_LENGTH = Double.PRECISION / 2;
    private static final int DOUBLE_2ND_HALF_LENGTH = Double.PRECISION - DOUBLE_1ST_HALF_LENGTH;
    private static final int NON_NEGATIVE_INTEGER_LENGTH = Integer.SIZE - 1;

    private final RandomSource randomSource;

    private OptionalDouble nextGaussian = OptionalDouble.empty();

    public RandomGenerator(final RandomSource randomSource) {
        this.randomSource = randomSource;
    }

    public long getSeed() {
        return randomSource.getSeed();
    }

    public int generateInt(final int upperBound) {
        if (upperBound < 0) {
            throw new RandomException("Upper bound must be non-negative, but was %d.".formatted(Integer.valueOf(upperBound)));
        }

        return generateNonNegativeInt() % upperBound;
    }

    private int generateNonNegativeInt() {
        return randomSource.nextBits(NON_NEGATIVE_INTEGER_LENGTH);
    }

    public double generateGaussian(final double mean, final double standardDeviation) {
        if (standardDeviation < 0) {
            throw new RandomException("Standard deviation must be non-negative, but was %s."
                .formatted(Double.valueOf(standardDeviation)));
        }

        final double currentGaussian;
        if (nextGaussian.isPresent()) {
            currentGaussian = nextGaussian.getAsDouble();
            nextGaussian = OptionalDouble.empty();
        } else {
            final double u1 = generateNonZeroDouble(), u2 = generateNonZeroDouble();
            final double r = Math.sqrt(-2 * Math.log(u1)), theta = 2 * Math.PI * u2;
            currentGaussian = r * Math.cos(theta);
            nextGaussian = OptionalDouble.of(r * Math.sin(theta));
        }

        return standardDeviation * currentGaussian + mean;
    }

    private double generateNonZeroDouble() {
        double result;
        do {
            result = generateDouble();
        } while (result == 0);
        return result;
    }

    public double generateDouble() {
        final long firstHalf = randomSource.nextBits(DOUBLE_1ST_HALF_LENGTH);
        final long secondHalf = randomSource.nextBits(DOUBLE_2ND_HALF_LENGTH);
        return (double) ((firstHalf << DOUBLE_2ND_HALF_LENGTH) | secondHalf) / DOUBLE_BASE;
    }

}
