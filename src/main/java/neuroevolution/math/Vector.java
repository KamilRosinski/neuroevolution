package neuroevolution.math;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;

public class Vector {

    private final double[] elements;

    public Vector(final double... elements) {
        this.elements = elements;
    }

    public Vector(final int length, final IntToDoubleFunction elementProvider) {
        if (length < 0) {
            throw new MathException("Cannot create vector. Invalid length: %d."
                    .formatted(Integer.valueOf(length)));
        }

        elements = new double[length];
        for (int index = 0; index < length; ++index) {
            elements[index] = elementProvider.applyAsDouble(index);
        }
    }

    public int length() {
        return elements.length;
    }

    public double get(final int index) {
        if (index < 0 || index >= length()) {
            throw new MathException("Vector index out of bound. Index: %d, vector length: %d."
                    .formatted(Integer.valueOf(index), Integer.valueOf(length())));
        }

        return elements[index];
    }

    public DoubleStream stream() {
        return Arrays.stream(elements);
    }

    public Vector add(final Vector other) {
        if (length() != other.length()) {
            throw new MathException("Cannot add vectors. Unequal lengths: %d and %d."
                .formatted(Integer.valueOf(length()), Integer.valueOf(other.length())));
        }

        return new Vector(length(), i -> get(i) + other.get(i));
    }

    public Vector apply(final DoubleUnaryOperator function) {
        return new Vector(length(), i -> function.applyAsDouble(get(i)));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Vector vector = (Vector) other;
        return Arrays.equals(elements, vector.elements);
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
