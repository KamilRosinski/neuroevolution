package neuroevolution.evolution;

import neuroevolution.ann.NeuralNetwork;
import neuroevolution.game.Game;
import neuroevolution.game.MoveDirection;
import neuroevolution.math.Vector;

import java.util.stream.IntStream;

public class GameAdapter {

	private final Game game;

	public GameAdapter(final Game game) {
		this.game = game;
	}

	public void move(final NeuralNetwork neuralNetwork) {

		final Vector input = new Vector(
//				game.getPlayerField().getX(),
//				game.getPlayerField().getY(),
//				game.getWidth() - game.getPlayerField().getX() - 1,
//				game.getHeight() - game.getPlayerField().getY() - 1,
				game.getPlayerField().getX() - game.getFoodField().getX(),
				game.getPlayerField().getY() - game.getFoodField().getY()
		).divide(Math.max(game.getHeight(), game.getWidth()));

		final Vector output = neuralNetwork.evaluate(input);
		final int maxValueIndex = IntStream.range(0, output.length())
				.reduce((i, j) -> output.get(i) > output.get(j) ? i : j)
				.orElseThrow(RuntimeException::new);

		game.move(MoveDirection.values()[maxValueIndex]);
	}

}
