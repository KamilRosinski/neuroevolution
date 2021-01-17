package neuroevolution.math;

import neuroevolution.exception.InvalidDimensionsException;

public class Matrix {

	private final int height;
	private final int width;
	private final double[][] elements;

	public Matrix(final int height, final int width, final MatrixValueProvider valueProvider) {
		this.height = height;
		this.width = width;
		elements = new double[height][width];
		for (int row = 0; row < height; ++row) {
			for (int column = 0; column < width; ++column) {
				elements[row][column] = valueProvider.generateValue(row, column);
			}
		}
	}

	public int height() {
		return height;
	}

	public int width() {
		return width;
	}

	public double get(final int row, final int column) {
		if (row < 0 || row >= height || column < 0 || column >= width) {
			throw new IndexOutOfBoundsException("Cannot access matrix element. Index out of bound.");
		}

		return elements[row][column];
	}

	public Vector multiply(final Vector vector) {
		if (width != vector.length()) {
			throw new InvalidDimensionsException("Cannot multiply. Matrix and vector dimensions does not match.");
		}

		final VectorValueProvider valueProvider = index -> {
			double result = 0;
			for (int i = 0; i < width; ++i) {
				result += elements[index][i] * vector.get(i);
			}
			return result;
		};

		return new Vector(height, valueProvider);
	}

	public Matrix apply(final ValueModifier modifier) {
		return new Matrix(height, width, (row, column) -> modifier.apply(elements[row][column]));
	}

}
