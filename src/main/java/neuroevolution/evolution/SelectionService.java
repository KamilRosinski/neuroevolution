package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SelectionService {

    private final RandomGenerator randomGenerator;

    public SelectionService(final RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Set<Organism> select(final Set<Organism> organisms, final int number) {

        if (number < 0) {
            throw new EvolutionException(("Failed to select organisms. Number of organisms to be selected must be" +
                " non-negative, but was: %d.").formatted(Integer.valueOf(number)));
        }

        if (number > organisms.size()) {
            throw new EvolutionException(("Failed to select organisms. Number of organisms to be selected (%d) must" +
                " not be greater than number of candidates (%d).")
                .formatted(Integer.valueOf(number), Integer.valueOf(organisms.size())));
        }

        final Set<Organism> remainingCandidates = new LinkedHashSet<>(organisms);
        final Set<Organism> result = new HashSet<>(number);

        for (int i = 0; i < number; ++i) {
            final Organism selected = select(remainingCandidates);
            remainingCandidates.remove(selected);
            result.add(selected);
        }

        return Collections.unmodifiableSet(result);
    }

    private Organism select(final Set<Organism> organisms) {

        final Map<Organism, Integer> cumulativeFitness = new LinkedHashMap<>();
        int cumulativeProbability = 0;
        for (final Organism organism : organisms) {
            cumulativeProbability += organism.fitness();
            cumulativeFitness.put(organism, Integer.valueOf(cumulativeProbability));
        }

        final int random = randomGenerator.generateInt(cumulativeProbability);

        for (final Map.Entry<Organism, Integer> entry : cumulativeFitness.entrySet()) {
            if (entry.getValue().intValue() > random) {
                return entry.getKey();
            }
        }

        throw new EvolutionException("Failed to select organism. No organism found.");
    }

}
