package neuroevolution.evolution;

import java.util.Arrays;

public record Organism(double... genotype) {

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Organism organism)) {
            return false;
        }
        return Arrays.equals(genotype, organism.genotype);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genotype);
    }

    @Override
    public String toString() {
        return Arrays.toString(genotype);
    }
}
