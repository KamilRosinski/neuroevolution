package neuroevolution.ann;

import neuroevolution.exception.InvalidDimensionsException;
import neuroevolution.math.ActivationFunction;
import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class NeuralNetworkTest {

	@Test
	void shouldEvaluateOutputValue() {
		// given
		final NeuralNetwork neuralNetwork = new NeuralNetwork(Arrays.asList(
				new NeuralNetworkLayer(
						new Matrix(3, 2, (row, column) -> new double[][]{{1, -1}, {0, 2}, {3, -2}}[row][column]),
						new Vector(-2, 1, -4),
						ActivationFunction.RELU
				),
				new NeuralNetworkLayer(
						new Matrix(1, 3, (row, column) -> new double[][]{{3, -2, 1}}[row][column]),
						new Vector(-3),
						ActivationFunction.RELU
				)
		));
		final Vector input = new Vector(1, -2);

		// when
		final Vector result = neuralNetwork.evaluate(input);

		// then
		Assertions.assertThat(result.length()).isEqualTo(1);
		Assertions.assertThat(result.get(0)).isEqualTo(3);
	}

	@Test
	void shouldBindInputDirectlyToOutputInEmptyNetwork() {
		// given
		final NeuralNetwork neuralNetwork = new NeuralNetwork(Collections.emptyList());
		final Vector input = new Vector(-1, 3);

		// when
		final Vector result = neuralNetwork.evaluate(input);

		// then
		Assertions.assertThat(result.length()).isEqualTo(2);
		Assertions.assertThat(result.get(0)).isEqualTo(-1);
		Assertions.assertThat(result.get(1)).isEqualTo(3);
	}

	@Test
	void shouldThrowExceptionWhenDimensionsOfLayersDoesNotMatch() {
		// given
		final NeuralNetworkLayer layer1 = new NeuralNetworkLayer(
				new Matrix(2, 3, (row, column) -> 0),
				new Vector(2, index -> 0),
				null
		);
		final NeuralNetworkLayer layer2 = new NeuralNetworkLayer(
				new Matrix(4, 1, (row, column) -> 0),
				new Vector(4, index -> 0),
				null
		);

		// when
		final InvalidDimensionsException result = Assertions.catchThrowableOfType(
				() -> new NeuralNetwork(Arrays.asList(layer1, layer2)), InvalidDimensionsException.class);

		// then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getMessage()).isEqualTo("Dimensions of adjacent layers does not match.");
	}

}
