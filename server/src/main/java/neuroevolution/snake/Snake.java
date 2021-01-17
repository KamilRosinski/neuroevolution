package neuroevolution.snake;

import neuroevolution.random.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Snake {

	private final int endurance;
	private final SnakeModel model;

	private PlayerState playerState = PlayerState.ALIVE;
	private MoveDirection headDirection = MoveDirection.RIGHT;
	private int energy;

	public Snake(final int width, final int height, final int endurance) {
		this.endurance = endurance;
		model = new SnakeModel(width, height);
		model.pushToSnakeFields(new Point2D(width / 2, height / 2));
		resetEnergy();
		updateFoodField();
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public Point2D getFoodField() {
		return model.getFoodField();
	}

	public Point2D getHeadField() {
		return model.getHeadField();
	}

	public Point2D[] getSnakeFields() {
		return model.getSnakeFields();
	}

	public int getScore() {
		return model.getSnakeLength() - 1;
	}

	public int getHeight() {
		return model.getHeight();
	}

	public int getWidth() {
		return model.getWidth();
	}

	public void move(final MoveDirection moveDirection) {
		if (playerState != PlayerState.ALIVE) {
			throw new SnakeException("Cannot move. Snake is no longer alive.");
		}

		if (headDirection.getTranslation().chessDistance(moveDirection.getTranslation()) == 1) {
			headDirection = moveDirection;
		}

		final Point2D newHeadField = model.getHeadField().add(headDirection.getTranslation());

		if (newHeadField.equals(model.getFoodField())) {
			model.pushToSnakeFields(newHeadField);
			resetEnergy();
			updateFoodField();
		} else {
			if (detectWallCollision(newHeadField)) {
				playerState = PlayerState.WALL_COLLISION;
			} else if (detectTailCollision(newHeadField)) {
				playerState = PlayerState.TAIL_COLLISION;
			} else {
				model.popFromSnakeFields();
				model.pushToSnakeFields(newHeadField);

				if (--energy <= 0) {
					playerState = PlayerState.STARVATION;
				}
			}
		}
	}

	private boolean detectWallCollision(final Point2D newHeadField) {
		return newHeadField.getX() < 0
				|| newHeadField.getX() >= model.getWidth()
				|| newHeadField.getY() < 0
				|| newHeadField.getY() >= model.getHeight();
	}

	private boolean detectTailCollision(final Point2D newHeadField) {
		final int collisionIndex = Arrays.asList(model.getSnakeFields()).indexOf(newHeadField);
		return collisionIndex >= 0 && collisionIndex < model.getSnakeLength() - 1;

	}

	private void resetEnergy() {
		energy = endurance;
	}

	private void updateFoodField() {
		final Set<Point2D> snakeFields = Arrays.stream(model.getSnakeFields()).collect(Collectors.toSet());

		final List<Point2D> availableFields = new ArrayList<>();
		for (int row = 0; row < model.getHeight(); ++row) {
			for (int column = 0; column < model.getWidth(); ++column) {
				final Point2D field = new Point2D(column, row);
				if (!snakeFields.contains(field)) {
					availableFields.add(field);
				}
			}
		}

		if (availableFields.isEmpty()) {
			System.out.println(Arrays.toString(model.getSnakeFields()));
		}

		model.setFoodField(availableFields.isEmpty() ? null : RandomUtils.pick(availableFields));
	}

}
