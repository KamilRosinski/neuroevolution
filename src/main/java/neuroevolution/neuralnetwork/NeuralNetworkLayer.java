package neuroevolution.neuralnetwork;

import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;

public class NeuralNetworkLayer {

    private final Matrix weights;
    private final Vector biases;
    private final ActivationFunction activationFunction;

    public NeuralNetworkLayer(final Matrix weights, final Vector biases, final ActivationFunction activationFunction) {
        if (weights.height() != biases.length()) {
            throw new NeuralNetworkException("Cannot create neural network layer. Dimensions does not match. Weights height: %d, biases length: %d."
                .formatted(Integer.valueOf(weights.height()), Integer.valueOf(biases.length())));
        }

        this.weights = weights;
        this.biases = biases;
        this.activationFunction = activationFunction;
    }

    public Vector evaluate(final Vector input) {
        if (input.length() != weights.width()) {
            throw new NeuralNetworkException("Cannot evaluate neural network layer. Expected input length: %d, but was %d."
                .formatted(Integer.valueOf(weights.width()), Integer.valueOf(input.length())));
        }

        return weights.multiply(input).add(biases).apply(activationFunction.getFunction());
    }

    public int getInputLength() {
        return weights.width();
    }

    public int getOutputLength() {
        return weights.height();
    }

}
