package neuroevolution.evolution;


@FunctionalInterface
public interface GeneValueProvider {

	double generateValue(int index);

}
