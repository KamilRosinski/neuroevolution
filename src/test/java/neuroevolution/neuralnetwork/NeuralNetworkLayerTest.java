package neuroevolution.neuralnetwork;

import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NeuralNetworkLayerTest {

    @Test
    void shouldFailToCreateNeuralNetworkLayerWhenDimensionsDoesNotMatch() {

        // given
        final Matrix weights = new Matrix(new double[2], new double[2], new double[2]);
        final Vector biases = new Vector(new double[2]);
        final ActivationFunction activationFunction = ActivationFunction.SIGMOID;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                new NeuralNetworkLayer(weights, biases, activationFunction);
            }).isInstanceOf(NeuralNetworkException.class)
            .hasMessage("Cannot create neural network layer. Dimensions does not match. Weights height: 3, biases length: 2.");
    }

    @Test
    void shouldEvaluateNeuralNetworkLayer() {

        // given
        final NeuralNetworkLayer layer = new NeuralNetworkLayer(
            new Matrix(new double[]{0, Math.PI}, new double[]{1, -0.5}, new double[]{-2, 2}),
            new Vector(0, -0.5, 1),
            ActivationFunction.RE_LU
        );

        final Vector input = new Vector(2, 1);

        // when
        final Vector result = layer.evaluate(input);

        // then
        Assertions.assertThat(result).isEqualTo(new Vector(Math.PI, 1, 0));
    }

    @Test
    void shouldFailToEvaluateNeuralNetworkLayerWhenInputLengthDoesNotMatch() {

        // given
        final NeuralNetworkLayer layer = new NeuralNetworkLayer(
            new Matrix(new double[4], new double[4]),
            new Vector(new double[2]),
            ActivationFunction.SIGMOID
        );

        final Vector input = new Vector(new double[2]);

        // then
        Assertions.assertThatThrownBy(() -> {
            // when
            layer.evaluate(input);
        }).isInstanceOf(NeuralNetworkException.class)
            .hasMessage("Cannot evaluate neural network layer. Expected input length: 4, but was 2.");
    }

}
