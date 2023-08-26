package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;

public class MutationService {

    private final RandomGenerator randomGenerator;

    public MutationService(final RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Organism mutate(final Organism organism, final double mutationProbability, final double mutationRate) {
        if (mutationProbability < 0 || mutationProbability > 1) {
            throw new EvolutionException("Cannot mutate organism. Invalid mutation probability: %s."
                .formatted(Double.valueOf(mutationProbability)));
        }

        if (mutationRate < 0) {
            throw new EvolutionException("Cannot mutate organism. Mutation rate must be non-negative, but was: %s."
                .formatted(Double.valueOf(mutationRate)));
        }

        final Organism result = new Organism(new double[organism.genotype().length]);
        for (int geneIndex = 0; geneIndex < result.genotype().length; ++geneIndex) {
            final double mutationValue = randomGenerator.generateBoolean(mutationProbability)
                ? randomGenerator.generateGaussian(0, mutationRate)
                : 0;

            result.genotype()[geneIndex] = organism.genotype()[geneIndex] + mutationValue;
        }

        return result;
    }

}
