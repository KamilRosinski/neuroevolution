package neuroevolution.random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class LinearCongruentialGeneratorTest {

    @ParameterizedTest
    @MethodSource("generateRandomBitsTestData")
    void shouldGenerateRandomBits(final long initialSeed, final int numberOfBits, final int expectedResult,
                                  final long expectedNextSeed) {

        // given
        final RandomSource randomSource = new LinearCongruentialGenerator(initialSeed);

        // when
        final int result = randomSource.nextBits(numberOfBits);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
        Assertions.assertThat(randomSource.getSeed()).isEqualTo(expectedNextSeed);
    }

    private static Stream<Arguments> generateRandomBitsTestData() {
        return Stream.of(
            Arguments.of(0x123456789abcL, 32, 0x1902d9ae, 0x1902d9aeca17L),
            Arguments.of(0x123456789abcL, 16, 0x00001902, 0x1902d9aeca17L),
            Arguments.of(0x123456789abcL, 10, 0x00000064, 0x1902d9aeca17L),
            Arguments.of(0x123456789abcL, 0, 0x00000000, 0x1902d9aeca17L)
        );
    }

    @Test
    void shouldFailToGenerateNegativeNumberOfBits() {

        // given
        final RandomSource randomSource = new LinearCongruentialGenerator(42);
        final int numberOfBits = -1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                randomSource.nextBits(numberOfBits);
            }).isInstanceOf(RandomException.class)
            .hasMessage("Number of bits must be non-negative, but was -1.");
    }

    @Test
    void shouldFailToGenerateTooManyBits() {

        // given
        final RandomSource randomSource = new LinearCongruentialGenerator(42);
        final int numberOfBits = 33;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                randomSource.nextBits(numberOfBits);
            }).isInstanceOf(RandomException.class)
            .hasMessage("Number of bits must not exceed 32, but was 33.");
    }

}
