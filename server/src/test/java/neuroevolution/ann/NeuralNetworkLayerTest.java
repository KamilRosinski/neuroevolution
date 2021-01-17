package neuroevolution.ann;

import neuroevolution.exception.InvalidDimensionsException;
import neuroevolution.math.ActivationFunction;
import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NeuralNetworkLayerTest {

	@Test
	void shouldEvaluateOutputValue() {
		// given
		final NeuralNetworkLayer layer = new NeuralNetworkLayer(
				new Matrix(2, 3, (row, column) -> new float[][]{{1, 0, -1}, {2, -3, 1}}[row][column]),
				new Vector(2, -1),
				ActivationFunction.RELU
		);
		final Vector input = new Vector(0, 3, -2);

		// when
		final Vector result = layer.evaluate(input);

		// then
		Assertions.assertThat(result.length()).isEqualTo(2);
		Assertions.assertThat(result.get(0)).isEqualTo(4);
		Assertions.assertThat(result.get(1)).isEqualTo(0);
	}

	@Test
	void shouldThrowExceptionWhenWeightsAndBiasesDimensionsDoesNotMatch() {
		// given
		final Matrix weights = new Matrix(2, 3, (row, column) -> 0);
		final Vector biases = new Vector(3, index -> 0);

		// when
		final InvalidDimensionsException result = Assertions.catchThrowableOfType(
				() -> new NeuralNetworkLayer(weights, biases, null), InvalidDimensionsException.class);

		// then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getMessage()).isEqualTo("Dimensions of weights matrix and biases vector does not match.");
	}

	@Test
	void shouldDetermineInputAndOutputSize() {
		// given
		final Matrix weights = new Matrix(2, 3, (row, column) -> 0);
		final Vector biases = new Vector(2, index -> 0);

		// when
		final NeuralNetworkLayer result = new NeuralNetworkLayer(
				weights,
				biases,
				null
		);

		// then
		Assertions.assertThat(result.inputSize()).isEqualTo(3);
		Assertions.assertThat(result.outputSize()).isEqualTo(2);
	}

}
