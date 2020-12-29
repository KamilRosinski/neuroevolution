package neuroevolution.snake;

import neuroevolution.random.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SnakeTest {

	@ParameterizedTest
	@MethodSource("provideValuesForMove")
	void shouldMove(final List<MoveDirection> moves, final Point2D expectedHeadField, final int expectedScore, final PlayerState expectedPlayerState) {
		try (final MockedStatic<RandomUtils> randomUtilsMock = Mockito.mockStatic(RandomUtils.class)) {
			// given
			randomUtilsMock.when(() -> RandomUtils.pick(Mockito.anyList())).thenReturn(new Point2D(2, 2));
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
				Arguments.of(Collections.emptyList(), new Point2D(2, 1), Integer.valueOf(0), PlayerState.ALIVE)
		);
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
				Arguments.of(Arrays.asList(MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT), new Point2D(3, 2), PlayerState.STARVATION)
		);
	}

}
