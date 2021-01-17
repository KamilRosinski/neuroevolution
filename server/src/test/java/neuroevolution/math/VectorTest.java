package neuroevolution.math;

import neuroevolution.exception.InvalidDimensionsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class VectorTest {

	@Test
	void shouldCreateVectorWithGivenElements() {

		// given
		final double[] elements = {2, 3, 5};

		// when
		final Vector result = new Vector(elements);

		// then
		Assertions.assertThat(result.length()).isEqualTo(3);
		Assertions.assertThat(result.get(0)).isEqualTo(2);
		Assertions.assertThat(result.get(1)).isEqualTo(3);
		Assertions.assertThat(result.get(2)).isEqualTo(5);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 2})
	void shouldThrowExceptionWhenTryingToAccessElementOutOfBound(final int index) {
		// given
		final Vector vector = new Vector(2, i -> 0);

		// when
		final IndexOutOfBoundsException result = Assertions.catchThrowableOfType(() -> vector.get(index), IndexOutOfBoundsException.class);

		// then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getMessage()).isEqualTo("Cannot access vector element. Index out of bound.");
	}

	@Test
	void shouldAddTwoVectors() {
		// given
		final Vector v1 = new Vector(1, 2);
		final Vector v2 = new Vector(3, -4);

		// when
		final Vector result = v1.add(v2);

		// then
		Assertions.assertThat(result.length()).isEqualTo(2);
		Assertions.assertThat(result.get(0)).isEqualTo(4);
		Assertions.assertThat(result.get(1)).isEqualTo(-2);
	}

	@Test
	void shouldThrowExceptionWhenTryingToAddVectorsOfWrongSize() {
		// given
		final Vector v1 = new Vector(2, index -> 0);
		final Vector v2 = new Vector(3, index -> 0);

		// when
		final InvalidDimensionsException result = Assertions.catchThrowableOfType(() -> v1.add(v2), InvalidDimensionsException.class);

		// then
		Assertions.assertThat(result.getMessage()).isEqualTo("Cannot add vectors. Dimensions does not match.");
	}

	@Test
	void shouldDivideVectorByScalar() {
		// given
		final Vector vector = new Vector(2, 0, -4);

		// when
		final Vector result = vector.divide(-2);

		// then
		Assertions.assertThat(result.length()).isEqualTo(3);
		Assertions.assertThat(result.get(0)).isEqualTo(-1);
		Assertions.assertThat(result.get(1)).isEqualTo(0);
		Assertions.assertThat(result.get(2)).isEqualTo(2);
	}

	@Test
	void shouldApplyFunctionToAllVectorElements() {
		// given
		final Vector vector = new Vector(1, 3, 2);

		// when
		final ValueModifier modifier = value -> 1 - 2 * value;
		final Vector result = vector.apply(modifier);

		// then
		Assertions.assertThat(result.length()).isEqualTo(3);
		Assertions.assertThat(result.get(0)).isEqualTo(-1);
		Assertions.assertThat(result.get(1)).isEqualTo(-5);
		Assertions.assertThat(result.get(2)).isEqualTo(-3);
	}

}
