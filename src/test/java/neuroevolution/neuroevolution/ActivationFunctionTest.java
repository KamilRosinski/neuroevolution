package neuroevolution.neuroevolution;

import neuroevolution.neuralnetwork.ActivationFunction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ActivationFunctionTest {

    @ParameterizedTest
    @MethodSource("generateSigmoidTestData")
    void shouldApplySigmoidFunction(final double x, final double expected) {

        // given
        final ActivationFunction activationFunction = ActivationFunction.SIGMOID;

        // when
        final double result = activationFunction.getFunction().applyAsDouble(x);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> generateSigmoidTestData() {
        return Stream.of(
                Arguments.of(Double.valueOf(-Double.MAX_VALUE), Double.valueOf(0)),
                Arguments.of(Double.valueOf(0), Double.valueOf(0.5)),
                Arguments.of(Double.valueOf(Double.MAX_VALUE), Double.valueOf(1))
        );
    }

    @ParameterizedTest
    @MethodSource("generateReLuTestData")
    void shouldApplyReLuFunction(final double x, final double expected) {

        // given
        final ActivationFunction activationFunction = ActivationFunction.RE_LU;

        // when
        final double result = activationFunction.getFunction().applyAsDouble(x);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> generateReLuTestData() {
        return Stream.of(
              Arguments.of(Double.valueOf(-3), Double.valueOf(0)),
              Arguments.of(Double.valueOf(0), Double.valueOf(0)),
              Arguments.of(Double.valueOf(2.5), Double.valueOf(2.5))
        );
    }

}
