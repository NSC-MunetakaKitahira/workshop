package carrot.web;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import carrot.game.player.JankenPlayer;
import carrot.game.player.Players;

/**
 * 参加可能なプレイヤーを定義する
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("players")
public class Player {

	public static Optional<JankenPlayer> findByName(String playerName) {
		return Players.findAll().stream()
				.filter(p -> p.getClass().getSimpleName().equals(playerName))
				.findFirst();
	}
	
	@GET
	@Path("names")
	public List<String> playerNames() {
		return Players.findAll().stream()
				.map(p -> p.getClass().getSimpleName())
				.collect(toList());
	}
}
