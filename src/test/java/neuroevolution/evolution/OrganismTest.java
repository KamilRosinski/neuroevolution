package neuroevolution.evolution;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrganismTest {

    @Test
    void shouldPositivelyCompareEqualOrganisms() {

        // given
        final Organism o1 = new Organism(1, 2, 3);
        final Organism o2 = new Organism(1, 2, 3);

        // when
        final boolean result = o1.equals(o2);

        // then
        Assertions.assertThat(o1.hashCode()).isEqualTo(o2.hashCode());
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldNegativelyCompareUnequalOrganisms() {

        // given
        final Organism o1 = new Organism(1, 2, 3);
        final Organism o2 = new Organism(3, 2, 1);

        // when
        final boolean result = o1.equals(o2);

        // then
        Assertions.assertThat(o1.hashCode()).isNotEqualTo(o2.hashCode());
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void shouldStringifyOrganism() {

        // given
        final Organism organism = new Organism(0, -0.5, 2);

        // when
        final String result = organism.toString();

        // then
        Assertions.assertThat(result).isEqualTo("[0.0, -0.5, 2.0]");
    }

}
