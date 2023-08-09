package neuroevolution.neuralnetwork;

import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NeuralNetworkTest {

    @Test
    void shouldCreateNeuralNetwork() {

        // given
        final NeuralNetworkLayer[] layers = {
            new NeuralNetworkLayer(
                new Matrix(new double[3], new double[3], new double[3], new double[3]),
                new Vector(new double[4]),
                ActivationFunction.SIGMOID
            ),
            new NeuralNetworkLayer(
                new Matrix(new double[4], new double[4]),
                new Vector(new double[2]),
                ActivationFunction.SIGMOID
            )
        };

        // when
        final NeuralNetwork result = new NeuralNetwork(layers);

        // then
        Assertions.assertThat(result.getInputLength()).isEqualTo(3);
        Assertions.assertThat(result.getOutputLength()).isEqualTo(2);
    }

    @Test
    void shouldFailToCreateNeuralNetworkWhenLayerDimensionsDoesNotMatch() {

        // given
        final NeuralNetworkLayer[] layers = {
            new NeuralNetworkLayer(
                new Matrix(new double[2], new double[2], new double[2]),
                new Vector(new double[3]),
                ActivationFunction.SIGMOID
            ),
            new NeuralNetworkLayer(
                new Matrix(new double[4], new double[4]),
                new Vector(new double[2]),
                ActivationFunction.SIGMOID
            )
        };

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                new NeuralNetwork(layers);
            }).isInstanceOf(NeuralNetworkException.class)
            .hasMessage("Cannot create neural network. Dimensions does not match. Layer 0 output size: 3. Layer 1 input size 4.");
    }

    @Test
    void shouldEvaluateNeuralNetwork() {

        // given
        final NeuralNetwork neuralNetwork = new NeuralNetwork(
            new NeuralNetworkLayer(
                new Matrix(new double[]{0, 1}, new double[]{0.5, -2}, new double[]{Math.PI, 3}),
                new Vector(0, -1, 3),
                ActivationFunction.RE_LU
            ),
            new NeuralNetworkLayer(
                new Matrix(new double[]{1, 0.5, 0}),
                new Vector(-1),
                ActivationFunction.SIGMOID
            )
        );

        final Vector input = new Vector(2, -1);

        // when
        final Vector result = neuralNetwork.evaluate(input);

        // then
        Assertions.assertThat(result).isEqualTo(new Vector(0.5));
    }

    @Test
    void shouldFailToEvaluateNeuralNetworkWhenInputLengthDoesNotMatch() {

        // given
        final NeuralNetwork neuralNetwork = new NeuralNetwork(
            new NeuralNetworkLayer(
                new Matrix(new double[3], new double[3], new double[3], new double[3]),
                new Vector(new double[4]),
                ActivationFunction.SIGMOID
            ),
            new NeuralNetworkLayer(
                new Matrix(new double[4], new double[4]),
                new Vector(new double[2]),
                ActivationFunction.SIGMOID
            )
        );

        final Vector input = new Vector(new double[2]);

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                neuralNetwork.evaluate(input);
            }).isInstanceOf(NeuralNetworkException.class)
            .hasMessage("Cannot evaluate neural network. Expected input length: 3, but was 2.");
    }

}
