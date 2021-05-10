package carrot.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import carrot.game.log.MatchLog;
import carrot.game.match.JankenMatch;
import carrot.game.player.JankenPlayer;

@Path("match")
@Produces(MediaType.APPLICATION_JSON)
public class Match {

	@GET
	@Path("onematch")
	public MatchLog oneMatch(
			@QueryParam("rounds") int numberOfRounds,
			@QueryParam("player1") String player1Name,
			@QueryParam("player2") String player2Name) {

		JankenPlayer player1 = Player.findByName(player1Name).get();
		JankenPlayer player2 = Player.findByName(player2Name).get();
		JankenMatch match = new JankenMatch(numberOfRounds, player1, player2);
		
		return MatchLog.log(match);
	}
}
