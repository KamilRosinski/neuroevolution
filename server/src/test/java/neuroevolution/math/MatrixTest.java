package neuroevolution.math;

import neuroevolution.exception.InvalidDimensionsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MatrixTest {

	@Test
	void shouldInitializeMatrixAndAccessItsValues() {
		// given
		final int height = 2;
		final int width = 3;
		final float[][] elements = {{1, 2, 3}, {-4, -5, -6}};

		// when
		final Matrix result = new Matrix(height, width, (row, column) -> elements[row][column]);

		// then
		Assertions.assertThat(result.height()).isEqualTo(2);
		Assertions.assertThat(result.width()).isEqualTo(3);
		Assertions.assertThat(result.get(0, 0)).isEqualTo(1);
		Assertions.assertThat(result.get(0, 1)).isEqualTo(2);
		Assertions.assertThat(result.get(0, 2)).isEqualTo(3);
		Assertions.assertThat(result.get(1, 0)).isEqualTo(-4);
		Assertions.assertThat(result.get(1, 1)).isEqualTo(-5);
		Assertions.assertThat(result.get(1, 2)).isEqualTo(-6);
	}

	@ParameterizedTest
	@MethodSource("provideInvalidRowAndColumnIndices")
	void shouldThrowExceptionWhenTryingToAccessOutOfBound(final int row, final int column) {
		// given
		final Matrix matrix = new Matrix(2, 3, (r, c) -> 0);

		// when
		final IndexOutOfBoundsException result = Assertions.catchThrowableOfType(() -> matrix.get(row, column), IndexOutOfBoundsException.class);

		// then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getMessage()).isEqualTo("Cannot access matrix element. Index out of bound.");
	}

	private static Stream<Arguments> provideInvalidRowAndColumnIndices() {
		return Stream.of(
				Arguments.of(Integer.valueOf(-1), Integer.valueOf(0)),
				Arguments.of(Integer.valueOf(0), Integer.valueOf(-1)),
				Arguments.of(Integer.valueOf(2), Integer.valueOf(0)),
				Arguments.of(Integer.valueOf(0), Integer.valueOf(3))
		);
	}

	@Test
	void shouldMultiplyMatrixByVector() {
		// given
		final Matrix matrix = new Matrix(2, 3, (row, column) -> row == 0 ? 1 : -2);
		final Vector vector = new Vector(-3, 1, 3);

		// when
		final Vector result = matrix.multiply(vector);


		// then
		Assertions.assertThat(result.length()).isEqualTo(2);
		Assertions.assertThat(result.get(0)).isEqualTo(1);
		Assertions.assertThat(result.get(1)).isEqualTo(-2);
	}

	@Test
	void shouldThrowExceptionWhenTryingToMultiplyMatrixByVectorOfWrongSize() {
		// given
		final Matrix matrix = new Matrix(2, 3, (row, column) -> 0);
		final Vector vector = new Vector(2, index -> 0);

		// when
		final InvalidDimensionsException result = Assertions.catchThrowableOfType(() -> matrix.multiply(vector), InvalidDimensionsException.class);

		// then
		Assertions.assertThat(result.getMessage()).isEqualTo("Cannot multiply. Matrix and vector dimensions does not match.");
	}

	@Test
	void shouldApplyFunctionToAllMatrixElements() {
		// given
		final Matrix matrix = new Matrix(2, 1, (row, column) -> new float[][] {{1}, {-2}}[row][column]);
		final ValueModifier modifier = value -> 1 -2 * value;

		// when
		final Matrix result = matrix.apply(modifier);

		// then
		Assertions.assertThat(result.height()).isEqualTo(2);
		Assertions.assertThat(result.width()).isEqualTo(1);
		Assertions.assertThat(result.get(0, 0)).isEqualTo(-1);
		Assertions.assertThat(result.get(1, 0)).isEqualTo(5);
	}

}
