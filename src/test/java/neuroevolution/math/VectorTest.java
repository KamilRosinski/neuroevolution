package neuroevolution.math;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;

public class VectorTest {

    @Test
    void shouldCreateVectorFromArray() {

        // given
        final double[] elements = {0, Math.PI, -2};

        // when
        final Vector result = new Vector(elements);

        // then
        Assertions.assertThat(result.length()).isEqualTo(3);
        Assertions.assertThat(result.get(0)).isEqualTo(0);
        Assertions.assertThat(result.get(1)).isEqualTo(Math.PI);
        Assertions.assertThat(result.get(2)).isEqualTo(-2);
    }

    @Test
    void shouldCreateVectorFromProvider() {

        // given
        final int length = 3;
        final IntToDoubleFunction provider = i -> 2 * i - 0.5;

        // when
        final Vector result = new Vector(length, provider);

        // then
        Assertions.assertThat(result.length()).isEqualTo(3);
        Assertions.assertThat(result.get(0)).isEqualTo(-0.5);
        Assertions.assertThat(result.get(1)).isEqualTo(1.5);
        Assertions.assertThat(result.get(2)).isEqualTo(3.5);
    }

    @Test
    void shouldFailToCreateVectorWithNegativeLength() {

        // given
        final int length = -1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                new Vector(length, i -> 0);
            }).isInstanceOf(MathException.class)
            .hasMessage("Cannot create vector. Invalid length: -1.");
    }

    @Test
    void shouldPositivelyCompareEqualVectors() {

        // given
        final Vector v1 = new Vector(0, -1, 1.5);
        final Vector v2 = new Vector(0, -1, 1.5);

        // when, then
        Assertions.assertThat(v1.hashCode()).isEqualTo(v2.hashCode());
        Assertions.assertThat(v1.equals(v2)).isTrue();
    }

    @Test
    void shouldNegativelyCompareUnequalVectors() {

        // given
        final Vector v1 = new Vector(0, -1, 1.5);
        final Vector v2 = new Vector(0, 1.5);

        // when, then
        Assertions.assertThat(v1.hashCode()).isNotEqualTo(v2.hashCode());
        Assertions.assertThat(v1.equals(v2)).isFalse();
    }

    @Test
    void shouldFailTeGetElementAtNegativeIndex() {

        // given
        final Vector vector = new Vector(0, 1.5, -2);
        final int index = -1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                vector.get(index);
            }).isInstanceOf(MathException.class)
            .hasMessage("Vector index out of bound. Index: -1, vector length: 3.");
    }

    @Test
    void shouldFailTeGetElementAtTooHighIndex() {

        // given
        final Vector vector = new Vector(0, 1.5, -2);
        final int index = 3;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                vector.get(index);
            }).isInstanceOf(MathException.class)
            .hasMessage("Vector index out of bound. Index: 3, vector length: 3.");
    }

    @Test
    void shouldStreamVectorElements() {

        // given
        final Vector vector = new Vector(0, -3, 2.5);

        // when
        final DoubleStream result = vector.stream();

        // then
        Assertions.assertThat(result).containsExactly(
            Double.valueOf(0),
            Double.valueOf(-3),
            Double.valueOf(2.5)
        );
    }

    @Test
    void shouldApplyFunctionOnVectorElements() {

        // given
        final Vector vector = new Vector(2, -1.5, 0);
        final DoubleUnaryOperator function = x -> Math.pow(x, 2);

        // when
        final Vector result = vector.apply(function);

        // then
        Assertions.assertThat(result).isEqualTo(new Vector(4, 2.25, 0));
    }

}
