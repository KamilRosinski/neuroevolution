package neuroevolution.random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomExceptionTest {

    @Test
    void shouldCreateExceptionWithGivenMessageAndNoCause() {

        // given
        final String message = "A terrible ERROR occurred!";

        // when
        final RandomException result = new RandomException(message);

        // then
        Assertions.assertThat(result.getMessage()).isEqualTo(message);
        Assertions.assertThat(result.getCause()).isNull();
    }

}
