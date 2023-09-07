package neuroevolution.evolution;

import neuroevolution.random.RandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class SelectionServiceTest {

    @Mock
    private RandomGenerator randomGenerator;

    @InjectMocks
    private SelectionService selectionService;

    @Test
    void shouldSelectParentOrganisms() {

        // given
        final Organism parent1 = new Organism(1, new Genotype(0, 0, 0), 1);
        final Organism parent2 = new Organism(2, new Genotype(0, 0, 0), 1);
        final Organism parent3 = new Organism(3, new Genotype(0, 0, 0), 2);
        final Set<Organism> parents = new LinkedHashSet<>();
        parents.add(parent1);
        parents.add(parent2);
        parents.add(parent3);

        Mockito.when(randomGenerator.generateInt(4)).thenReturn(2);
        Mockito.when(randomGenerator.generateInt(2)).thenReturn(0);

        // 1: [0, 1), 2: [1, 2), 4: [2, 4)
        // 1: [0, 1), 3: [1, 3)

        // when
        final Set<Organism> result = selectionService.select(parents, 2);

        // then
        Assertions.assertThat(result).containsExactlyInAnyOrder(parent1, parent3);
    }

}
