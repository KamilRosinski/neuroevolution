package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;

public class CrossoverService {

    private final RandomGenerator randomGenerator;

    public CrossoverService(final RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Genotype crossOver(final Genotype... parents) {
        validateInput(parents);
        final double[] resultGenotype = new double[parents[0].genes().length];

        for (int geneIndex = 0; geneIndex < resultGenotype.length; ++geneIndex) {
            final double[] possibleGenes = new double[parents.length];
            for (int parentIndex = 0; parentIndex < possibleGenes.length; ++parentIndex) {
                final Genotype parent = parents[parentIndex];
                possibleGenes[parentIndex] = parent.genes()[geneIndex];
            }
            final int randomGeneIndex = (int) randomGenerator.generateUniform(0, possibleGenes.length);
            resultGenotype[geneIndex] = possibleGenes[randomGeneIndex];
        }
        
        return new Genotype(resultGenotype);
    }

    private static void validateInput(Genotype... parents) {
        if (parents.length == 0) {
            throw new EvolutionException("Cannot cross over genotypes. Parents array must not be empty.");
        }

        final int firstParentLength = parents[0].genes().length;
        for (int i = 1; i < parents.length; ++i) {
            final int currentParentLength = parents[i].genes().length;
            if (currentParentLength != firstParentLength) {
                throw new EvolutionException("Cannot cross over genotypes. Parent genotypes have different lengths: %d and %d."
                    .formatted(Integer.valueOf(firstParentLength), Integer.valueOf(currentParentLength)));
            }
        }
    }

}
