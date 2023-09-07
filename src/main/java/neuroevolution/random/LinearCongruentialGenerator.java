package neuroevolution.random;

public class LinearCongruentialGenerator implements RandomSource {

    private static final long MULTIPLIER = 25214903917L;
    private static final long INCREMENT = 11;
    private static final long MASK_LENGTH = 48;
    private static final long MASK = (1L << MASK_LENGTH) - 1;

    private long seed;

    public LinearCongruentialGenerator(final long seed) {
        this.seed = seed;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public int nextBits(int numberOfBits) {
        if (numberOfBits < 0) {
            throw new RandomException("Number of bits must be non-negative, but was %d."
                .formatted(Integer.valueOf(numberOfBits)));
        }

        if (numberOfBits > Integer.SIZE) {
            throw new RandomException("Number of bits must not exceed %d, but was %d."
                .formatted(Integer.valueOf(Integer.SIZE), Integer.valueOf(numberOfBits)));
        }

        seed = (MULTIPLIER * seed + INCREMENT) & MASK;
        return (int) (seed >>> (MASK_LENGTH - numberOfBits));
    }
}
