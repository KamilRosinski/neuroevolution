package neuroevolution.ann;

import neuroevolution.exception.InvalidDimensionsException;
import neuroevolution.math.ActivationFunction;
import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;

public class NeuralNetworkLayer {

	private final Matrix weights;
	private final Vector biases;
	private final ActivationFunction activationFunction;

	public NeuralNetworkLayer(final Matrix weights, final Vector biases, final ActivationFunction activationFunction) {
		if (weights.height() != biases.length()) {
			throw new InvalidDimensionsException("Dimensions of weights matrix and biases vector does not match.");
		}

		this.weights = weights;
		this.biases = biases;
		this.activationFunction = activationFunction;
	}

	public Vector evaluate(final Vector input) {
		return weights.multiply(input).add(biases).apply(activationFunction.getFunction());
	}

	public int inputSize() {
		return weights.width();
	}

	public int outputSize() {
		return weights.height();
	}

}
