package neuroevolution.math;

@FunctionalInterface
public interface MatrixValueProvider {

	double generateValue(int row, int column);

}
