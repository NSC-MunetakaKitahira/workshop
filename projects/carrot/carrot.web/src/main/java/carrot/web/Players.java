package carrot.web;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import carrot.game.player.JankenPlayer;
import carrot.game.player.sample.AlwaysPaPlayer;
import carrot.game.player.sample.CountingPlayer;
import carrot.game.player.sample.PreviousHandPlayer;
import carrot.game.player.sample.RandomPlayer;

/**
 * 参加可能なプレイヤーを定義する
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("players")
public class Players {

	private static final List<JankenPlayer> PLAYERS = Arrays.asList(
			new AlwaysPaPlayer(),
			new CountingPlayer(),
			new PreviousHandPlayer(),
			new RandomPlayer()
			);

	public static Optional<JankenPlayer> findByName(String playerName) {
		return PLAYERS.stream()
				.filter(p -> p.getClass().getSimpleName().equals(playerName))
				.findFirst();
	}
	
	@GET
	@Path("names")
	public List<String> playerNames() {
		return PLAYERS.stream()
				.map(p -> p.getClass().getSimpleName())
				.collect(toList());
	}
}
