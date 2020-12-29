package neuroevolution.evolution;

import neuroevolution.ann.NeuralNetwork;
import neuroevolution.snake.Snake;
import neuroevolution.snake.MoveDirection;
import neuroevolution.math.Vector;

import java.util.stream.IntStream;

public class SnakeAdapter {

	private final Snake snake;

	public SnakeAdapter(final Snake snake) {
		this.snake = snake;
	}

	public void move(final NeuralNetwork neuralNetwork) {

		final Vector input = new Vector(
				snake.getHeadField().getX() - snake.getFoodField().getX(),
				snake.getHeadField().getY() - snake.getFoodField().getY()
		).divide(Math.max(snake.getHeight(), snake.getWidth()));

		final Vector output = neuralNetwork.evaluate(input);
		final int maxValueIndex = IntStream.range(0, output.length())
				.reduce((i, j) -> output.get(i) > output.get(j) ? i : j)
				.orElseThrow(RuntimeException::new);

		snake.move(MoveDirection.values()[maxValueIndex]);
	}

}
