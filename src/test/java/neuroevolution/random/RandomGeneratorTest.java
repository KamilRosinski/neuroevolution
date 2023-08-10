package neuroevolution.random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class RandomGeneratorTest {

    @Mock
    private Random random;

    @InjectMocks
    private RandomGenerator randomGenerator;

    @Test
    void shouldGenerateUniform() {

        // given
        final double lowerBound = -1;
        final double upperBound = 2;

        Mockito.when(Double.valueOf(random.nextDouble(lowerBound, upperBound))).thenReturn(Double.valueOf(1.5));

        // when
        final double result = randomGenerator.generateUniform(lowerBound, upperBound);

        // then
        Assertions.assertThat(result).isEqualTo(1.5);
    }

    @Test
    void shouldFailToGenerateUniformWhenLowerBoundIsGreaterThanUpperBound() {

        // given
        final double lowerBound = 0.5;
        final double upperBound = -1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                randomGenerator.generateUniform(lowerBound, upperBound);
            }).isInstanceOf(RandomException.class)
            .hasMessage("Cannot generate uniform number. Lower bound: 0.5, upper bound: -1.0.");
    }

    @Test
    void shouldGenerateGaussian() {

        // given
        final double mean = 1;
        final double standardDeviation = 0.5;

        Mockito.when(Double.valueOf(random.nextGaussian(mean, standardDeviation))).thenReturn(Double.valueOf(1.1));

        // when
        final double result = randomGenerator.generateGaussian(mean, standardDeviation);

        // then
        Assertions.assertThat(result).isEqualTo(1.1);
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
            .hasMessage("Cannot generate gaussian number. Standard deviation must be non-negative but was -0.5.");
    }

}
