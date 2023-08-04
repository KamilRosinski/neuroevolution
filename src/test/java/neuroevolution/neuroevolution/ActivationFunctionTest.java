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
        final double result = activationFunction.apply(x);

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

}
