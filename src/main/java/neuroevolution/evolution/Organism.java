package neuroevolution.evolution;

import neuroevolution.ann.NeuralNetwork;
import neuroevolution.ann.NeuralNetworkLayer;
import neuroevolution.math.ActivationFunction;
import neuroevolution.math.Matrix;
import neuroevolution.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public class Organism {

	private final int[] layerSizes;
	private final ActivationFunction activationFunction;
	private final double[] genes;

	public Organism(final int inputLayerSize, final int[] hiddenLayerSizes, final int outputLayerSizes, final ActivationFunction activationFunction, final GeneValueProvider geneProvider) {
		layerSizes = IntStream.concat(IntStream.concat(IntStream.of(inputLayerSize), IntStream.of(hiddenLayerSizes)), IntStream.of(outputLayerSizes)).toArray();
		this.activationFunction = activationFunction;
		genes = new double[computeGenotypeLength()];
		for (int i = 0; i < genes.length; ++i) {
			genes[i] = geneProvider.generateValue(i);
		}
	}

	private int computeGenotypeLength() {
		int result = 0;
		for (int i = 1; i < layerSizes.length; ++i) {
			result += layerSizes[i] * (layerSizes[i - 1] + 1);
		}
		return result;
	}

	public double getGene(final int index) {
		return genes[index];
	}

	public NeuralNetwork buildNeuralNetwork() {
		final PrimitiveIterator.OfDouble genesIterator = Arrays.stream(genes).iterator();
		final List<NeuralNetworkLayer> layers = new ArrayList<>();
		for (int i = 1; i < layerSizes.length; ++i) {
			final NeuralNetworkLayer layer = new NeuralNetworkLayer(
					new Matrix(layerSizes[i], layerSizes[i - 1], (row, column) -> genesIterator.nextDouble()),
					new Vector(layerSizes[i], index -> genesIterator.nextDouble()),
					activationFunction);
			layers.add(layer);
		}
		return new NeuralNetwork(layers);
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(layerSizes);
		result = 31 * result + activationFunction.hashCode();
		result = 31 * result + Arrays.hashCode(genes);
		return result;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Organism organism = (Organism) o;

		return Arrays.equals(layerSizes, organism.layerSizes)
				&& activationFunction != organism.activationFunction
				&& Arrays.equals(genes, organism.genes);
	}

}
