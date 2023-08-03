package neuroevolution.math;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleFunctionTest {

    @Test
    void shouldApplyFunction() {

        // given
        final DoubleFunction function = x -> Math.pow(x, 2) - x;

        // when
        final double result = function.apply(-3.5);

        // then
        Assertions.assertThat(result).isEqualTo(15.75);
    }

}
