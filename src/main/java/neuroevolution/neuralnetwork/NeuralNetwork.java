package neuroevolution.neuralnetwork;

import neuroevolution.math.Vector;

import java.util.Arrays;

public class NeuralNetwork {

    final NeuralNetworkLayer[] layers;

    public NeuralNetwork(final NeuralNetworkLayer... layers) {
        for (int i = 1; i < layers.length; ++i) {
            final int previousLayerOutputLength = layers[i - 1].getOutputLength();
            final int currentLayerInputLength = layers[i].getInputLength();
            if (previousLayerOutputLength != currentLayerInputLength) {
                throw new NeuralNetworkException(("Cannot create neural network. Dimensions does not match. " +
                    "Layer %d output size: %d. Layer %d input size %d.")
                    .formatted(Integer.valueOf(i - 1), Integer.valueOf(previousLayerOutputLength),
                        Integer.valueOf(i), Integer.valueOf(currentLayerInputLength)));
            }
        }

        this.layers = layers;
    }

    public Vector evaluate(final Vector input) {
        if (input.length() != getInputLength()) {
            throw new NeuralNetworkException("Cannot evaluate neural network. Expected input length: %d, but was %d."
                .formatted(Integer.valueOf(getInputLength()), Integer.valueOf(input.length())));
        }

        return Arrays.stream(layers).reduce(input, (v, layer) -> layer.evaluate(v), (previous, current) -> current);
    }

    public int getInputLength() {
        return layers.length > 0 ? layers[0].getInputLength() : 0;
    }

    public int getOutputLength() {
        return layers.length > 0 ? layers[layers.length - 1].getOutputLength() : 0;
    }

}
