package neuroevolution.math;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathExceptionTest {

    @Test
    void shouldCreateExceptionWithGivenMessageAndNoCause() {

        // given
        final String message = "An ERROR occurred!";

        // when
        final MathException result = new MathException(message);

        // then
        Assertions.assertThat(result.getMessage()).isEqualTo(message);
        Assertions.assertThat(result.getCause()).isNull();
    }

}
