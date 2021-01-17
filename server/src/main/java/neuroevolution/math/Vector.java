package neuroevolution.math;

import neuroevolution.exception.InvalidDimensionsException;

public class Vector {

	private final double[] elements;

	public Vector(final double... elements) {
		this.elements = elements;
	}

	public Vector(final int size, final VectorValueProvider valueProvider) {
		elements = new double[size];
		for (int i = 0; i < elements.length; ++i) {
			elements[i] = valueProvider.generateValue(i);
		}
	}

	public int length() {
		return elements.length;
	}

	public double get(final int index) {
		if (index < 0 || index >= elements.length) {
			throw new IndexOutOfBoundsException("Cannot access vector element. Index out of bound.");
		}

		return elements[index];
	}

	public Vector add(final Vector other) {
		if (elements.length != other.elements.length) {
			throw new InvalidDimensionsException("Cannot add vectors. Dimensions does not match.");
		}

		return new Vector(elements.length, index -> elements[index] + other.elements[index]);
	}

	public Vector divide(final double factor) {
		return new Vector(elements.length, index -> elements[index] / factor);
	}

	public Vector apply(final ValueModifier modifier) {
		return new Vector(elements.length, index -> modifier.apply(elements[index]));
	}

}
