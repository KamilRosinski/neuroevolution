package neuroevolution.game;

import java.util.Objects;

public class Point2D {

	private final int x;
	private final int y;

	public Point2D(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Point2D add(final Point2D other) {
		return new Point2D(x + other.x, y + other.y);
	}

	@Override
	public String toString() {
		return String.format("{x: %d, y: %d}", Integer.valueOf(x), Integer.valueOf(y));
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Point2D point2D = (Point2D) o;
		return x == point2D.x && y == point2D.y;
	}

}
