package neuroevolution.neuralnetwork;

import neuroevolution.math.DoubleFunction;

public enum ActivationFunction {

    RE_LU(x -> Math.max(0, x)),
    SIGMOID(x -> 1 / (1 + Math.exp(-x)));

    private final DoubleFunction function;

    ActivationFunction(final DoubleFunction function) {
        this.function = function;
    }

    public double apply(final double x) {
        return function.apply(x);
    }

}
