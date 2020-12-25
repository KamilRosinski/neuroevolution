package neuroevolution.ann;

import neuroevolution.exception.InvalidDimensionsException;
import neuroevolution.math.Vector;

import java.util.List;

public class NeuralNetwork {

	private final List<NeuralNetworkLayer> layers;

	public NeuralNetwork(final List<NeuralNetworkLayer> layers) {
		for (int i = 1; i < layers.size(); ++i) {
			if (layers.get(i - 1).outputSize() != layers.get(i).inputSize()) {
				throw new InvalidDimensionsException("Dimensions of adjacent layers does not match.");
			}
		}
		this.layers = layers;
	}

	public Vector evaluate(final Vector input) {
		return layers.stream().reduce(input, (vector, layer) -> layer.evaluate(vector), (accumulator, value) -> value);
	}

}
