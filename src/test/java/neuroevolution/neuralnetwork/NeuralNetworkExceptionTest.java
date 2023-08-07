package neuroevolution.neuralnetwork;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NeuralNetworkExceptionTest {

    @Test
    void shouldCreateExceptionWithGivenMessageAndNoCause() {

        // given
        final String message = "A fatal ERROR occurred!";

        // when
        final NeuralNetworkException result = new NeuralNetworkException(message);

        // then
        Assertions.assertThat(result.getMessage()).isEqualTo(message);
        Assertions.assertThat(result.getCause()).isNull();
    }

}
