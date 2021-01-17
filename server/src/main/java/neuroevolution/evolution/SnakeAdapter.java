package neuroevolution.evolution;

import neuroevolution.ann.NeuralNetwork;
import neuroevolution.math.Vector;
import neuroevolution.snake.MoveDirection;
import neuroevolution.snake.Point2D;
import neuroevolution.snake.Snake;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SnakeAdapter {

	private final Snake snake;

	public SnakeAdapter(final Snake snake) {
		this.snake = snake;
	}

	public void move(final NeuralNetwork neuralNetwork) {

		final Vector input = new Vector(
				isWallCollision(MoveDirection.UP) || isTailCollision(MoveDirection.UP) ? 0 : 1,
				isWallCollision(MoveDirection.RIGHT) || isTailCollision(MoveDirection.RIGHT) ? 0 : 1,
				isWallCollision(MoveDirection.DOWN) || isTailCollision(MoveDirection.DOWN) ? 0 : 1,
				isWallCollision(MoveDirection.LEFT) || isTailCollision(MoveDirection.LEFT) ? 0 : 1,
				isFoodTowards(MoveDirection.UP) ? 1 : 0,
				isFoodTowards(MoveDirection.RIGHT) ? 1 : 0,
				isFoodTowards(MoveDirection.DOWN) ? 1 : 0,
				isFoodTowards(MoveDirection.LEFT) ? 1 : 0
		);

		final Vector output = neuralNetwork.evaluate(input);
		final int maxValueIndex = IntStream.range(0, output.length())
				.reduce((i, j) -> output.get(i) > output.get(j) ? i : j)
				.orElseThrow(RuntimeException::new);

		snake.move(MoveDirection.values()[maxValueIndex]);
	}

	private boolean isWallCollision(final MoveDirection direction) {
		switch (direction) {
			case UP:
				return snake.getHeadField().getY() >= snake.getHeight() - 1;
			case RIGHT:
				return snake.getHeadField().getX() >= snake.getWidth() - 1;
			case DOWN:
				return snake.getHeadField().getY() <= 0;
			case LEFT:
				return snake.getHeadField().getX() <= 0;
			default:
				return false;
		}
	}

	private boolean isTailCollision(final MoveDirection direction) {
		final Point2D newHead = snake.getHeadField().add(direction.getTranslation());
		final List<Point2D> snakeFields = Arrays.asList(snake.getSnakeFields());
		final int collisionIndex = snakeFields.indexOf(newHead);
		return collisionIndex >= 0 && collisionIndex < snakeFields.size() - 1;
	}

	private boolean isFoodTowards(final MoveDirection direction) {
		if (snake.getFoodField() == null) {
			return false;
		}

		switch (direction) {
			case UP:
				return snake.getFoodField().getY() > snake.getHeadField().getY();
			case RIGHT:
				return snake.getFoodField().getX() > snake.getHeadField().getX();
			case DOWN:
				return snake.getFoodField().getY() < snake.getHeadField().getY();
			case LEFT:
				return snake.getFoodField().getX() < snake.getHeadField().getX();
			default:
				return false;
		}
	}

}
