package neuroevolution.game;

public enum MoveDirection {

	UP(new Point2D(0, 1)),
	RIGHT(new Point2D(1, 0)),
	DOWN(new Point2D(0, -1)),
	LEFT(new Point2D(-1, 0));

	private final Point2D translation;

	MoveDirection(final Point2D translation) {
		this.translation = translation;
	}

	public Point2D getTranslation() {
		return translation;
	}

}
