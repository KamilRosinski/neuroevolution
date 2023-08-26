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
public class MutationServiceTest {

    @Mock
    private RandomGenerator randomGenerator;

    @InjectMocks
    private MutationService mutationService;

    @Test
    void shouldFailToMutateOrganismWhenMutationProbabilityIsNegative() {

        // given
        final Organism organism = new Organism(0, 0, 0);
        final double mutationProbability = -0.5;
        final double mutationRate = 0.2;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                mutationService.mutate(organism, mutationProbability, mutationRate);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot mutate organism. Invalid mutation probability: -0.5.");
    }

    @Test
    void shouldFailToMutateOrganismWhenMutationProbabilityIsGreaterThanOne() {

        // given
        final Organism organism = new Organism(0, 0, 0);
        final double mutationProbability = 2;
        final double mutationRate = 0.2;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                mutationService.mutate(organism, mutationProbability, mutationRate);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot mutate organism. Invalid mutation probability: 2.0.");
    }

    @Test
    void shouldFailToMutateOrganismWhenMutationRateIsNegative() {

        // given
        final Organism organism = new Organism(0, 0, 0);
        final double mutationProbability = 0.05;
        final double mutationRate = -0.1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                mutationService.mutate(organism, mutationProbability, mutationRate);
            }).isInstanceOf(EvolutionException.class)
            .hasMessage("Cannot mutate organism. Mutation rate must be non-negative, but was: -0.1.");
    }

    @Test
    void shouldMutateOrganism() {

        // given
        final Organism organism = new Organism(1, 0, -0.5, 2, -1, 0.2);
        final double mutationProbability = 0.2;
        final double mutationRate = 0.1;

        Mockito.when(Boolean.valueOf(randomGenerator.generateBoolean(mutationProbability)))
            .thenReturn(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);

        Mockito.when(Double.valueOf(randomGenerator.generateGaussian(0, mutationRate)))
            .thenReturn(Double.valueOf(-0.2), Double.valueOf(0.5));

        // when
        final Organism result = mutationService.mutate(organism, mutationProbability, mutationRate);

        // then
        Assertions.assertThat(result).isEqualTo(new Organism(1, -0.2, -0.5, 2, -0.5, 0.2));
    }

}
