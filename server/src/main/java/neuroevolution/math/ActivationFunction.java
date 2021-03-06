package neuroevolution.math;

public enum ActivationFunction {

	RELU(value -> Math.max(0, value));

	private final ValueModifier function;

	ActivationFunction(final ValueModifier function) {
		this.function = function;
	}

	public ValueModifier getFunction() {
		return function;
	}

}
