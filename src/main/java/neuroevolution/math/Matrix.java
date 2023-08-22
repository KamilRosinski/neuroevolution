package neuroevolution.math;

import java.util.Arrays;

public class Matrix {

    private final double[][] elements;

    public Matrix(final double[]... elements) {
        if (elements.length > 0) {
            final int firstRowLength = elements[0].length;
            for (int row = 1; row < elements.length; ++row) {
                final int currentRowLength = elements[row].length;
                if (currentRowLength != firstRowLength) {
                    throw new MathException("Cannot create matrix. Rows have different lengths: %d and %d."
                        .formatted(Integer.valueOf(firstRowLength), Integer.valueOf(currentRowLength)));
                }
            }
        }

        this.elements = elements;
    }

    public Matrix(final int height, final int width, final IntToDoubleBiFunction elementProvider) {
        if (height < 0) {
            throw new MathException("Cannot create matrix. Invalid height: %d."
                .formatted(Integer.valueOf(height)));
        }
        if (width < 0) {
            throw new MathException("Cannot create matrix. Invalid width: %d."
                .formatted(Integer.valueOf(width)));
        }

        elements = new double[height][width];
        for (int column = 0; column < width; ++column) {
            for (int row = 0; row < height; ++row) {
                elements[row][column] = elementProvider.apply(row, column);
            }
        }
    }

    public int height() {
        return elements.length;
    }

    public int width() {
        return elements.length > 0 ? elements[0].length : 0;
    }

    public double get(final int row, final int column) {
        if (row < 0 || row >= height()) {
            throw new MathException("Matrix row index ot of bound. Index: %d, matrix height: %d."
                .formatted(Integer.valueOf(row), Integer.valueOf(height())));
        }
        if (column < 0 || column >= width()) {
            throw new MathException("Matrix column index ot of bound. Index: %d, matrix width: %d."
                .formatted(Integer.valueOf(column), Integer.valueOf(width())));
        }

        return elements[row][column];
    }

    public Vector multiply(final Vector vector) {
        if (width() != vector.length()) {
            throw new MathException("Cannot multiply. Dimensions does not match. Matrix width: %d. Vector length: %d."
                .formatted(Integer.valueOf(width()), Integer.valueOf(vector.length())));
        }

        return new Vector(height(), row -> {
            double result = 0;
            for (int column = 0; column < width(); ++column) {
                result += get(row, column) * vector.get(column);
            }
            return result;
        });
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(elements);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Matrix matrix)) {
            return false;
        }
        return Arrays.deepEquals(elements, matrix.elements);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(elements);
    }

}
