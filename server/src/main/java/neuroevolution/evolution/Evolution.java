package neuroevolution.evolution;

import neuroevolution.ann.NeuralNetwork;
import neuroevolution.snake.Snake;
import neuroevolution.snake.PlayerState;
import neuroevolution.math.ActivationFunction;
import neuroevolution.random.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Evolution {

	private final List<Generation> generations = new ArrayList<>();
	private final int generationSize = 200;
	private final float mutationProbability = 0.05f;
	private final float mutationRate = 2;
	private final int inputSize = 8;
	private final int[] hiddenLayerSizes = {6};
	private final int outputSize = 4;
	private final int numberOfParents = 2;
	private final ActivationFunction activationFunction = ActivationFunction.RELU;

	public int[] nextGeneration() {
		final Generation generation = new Generation();
		for (int i = 0; i < generationSize; ++i) {
			final Organism organism;
			if (generations.isEmpty()) {
				organism = createRandomOrganism();
			} else {
				final List<Organism> parents = chooseParents();
				organism = createOrganismFromParents(parents);
			}
			final int fitness = evaluateOrganism(organism);
			generation.addOrganism(organism, fitness);
		}
		generations.add(generation);

		return generation.getOrganisms().values().stream().mapToInt(Integer::intValue).toArray();
	}

	private List<Organism> chooseParents() {
		final List<Organism> result = new ArrayList<>(numberOfParents);
		final Map<Organism, Integer> possibleParents = generations.get(generations.size() - 1).getOrganisms();
		for (int i = 0; i < numberOfParents; ++i) {
			final Organism parent = possibleParents.values().stream().anyMatch(v -> v.intValue() > 0)
					? RandomUtils.pickWithProbability(possibleParents)
					: RandomUtils.pick(new ArrayList<>(possibleParents.keySet()));
			result.add(parent);
			possibleParents.remove(parent);
		}
		return result;
	}

	private int evaluateOrganism(final Organism organism) {
		final NeuralNetwork brain = organism.buildNeuralNetwork();
		final Snake snake = new Snake(6, 6, 50);
		final SnakeAdapter snakeAdapter = new SnakeAdapter(snake);
		while (snake.getPlayerState() == PlayerState.ALIVE) {
			snakeAdapter.move(brain);
		}
		return snake.getScore();
	}

	private Organism createRandomOrganism() {
		return new Organism(inputSize, hiddenLayerSizes, outputSize, activationFunction, index -> RandomUtils.generateInRange(-1, 1));
	}

	private Organism createOrganismFromParents(final List<Organism> parents) {
		return new Organism(inputSize, hiddenLayerSizes, outputSize, activationFunction, index -> {
			final double parentGene = RandomUtils.pick(parents).getGene(index);
			final double mutationFactor = RandomUtils.generateWithProbability(mutationProbability) ? RandomUtils.generateInRange(-mutationRate, mutationRate) : 1;
			return parentGene * mutationFactor;
		});
	}

}
