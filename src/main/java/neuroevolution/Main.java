package neuroevolution;

import neuroevolution.evolution.Evolution;

import java.util.Arrays;

public class Main {

	public static void main(final String[] args) {
		final Evolution evolution = new Evolution();
		int generationNumber = 0;
		int bestResult = 0;
		do {
			++generationNumber;
			final int[] generationResult = evolution.nextGeneration();
			bestResult = Arrays.stream(generationResult).max().orElse(0);
			final double avgResult = Arrays.stream(generationResult).average().orElse(0);
			System.out.printf("Generation %3d: max: %5d, avg: %s\n", Integer.valueOf(generationNumber), Integer.valueOf(bestResult), Double.valueOf(avgResult));
		} while (true);
	}

}
