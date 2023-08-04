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

    public Vector(final int length, final IntToDoubleFunction supplier) {
        if (length < 0) {
            throw new MathException("Cannot create vector. Invalid length: %d."
                    .formatted(Integer.valueOf(length)));
        }

        elements = new double[length];
        for (int index = 0; index < length; ++index) {
            elements[index] = supplier.applyAsDouble(index);
        }
    }

    public int length() {
        return elements.length;
    }

    public double get(final int index) {
        if (index < 0 || index >= elements.length) {
            throw new MathException("Vector index out of bound. Index: %s, vector length: %s."
                    .formatted(Integer.valueOf(index), Integer.valueOf(elements.length)));
        }

        return elements[index];
    }

    public DoubleStream stream() {
        return Arrays.stream(elements);
    }

    public Vector apply(final DoubleUnaryOperator function) {
        return new Vector(elements.length, i -> function.applyAsDouble(elements[i]));
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
