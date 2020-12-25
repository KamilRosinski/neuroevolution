package neuroevolution.random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomUtilsTest {

	@ParameterizedTest
	@MethodSource("provideRandomValuesForRange")
	void shouldGenerateRandomNumberInRange(final Double generatedRandom, final double expectedResult) {
		try (final MockedStatic<RandomGenerator> randomGeneratorMock = Mockito.mockStatic(RandomGenerator.class)) {
			// given
			final int min = -1;
			final int max = 3;

			randomGeneratorMock.when(RandomGenerator::generate).thenReturn(generatedRandom);

			// when
			final double result = RandomUtils.generateInRange(min, max);

			Assertions.assertThat(result).isEqualTo(expectedResult);
			randomGeneratorMock.verify(RandomGenerator::generate);
		}
	}

	private static Stream<Arguments> provideRandomValuesForRange() {
		return Stream.of(
				Arguments.of(Double.valueOf(0), Double.valueOf(-1)),
				Arguments.of(Double.valueOf(0.5), Double.valueOf(1)),
				Arguments.of(Double.valueOf(0.75), Double.valueOf(2))
		);
	}

	@ParameterizedTest
	@MethodSource("provideRandomValuesForProbability")
	void shouldGenerateBooleanValueWithGivenProbability(final Double generatedRandom, final boolean expectedResult) {
		try (final MockedStatic<RandomGenerator> randomGeneratorMock = Mockito.mockStatic(RandomGenerator.class)) {
			// given
			final double trueProbability = 0.75;

			randomGeneratorMock.when(RandomGenerator::generate).thenReturn(generatedRandom);

			// when
			final boolean result = RandomUtils.generateWithProbability(trueProbability);

			Assertions.assertThat(result).isEqualTo(expectedResult);
			randomGeneratorMock.verify(RandomGenerator::generate);
		}
	}

	private static Stream<Arguments> provideRandomValuesForProbability() {
		return Stream.of(
				Arguments.of(Double.valueOf(0.25), Boolean.valueOf(true)),
				Arguments.of(Double.valueOf(0.75), Boolean.valueOf(true)),
				Arguments.of(Double.valueOf(0.8), Boolean.valueOf(false))
		);
	}

	@ParameterizedTest
	@MethodSource("provideRandomValuesForPick")
	void shouldPickRandomElementFromArray(final Double generatedRandom, final int expectedIndex) {
		try (final MockedStatic<RandomGenerator> randomGeneratorMock = Mockito.mockStatic(RandomGenerator.class)) {
			// given
			final List<String> strings = Arrays.asList("01", "02", "03", "04");

			randomGeneratorMock.when(RandomGenerator::generate).thenReturn(generatedRandom);

			// when
			final String result = RandomUtils.pick(strings);

			Assertions.assertThat(result).isEqualTo(strings.get(expectedIndex));
			randomGeneratorMock.verify(RandomGenerator::generate);
		}
	}

	private static Stream<Arguments> provideRandomValuesForPick() {
		return Stream.of(
				Arguments.of(Double.valueOf(0), Integer.valueOf(0)),
				Arguments.of(Double.valueOf(0.3), Integer.valueOf(1)),
				Arguments.of(Double.valueOf(0.5), Integer.valueOf(2)),
				Arguments.of(Double.valueOf(0.9), Integer.valueOf(3))
		);
	}

	@ParameterizedTest
	@MethodSource("provideRandomValuesForPickWithProbability")
	void shouldPickValueWithGivenProbability(final Double generatedRandom, final String expectedResult) {
		try (final MockedStatic<RandomGenerator> randomGeneratorMock = Mockito.mockStatic(RandomGenerator.class)) {
			// given
			final Map<String, Integer> inputMap = new HashMap<>();
			inputMap.put("A", Integer.valueOf(1));
			inputMap.put("B", Integer.valueOf(2));
			inputMap.put("C", Integer.valueOf(0));
			inputMap.put("D", Integer.valueOf(3));

			randomGeneratorMock.when(RandomGenerator::generate).thenReturn(generatedRandom);

			// when
			final String result = RandomUtils.pickWithProbability(inputMap);

			// then
			Assertions.assertThat(result).isEqualTo(expectedResult);
			randomGeneratorMock.verify(RandomGenerator::generate);
		}
	}

	private static Stream<Arguments> provideRandomValuesForPickWithProbability() {
		return Stream.of(
				Arguments.of(Double.valueOf(0.0), "A"),
				Arguments.of(Double.valueOf(0.2), "B"),
				Arguments.of(Double.valueOf(0.4), "B"),
				Arguments.of(Double.valueOf(0.5), "D"),
				Arguments.of(Double.valueOf(0.8), "D")
		);
	}

	@Test
	void shouldThrowExceptionWhenNoPositiveWeight() {
		try (final MockedStatic<RandomGenerator> randomGeneratorMock = Mockito.mockStatic(RandomGenerator.class)) {
			// given
			final Map<String, Integer> inputMap = new HashMap<>();
			inputMap.put("A", Integer.valueOf(-1));
			inputMap.put("B", Integer.valueOf(0));

			// when
			final RandomUtilsException result = Assertions.catchThrowableOfType(() -> RandomUtils.pickWithProbability(inputMap), RandomUtilsException.class);

			// then
			Assertions.assertThat(result).isNotNull();
			Assertions.assertThat(result.getMessage()).isEqualTo("Weights must contain at least one positive value.");
			randomGeneratorMock.verifyNoInteractions();
		}
	}

}
