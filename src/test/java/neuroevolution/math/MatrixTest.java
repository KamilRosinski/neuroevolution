package neuroevolution.math;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {

    @Test
    void shouldCreateMatrixFromArrays() {

        // given
        final double[][] elements = {
            {0, -1, 2.5},
            {3, -4, Math.PI}
        };

        // when
        final Matrix result = new Matrix(elements);

        // then
        Assertions.assertThat(result.height()).isEqualTo(2);
        Assertions.assertThat(result.width()).isEqualTo(3);
        Assertions.assertThat(result.get(0, 0)).isEqualTo(0);
        Assertions.assertThat(result.get(0, 1)).isEqualTo(-1);
        Assertions.assertThat(result.get(0, 2)).isEqualTo(2.5);
        Assertions.assertThat(result.get(1, 0)).isEqualTo(3);
        Assertions.assertThat(result.get(1, 1)).isEqualTo(-4);
        Assertions.assertThat(result.get(1, 2)).isEqualTo(Math.PI);
    }

    @Test
    void shouldFailToCreateMatrixWhenArraysHaveDifferentLengths() {

        // given
        final double[][] elements = {
            {0, -1, 2.5},
            {3, -4, Math.PI, 42}
        };

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                new Matrix(elements);
            }).isInstanceOf(MathException.class)
            .hasMessage("Cannot create matrix. Rows have different lengths: 3 and 4.");
    }

    @Test
    void shouldCreateMatrixFromElementProvider() {

        // given
        final double[][] elements = {
            {0, -1, 2.5},
            {3, -4, Math.PI}
        };

        // when
        final Matrix result = new Matrix(2, 3, ((row, column) -> elements[row][column]));

        // then
        Assertions.assertThat(result.height()).isEqualTo(2);
        Assertions.assertThat(result.width()).isEqualTo(3);
        Assertions.assertThat(result.get(0, 0)).isEqualTo(0);
        Assertions.assertThat(result.get(0, 1)).isEqualTo(-1);
        Assertions.assertThat(result.get(0, 2)).isEqualTo(2.5);
        Assertions.assertThat(result.get(1, 0)).isEqualTo(3);
        Assertions.assertThat(result.get(1, 1)).isEqualTo(-4);
        Assertions.assertThat(result.get(1, 2)).isEqualTo(Math.PI);
    }

    @Test
    void shouldFailToCreateMatrixWithNegativeHeight() {

        // given
        final int width = 2;
        final int height = -1;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                new Matrix(height, width, (row, column) -> 0);
            }).isInstanceOf(MathException.class)
            .hasMessage("Cannot create matrix. Invalid height: -1.");
    }

    @Test
    void shouldFailToCreateMatrixWithNegativeWidth() {

        // given
        final int width = -1;
        final int height = 3;

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                new Matrix(height, width, (row, column) -> 0);
            }).isInstanceOf(MathException.class)
            .hasMessage("Cannot create matrix. Invalid width: -1.");
    }

    @Test
    void shouldMultiplyMatrixByVector() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{1, -2, 3.5},
            new double[]{-1, 0, 0.5}
        );
        final Vector vector = new Vector(2, 1, -2);

        // when
        final Vector result = matrix.multiply(vector);

        // then
        Assertions.assertThat(result).isEqualTo(new Vector(-7, -3));
    }

    @Test
    void shouldFailToMultiplyMatrixByVectorWhenDimensionsDoesNotMatch() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{0, 0, 0},
            new double[]{0, 0, 0}
        );
        final Vector vector = new Vector(0, 0);

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                matrix.multiply(vector);
            }).isInstanceOf(MathException.class)
            .hasMessage("Cannot multiply. Dimensions does not match. Matrix width: 3. Vector length: 2.");
    }

    @Test
    void shouldFailToGetElementAtNegativeRow() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{0, 1, 2},
            new double[]{3, 4, 5}
        );

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                matrix.get(-1, 0);
            }).isInstanceOf(MathException.class)
            .hasMessage("Matrix row index ot of bound. Index: -1, matrix height: 2.");
    }

    @Test
    void shouldFailToGetElementAtNegativeColumn() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{0, 1, 2},
            new double[]{3, 4, 5}
        );

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                matrix.get(0, -1);
            }).isInstanceOf(MathException.class)
            .hasMessage("Matrix column index ot of bound. Index: -1, matrix width: 3.");
    }

    @Test
    void shouldFailToGetElementAtTooHighRow() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{0, 1, 2},
            new double[]{3, 4, 5}
        );

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                matrix.get(2, 0);
            }).isInstanceOf(MathException.class)
            .hasMessage("Matrix row index ot of bound. Index: 2, matrix height: 2.");
    }

    @Test
    void shouldFailToGetElementAtTooHighColumn() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{0, 1, 2},
            new double[]{3, 4, 5}
        );

        // then
        Assertions.assertThatThrownBy(() -> {
                // when
                matrix.get(1, 4);
            }).isInstanceOf(MathException.class)
            .hasMessage("Matrix column index ot of bound. Index: 4, matrix width: 3.");
    }

    @Test
    void shouldPositivelyCompareEqualMatrices() {

        // given
        final Matrix m1 = new Matrix(
            new double[]{0, 1, 1.25},
            new double[]{3, 3.14, -5}
        );
        final Matrix m2 = new Matrix(
            new double[]{0, 1, 1.25},
            new double[]{3, 3.14, -5}
        );

        // when
        final boolean result = m1.equals(m2);

        // then
        Assertions.assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldNegativelyCompareUnequalMatrices() {

        // given
        final Matrix m1 = new Matrix(
            new double[]{0, 1, 1.25},
            new double[]{3, 3.14, -5}
        );
        final Matrix m2 = new Matrix(
            new double[]{0, 1},
            new double[]{3, 3.14},
            new double[]{-2, -1.2}
        );

        // when
        final boolean result = m1.equals(m2);

        // then
        Assertions.assertThat(m1.hashCode()).isNotEqualTo(m2.hashCode());
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void shouldStringifyMatrix() {

        // given
        final Matrix matrix = new Matrix(
            new double[]{0, 1, 1.25},
            new double[]{3, 3.14, -5}
        );

        // when
        final String result = matrix.toString();

        // then
        Assertions.assertThat(result).isEqualTo("[[0.0, 1.0, 1.25], [3.0, 3.14, -5.0]]");
    }

}
