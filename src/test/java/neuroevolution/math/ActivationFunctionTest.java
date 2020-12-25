package neuroevolution.math;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ActivationFunctionTest {

	@ParameterizedTest
	@MethodSource("provideReLuTestData")
	void shouldTestReLuFunction(final double input, final double expectedResult) {
		// given
		final ValueModifier relu = ActivationFunction.RELU.getFunction();

		// when
		final double result = relu.apply(input);

		// then
		Assertions.assertThat(result).isEqualTo(expectedResult);
	}

	private static Stream<Arguments> provideReLuTestData() {
		return Stream.of(
				Arguments.of(Double.valueOf(-5), Double.valueOf(0)),
				Arguments.of(Double.valueOf(0), Double.valueOf(0)),
				Arguments.of(Double.valueOf(3), Double.valueOf(3))
		);
	}

}
