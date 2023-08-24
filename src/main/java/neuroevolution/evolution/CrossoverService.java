package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;

public class CrossoverService {

    private final RandomGenerator randomGenerator;

    public CrossoverService(final RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Organism crossOver(final Organism... parents) {
        validateInput(parents);
        final double[] resultGenotype = new double[parents[0].genotype().length];

        for (int geneIndex = 0; geneIndex < resultGenotype.length; ++geneIndex) {
            final double[] possibleGenes = new double[parents.length];
            for (int parentIndex = 0; parentIndex < possibleGenes.length; ++parentIndex) {
                final Organism parent = parents[parentIndex];
                possibleGenes[parentIndex] = parent.genotype()[geneIndex];
            }
            final int randomGeneIndex = (int) randomGenerator.generateUniform(0, possibleGenes.length);
            resultGenotype[geneIndex] = possibleGenes[randomGeneIndex];
        }
        
        return new Organism(resultGenotype);
    }

    private static void validateInput(Organism... parents) {
        if (parents.length == 0) {
            throw new EvolutionException("Cannot cross over organisms. Parents array must not be empty.");
        }

        final int firstParentLength = parents[0].genotype().length;
        for (int i = 1; i < parents.length; ++i) {
            final int currentParentLength = parents[i].genotype().length;
            if (currentParentLength != firstParentLength) {
                throw new EvolutionException("Cannot cross over organisms. Parent genotypes have different lengths: %d and %d."
                    .formatted(Integer.valueOf(firstParentLength), Integer.valueOf(currentParentLength)));
            }
        }
    }

}
