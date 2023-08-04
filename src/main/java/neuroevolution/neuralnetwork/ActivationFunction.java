package neuroevolution.neuralnetwork;

import java.util.function.DoubleUnaryOperator;

public enum ActivationFunction {

    RE_LU(x -> Math.max(0, x)),
    SIGMOID(x -> 1 / (1 + Math.exp(-x)));

    private final DoubleUnaryOperator function;

    ActivationFunction(final DoubleUnaryOperator function) {
        this.function = function;
    }

    public DoubleUnaryOperator getFunction() {
        return function;
    }

}
