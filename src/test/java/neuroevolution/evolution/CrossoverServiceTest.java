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
    void shouldCreateChildGenotype() {

        // given
        final Genotype[] parents = {
            new Genotype(1, 1, 2, 3),
            new Genotype(5, 8, 13, 21)
        };

        Mockito.when(Double.valueOf(randomGenerator.generateUniform(0, 2)))
            .thenReturn(Double.valueOf(1.5), Double.valueOf(0), Double.valueOf(0.9), Double.valueOf(1));

        // when
        final Genotype result = crossoverService.crossOver(parents);

        // then
        Assertions.assertThat(result).isEqualTo(new Genotype(5, 1, 2, 21));
    }

    @Test
    void shouldFailToCreateChildGenotypeWhenParentGenotypeLengthsDoesNotMatch() {

        // given
        final Genotype[] parents = {
            new Genotype(0, 0),
            new Genotype(0, 0, 0)
        };

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                crossoverService.crossOver(parents);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot cross over genotypes. Parent genotypes have different lengths: 2 and 3.");
    }

    @Test
    void shouldFailToCreateChildGenotypeWhenParentsArrayIsEmpty() {

        // given
        final Genotype[] parents = {};

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                crossoverService.crossOver(parents);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot cross over genotypes. Parents array must not be empty.");
    }

}
