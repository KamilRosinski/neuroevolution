package neuroevolution.evolution;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenotypeTest {

    @Test
    void shouldPositivelyCompareEqualGenotypes() {

        // given
        final Genotype o1 = new Genotype(1, 2, 3);
        final Genotype o2 = new Genotype(1, 2, 3);

        // when
        final boolean result = o1.equals(o2);

        // then
        Assertions.assertThat(o1.hashCode()).isEqualTo(o2.hashCode());
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldNegativelyCompareUnequalGenotypes() {

        // given
        final Genotype o1 = new Genotype(1, 2, 3);
        final Genotype o2 = new Genotype(3, 2, 1);

        // when
        final boolean result = o1.equals(o2);

        // then
        Assertions.assertThat(o1.hashCode()).isNotEqualTo(o2.hashCode());
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void shouldStringifyGenotype() {

        // given
        final Genotype genotype = new Genotype(0, -0.5, 2);

        // when
        final String result = genotype.toString();

        // then
        Assertions.assertThat(result).isEqualTo("[0.0, -0.5, 2.0]");
    }

}
