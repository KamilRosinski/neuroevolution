package neuroevolution.game;

import neuroevolution.random.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class GameTest {

	@ParameterizedTest
	@MethodSource("provideValuesForMove")
	void shouldMove(final List<MoveDirection> moves, final Point2D expectedPlayerField, final int expectedScore, final PlayerState expectedPlayerState) {
		try (final MockedStatic<RandomUtils> randomUtilsMock = Mockito.mockStatic(RandomUtils.class)) {
			// given
			randomUtilsMock.when(() -> RandomUtils.pick(Mockito.anyList())).thenReturn(new Point2D(1, 2), new Point2D(2, 1), new Point2D(2, 2));
			final Game game = new Game(4, 3, 3);

			// when
			moves.forEach(game::move);

			// then
			Assertions.assertThat(game.getPlayerField()).isEqualTo(expectedPlayerField);
			Assertions.assertThat(game.getScore()).isEqualTo(expectedScore);
			Assertions.assertThat(game.getPlayerState()).isEqualTo(expectedPlayerState);
			randomUtilsMock.verify(Mockito.times(game.getScore() + 1), () -> RandomUtils.pick(Mockito.anyList()));

		}
	}

	private static Stream<Arguments> provideValuesForMove() {
		return Stream.of(
				Arguments.of(Collections.emptyList(), new Point2D(2, 1), Integer.valueOf(0), PlayerState.ALIVE),
				Arguments.of(Collections.singletonList(MoveDirection.LEFT), new Point2D(1, 1), Integer.valueOf(0), PlayerState.ALIVE),
				Arguments.of(Arrays.asList(MoveDirection.RIGHT, MoveDirection.RIGHT), new Point2D(3, 1), Integer.valueOf(0), PlayerState.WALL_COLLISION),
				Arguments.of(Arrays.asList(MoveDirection.LEFT, MoveDirection.DOWN, MoveDirection.RIGHT), new Point2D(2, 0), Integer.valueOf(0), PlayerState.STARVATION),
				Arguments.of(Arrays.asList(MoveDirection.LEFT, MoveDirection.UP), new Point2D(1, 2), Integer.valueOf(1), PlayerState.ALIVE),
				Arguments.of(Arrays.asList(MoveDirection.LEFT, MoveDirection.UP, MoveDirection.RIGHT, MoveDirection.DOWN), new Point2D(2, 1), Integer.valueOf(2), PlayerState.ALIVE)
		);
	}

	@ParameterizedTest
	@MethodSource("provideValuesForExceptionHandling")
	void shouldThrowException(final List<MoveDirection> moves, final Point2D expectedPlayerField, final PlayerState expectedPlayerState) {
		try (final MockedStatic<RandomUtils> randomUtilsMock = Mockito.mockStatic(RandomUtils.class)) {
			// given
			randomUtilsMock.when(() -> RandomUtils.pick(Mockito.anyList())).thenReturn(new Point2D(0, 0));
			final Game game = new Game(4, 3, 2);

			// when
			final GameException result = Assertions.catchThrowableOfType(() -> moves.forEach(game::move), GameException.class);

			// then
			Assertions.assertThat(result).isNotNull();
			Assertions.assertThat(result.getMessage()).isEqualTo("Cannot move. Player is no longer alive.");
			Assertions.assertThat(game.getScore()).isEqualTo(0);
			Assertions.assertThat(game.getPlayerField()).isEqualTo(expectedPlayerField);
			Assertions.assertThat(game.getPlayerState()).isEqualTo(expectedPlayerState);
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
