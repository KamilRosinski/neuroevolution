package neuroevolution.evolution;

import neuroevolution.evolution.EvolutionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EvolutionExceptionTest {

    @Test
    void shouldCreateExceptionWithGivenMessageAndNoCause() {

        // given
        final String message = "Fatal ERROR occurred!";

        // when
        final EvolutionException result = new EvolutionException(message);

        // then
        Assertions.assertThat(result.getMessage()).isEqualTo(message);
        Assertions.assertThat(result.getCause()).isNull();
    }

}
