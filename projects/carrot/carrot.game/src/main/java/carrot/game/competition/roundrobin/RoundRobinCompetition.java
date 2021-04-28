package carrot.game.competition.roundrobin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.player.JankenPlayer;

/**
 * 総当たり戦のコントロール
 */
public class RoundRobinCompetition {

	private final int numberOfRounds;
	private final List<JankenPlayer> players;

	public RoundRobinCompetition(int numberOfRounds, List<JankenPlayer> players) {
		this.numberOfRounds = numberOfRounds;
		this.players = players;
	}
	
	public void start(
			Consumer<JankenMatch> gameNotifier,
			Consumer<JankenMatchResult> gameResultNotifier,
			Consumer<WinPoints> competitionResultNotifier
			) {
		
		WinPoints winPoints = new WinPoints(playerClasses());
		
		for (JankenMatch game : createRoundRobin()) {
			
			gameNotifier.accept(game);
			
			JankenMatchResult result = game.start(s -> {});
			winPoints.process(result, game.player1.getClass(), game.player2.getClass());

			gameResultNotifier.accept(result);
		}

		competitionResultNotifier.accept(winPoints);
	}
	
	private List<Class<?>> playerClasses() {
		return players.stream().map(p -> p.getClass()).collect(Collectors.toList());
	}
	
	private List<JankenMatch> createRoundRobin() {
		
		List<JankenMatch> games = new ArrayList<>();
		
		for (int i = 0; i < players.size() - 1; i++) {
			JankenPlayer player1 = players.get(i);
			
			for (int j = i + 1; j < players.size(); j++) {
				JankenPlayer player2 = players.get(j);
				games.add(new JankenMatch(numberOfRounds, player1, player2));
			}
		}
		
		return games;
	}
}
