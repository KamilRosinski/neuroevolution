package neuroevolution.random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RandomGeneratorTest {

    @Mock
    private LinearCongruentialGenerator randomSource;

    @InjectMocks
    private RandomGenerator randomGenerator;

    @Test
    void shouldReturnSeed() {

        // given
        final long seed = 42;

        Mockito.when(randomSource.getSeed()).thenReturn(seed);

        // when
        final long result = randomGenerator.getSeed();

        // then
        Assertions.assertThat(result).isEqualTo(seed);
    }

    @Test
    void shouldGenerateUniform() {

        // given
        final int upperBound = 3;

        Mockito.when(randomSource.nextBits(31)).thenReturn(7);

        // when
        final double result = randomGenerator.generateInt(upperBound);

        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void shouldFailToGenerateUniformWhenUpperBoundIsNegative() {

        // given
        final int upperBound = -1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                randomGenerator.generateInt(upperBound);
            }).isInstanceOf(RandomException.class)
            .hasMessage("Upper bound must be non-negative, but was -1.");
    }

    @Test
    void shouldGenerateDouble() {

        // given
        Mockito.when(randomSource.nextBits(26)).thenReturn(0b00000011001010100111100100101001);
        Mockito.when(randomSource.nextBits(27)).thenReturn(0b00000101010101111010010100100000);

        // when
        final double result = randomGenerator.generateDouble();

        // then
        final double expected = (double) 0b0000000000011001010100111100100101001101010101111010010100100000L / (1L << 53);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldGenerateGaussianPair() {

        // given
        final double mean = 1;
        final double standardDeviation = 0.5;

        Mockito.when(randomSource.nextBits(26)).thenReturn(
            0b01010010010101011000101011,
            0b00000000000000000000000000,
            0b11011000101000001101011111
        );
        Mockito.when(randomSource.nextBits(27)).thenReturn(
            0b101010010101010101010010101,
            0b000000000000000000000000000,
            0b010011110101000110100001101
        );

        // when
        final double result1 = randomGenerator.generateGaussian(mean, standardDeviation);
        final double result2 = randomGenerator.generateGaussian(mean, standardDeviation);

        // then
        final double u1 = (double) 0b01010010010101011000101011101010010101010101010010101L / (1L << 53);
        final double u2 = (double) 0b11011000101000001101011111010011110101000110100001101L / (1L << 53);
        final double r = Math.sqrt(-2 * Math.log(u1));
        final double theta = 2 * Math.PI * u2;
        Assertions.assertThat(result1).isEqualTo(0.5 * r * Math.cos(theta) + 1);
        Assertions.assertThat(result2).isEqualTo(0.5 * r * Math.sin(theta) + 1);
    }

    @Test
    void shouldFailToGenerateGaussianWhenStandardDeviationIsNegative() {

        // given
        final double mean = 0;
        final double standardDeviation = -0.5;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                randomGenerator.generateGaussian(mean, standardDeviation);
            }).isInstanceOf(RandomException.class)
            .hasMessage("Standard deviation must be non-negative, but was -0.5.");
    }

}
