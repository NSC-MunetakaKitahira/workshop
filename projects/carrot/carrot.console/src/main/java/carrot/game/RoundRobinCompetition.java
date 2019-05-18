package carrot.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import carrot.janken.JankenGame;
import carrot.janken.JankenPlayer;

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
	
	public void start() {
		
		WinPoints<Class<?>> winPoints = new WinPoints<>(playerClasses());
		
		for (JankenGame game : createRoundRobin()) {
			
			System.out.println(
					"P1:" + game.player1.getClass().getSimpleName() 
					+ " vs P2:" + game.player2.getClass().getSimpleName());
			
			JankenGame.Result result = game.start(s -> { });
			winPoints.process(result, game.player1.getClass(), game.player2.getClass());

			System.out.println(result.format());
		}
		
		System.out.println("---------------------------------");
		
		winPoints.sortedList().forEach(x -> {
			System.out.println(x.player.getSimpleName() + ": " + x.points + " (" + x.subPoints + ")");
		});
	}
	
	private List<Class<?>> playerClasses() {
		return players.stream().map(p -> p.getClass()).collect(Collectors.toList());
	}
	
	private List<JankenGame> createRoundRobin() {
		
		List<JankenGame> games = new ArrayList<>();
		
		for (int i = 0; i < players.size() - 1; i++) {
			JankenPlayer player1 = players.get(i);
			
			for (int j = i + 1; j < players.size(); j++) {
				JankenPlayer player2 = players.get(j);
				games.add(new JankenGame(numberOfRounds, player1, player2));
			}
		}
		
		return games;
	}
}
