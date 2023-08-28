package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;

public class MutationService {

    private final RandomGenerator randomGenerator;

    public MutationService(final RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Genotype mutate(final Genotype genotype, final double mutationProbability, final double mutationRate) {
        if (mutationProbability < 0 || mutationProbability > 1) {
            throw new EvolutionException("Cannot mutate genotype. Invalid mutation probability: %s."
                .formatted(Double.valueOf(mutationProbability)));
        }

        if (mutationRate < 0) {
            throw new EvolutionException("Cannot mutate genotype. Mutation rate must be non-negative, but was: %s."
                .formatted(Double.valueOf(mutationRate)));
        }

        final Genotype result = new Genotype(new double[genotype.genes().length]);
        for (int geneIndex = 0; geneIndex < result.genes().length; ++geneIndex) {
            final double mutationValue = randomGenerator.generateBoolean(mutationProbability)
                ? randomGenerator.generateGaussian(0, mutationRate)
                : 0;

            result.genes()[geneIndex] = genotype.genes()[geneIndex] + mutationValue;
        }

        return result;
    }

}
