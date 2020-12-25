package neuroevolution.evolution;

import neuroevolution.ann.NeuralNetwork;
import neuroevolution.math.ActivationFunction;
import neuroevolution.math.Vector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrganismTest {

	@Test
	void shouldCreateOrganismAndBuildNeuralNetworkFromIt() {
		// given
		final double[] genes = {1, -1, 0, 2, 3, -2, -2, 1, -4, 3, -2, 1, -3};
		final Organism organism = new Organism(2, new int[]{3}, 1, ActivationFunction.RELU, index -> genes[index]);

		// when
		final NeuralNetwork neuralNetwork = organism.buildNeuralNetwork();
		final Vector result = neuralNetwork.evaluate(new Vector(1, -2));

		// then
		Assertions.assertThat(result.length()).isEqualTo(1);
		Assertions.assertThat(result.get(0)).isEqualTo(3);
	}

}
