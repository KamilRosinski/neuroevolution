package neuroevolution.evolution;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Generation {

	private final Map<Organism, Integer> organisms = new HashMap<>();

	public void addOrganism(final Organism organism, final int fitness) {
		organisms.put(organism, Integer.valueOf(fitness));
	}

	public Map<Organism, Integer> getOrganisms() {
		return organisms.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

}
