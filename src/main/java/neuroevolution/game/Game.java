package neuroevolution.game;

import neuroevolution.random.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private final int width;
	private final int height;
	private final int endurance;

	private PlayerState playerState = PlayerState.ALIVE;
	private Point2D playerField;
	private Point2D foodField;
	private int score = 0;
	private int energy;

	public Game(final int width, final int height, final int endurance) {
		this.width = width;
		this.height = height;
		this.endurance = endurance;
		playerField = new Point2D(width / 2, height / 2);
		updateFoodField();
		resetEnergy();
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public Point2D getPlayerField() {
		return playerField;
	}

	public Point2D getFoodField() {
		return foodField;
	}

	public int getScore() {
		return score;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void move(final MoveDirection moveDirection) {
		if (playerState != PlayerState.ALIVE) {
			throw new GameException("Cannot move. Player is no longer alive.");
		}

		final Point2D newPlayerField = playerField.add(moveDirection.getTranslation());
		if (newPlayerField.getX() < 0 || newPlayerField.getX() >= width || newPlayerField.getY() < 0 || newPlayerField.getY() >= height) {
			playerState = PlayerState.WALL_COLLISION;
		} else {
			playerField = newPlayerField;
			--energy;
			if (playerField.equals(foodField)) {
				++score;
				resetEnergy();
				updateFoodField();
			} else if (energy <= 0) {
				playerState = PlayerState.STARVATION;
			}
		}

		if (score >= 10000) {
			playerState = PlayerState.WINNER;
		}
	}

	private void updateFoodField() {
		final List<Point2D> availableFields = new ArrayList<>(width * height - 1);
		for (int row = 0; row < height; ++row) {
			for (int column = 0; column < width; ++column) {
				final Point2D field = new Point2D(column, row);
				if (!field.equals(playerField)) {
					availableFields.add(field);
				}
			}
		}
		foodField = RandomUtils.pick(availableFields);
	}

	private void resetEnergy() {
		energy = endurance;
	}

}
