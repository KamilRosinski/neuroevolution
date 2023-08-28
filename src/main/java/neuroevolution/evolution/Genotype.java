package neuroevolution.evolution;

import java.util.Arrays;

public record Genotype(double... genes) {

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Genotype genotype)) {
            return false;
        }
        return Arrays.equals(this.genes, genotype.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }

}
