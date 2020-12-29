package neuroevolution.snake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SnakeModel {

	private final int width;
	private final int height;
	private final boolean[][] emptyFields;
	private final List<Point2D> snakeFields = new LinkedList<>();
	private Point2D foodField;

	public SnakeModel(final int width, final int height) {
		this.width = width;
		this.height = height;

		emptyFields = new boolean[height][width];
		for (int row = 0; row < height; ++row) {
			for (int column = 0; column < width; ++column) {
				emptyFields[row][column] = true;
			}
		}
	}

	public void setFoodField(final Point2D foodField) {
		this.foodField = foodField;
		updateEmptyField(foodField, false);
	}

	public Point2D getFoodField() {
		return foodField;
	}

	public Point2D getHeadField() {
		return snakeFields.get(0);
	}

	public void pushToSnakeFields(final Point2D field) {
		snakeFields.add(field);
		updateEmptyField(field, false);
	}

	public void popFromSnakeFields() {
		final Point2D removedSnakeField = snakeFields.remove(0);
		updateEmptyField(removedSnakeField, true);
	}

	public Point2D[] getSnakeFields() {
		return snakeFields.toArray(new Point2D[0]);
	}

	public int getSnakeLength() {
		return snakeFields.size();
	}

	public Point2D[] getEmptyFields() {
		final List<Point2D> result = new ArrayList<>();
		for (int row = 0; row < height; ++row) {
			for (int column = 0; column < width; ++column) {
				if (emptyFields[row][column]) {
					result.add(new Point2D(column, row));
				}
			}
		}
		return result.toArray(new Point2D[0]);
	}

	private void updateEmptyField(final Point2D field, final boolean isEmpty) {
		emptyFields[field.getY()][field.getX()] = isEmpty;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
