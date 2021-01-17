package neuroevolution.snake;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeModel {

	private final int width;
	private final int height;
	private final Deque<Point2D> snakeFields = new LinkedList<>();
	private Point2D foodField;

	public SnakeModel(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	public void setFoodField(final Point2D foodField) {
		this.foodField = foodField;
	}

	public Point2D getFoodField() {
		return foodField;
	}

	public Point2D getHeadField() {
		return snakeFields.peekFirst();
	}

	public void pushToSnakeFields(final Point2D field) {
		snakeFields.addFirst(field);
	}

	public void popFromSnakeFields() {
		snakeFields.removeLast();
	}

	public Point2D[] getSnakeFields() {
		return snakeFields.toArray(new Point2D[0]);
	}

	public int getSnakeLength() {
		return snakeFields.size();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
