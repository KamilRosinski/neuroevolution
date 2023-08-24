package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CrossoverServiceTest {

    @Mock
    private RandomGenerator randomGenerator;

    @InjectMocks
    private CrossoverService crossoverService;

    @Test
    void shouldCreateChildOrganism() {

        // given
        final Organism[] parents = {
            new Organism(1, 1, 2, 3),
            new Organism(5, 8, 13, 21)
        };

        Mockito.when(Double.valueOf(randomGenerator.generateUniform(0, 2)))
            .thenReturn(Double.valueOf(1.5), Double.valueOf(0), Double.valueOf(0.9), Double.valueOf(1));

        // when
        final Organism result = crossoverService.crossOver(parents);

        // then
        Assertions.assertThat(result).isEqualTo(new Organism(5, 1, 2, 21));
    }

    @Test
    void shouldFailToCreateChildOrganismWhenParentGenotypeLengthsDoesNotMatch() {

        // given
        final Organism[] parents = {
            new Organism(0, 0),
            new Organism(0, 0, 0)
        };

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                crossoverService.crossOver(parents);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot cross over organisms. Parent genotypes have different lengths: 2 and 3.");
    }

    @Test
    void shouldFailToCreateChildOrganismWhenParentsArrayIsEmpty() {

        // given
        final Organism[] parents = {};

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                crossoverService.crossOver(parents);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot cross over organisms. Parents array must not be empty.");
    }

}
