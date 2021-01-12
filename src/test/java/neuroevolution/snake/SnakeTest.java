package neuroevolution.snake;

import neuroevolution.random.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

public class SnakeTest {

	@ParameterizedTest
	@MethodSource("provideValuesForMove")
	void shouldMove(final List<MoveDirection> moves, final Point2D expectedHeadField, final int expectedScore, final PlayerState expectedPlayerState) {
		try (final MockedStatic<RandomUtils> randomUtilsMock = Mockito.mockStatic(RandomUtils.class)) {
			// given
			randomUtilsMock.when(() -> RandomUtils.pick(Mockito.anyList())).thenReturn(
					new Point2D(2, 2),
					new Point2D(0, 2),
					new Point2D(1, 1),
					new Point2D(2, 2),
					new Point2D(3, 0));
			final Snake snake = new Snake(4, 3, 5);

			// when
			moves.forEach(snake::move);

			// then
			Assertions.assertThat(snake.getHeadField()).isEqualTo(expectedHeadField);
			Assertions.assertThat(snake.getScore()).isEqualTo(expectedScore);
			Assertions.assertThat(snake.getPlayerState()).isEqualTo(expectedPlayerState);
			randomUtilsMock.verify(Mockito.times(snake.getScore() + 1), () -> RandomUtils.pick(Mockito.anyList()));

		}
	}

	private static Stream<Arguments> provideValuesForMove() {
		return Stream.of(
				Arguments.of(
						// create snake
						Collections.emptyList(),
						new Point2D(2, 1),
						Integer.valueOf(0),
						PlayerState.ALIVE),
				Arguments.of(
						// move forward
						Collections.singletonList(MoveDirection.RIGHT),
						new Point2D(3, 1),
						Integer.valueOf(0),
						PlayerState.ALIVE),
				Arguments.of(
						// turn
						Collections.singletonList(MoveDirection.DOWN),
						new Point2D(2, 0),
						Integer.valueOf(0),
						PlayerState.ALIVE),
				Arguments.of(
						// ignore illegal move
						Collections.singletonList(MoveDirection.LEFT),
						new Point2D(3, 1),
						Integer.valueOf(0),
						PlayerState.ALIVE),
				Arguments.of(
						// crash into wall
						Arrays.asList(MoveDirection.RIGHT, MoveDirection.RIGHT),
						new Point2D(3, 1),
						Integer.valueOf(0),
						PlayerState.WALL_COLLISION),
				Arguments.of(
						// eat food
						Collections.singletonList(MoveDirection.UP),
						new Point2D(2, 2),
						Integer.valueOf(1),
						PlayerState.ALIVE),
				Arguments.of(
						// eat food & crash into wall
						Arrays.asList(MoveDirection.UP, MoveDirection.UP),
						new Point2D(2, 2),
						Integer.valueOf(1),
						PlayerState.WALL_COLLISION),
				Arguments.of(
						// run out of energy
						Arrays.asList(MoveDirection.RIGHT, MoveDirection.DOWN, MoveDirection.LEFT, MoveDirection.UP, MoveDirection.RIGHT),
						new Point2D(3, 1),
						Integer.valueOf(0),
						PlayerState.STARVATION),
				Arguments.of(
						// collide with tail
						Arrays.asList(MoveDirection.UP, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.DOWN, MoveDirection.LEFT, MoveDirection.UP),
						new Point2D(2, 1),
						Integer.valueOf(4),
						PlayerState.TAIL_COLLISION));
	}

	@Disabled
	@ParameterizedTest
	@MethodSource("provideValuesForExceptionHandling")
	void shouldThrowException(final List<MoveDirection> moves, final Point2D expectedPlayerField, final PlayerState expectedPlayerState) {
		try (final MockedStatic<RandomUtils> randomUtilsMock = Mockito.mockStatic(RandomUtils.class)) {
			// given
			randomUtilsMock.when(() -> RandomUtils.pick(Mockito.anyList())).thenReturn(new Point2D(0, 0));
			final Snake snake = new Snake(4, 3, 2);

			// when
			final SnakeException result = Assertions.catchThrowableOfType(() -> moves.forEach(snake::move), SnakeException.class);

			// then
			Assertions.assertThat(result).isNotNull();
			Assertions.assertThat(result.getMessage()).isEqualTo("Cannot move. Player is no longer alive.");
			Assertions.assertThat(snake.getScore()).isEqualTo(0);
			Assertions.assertThat(snake.getHeadField()).isEqualTo(expectedPlayerField);
			Assertions.assertThat(snake.getPlayerState()).isEqualTo(expectedPlayerState);
			randomUtilsMock.verify(() -> RandomUtils.pick(Mockito.anyList()));
		}
	}

	private static Stream<Arguments> provideValuesForExceptionHandling() {
		return Stream.of(
				Arguments.of(Arrays.asList(MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.LEFT), new Point2D(3, 1), PlayerState.WALL_COLLISION),
				Arguments.of(Arrays.asList(MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT), new Point2D(3, 2), PlayerState.STARVATION));
	}

}
