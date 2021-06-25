package carrot.console;

import java.util.Arrays;
import java.util.List;

import carrot.game.competition.roundrobin.RoundRobinCompetition;
import carrot.game.competition.roundrobin.WinPoints;
import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.player.JankenPlayer;
//import carrot.game.player.sample.AlwaysPaPlayer;
//import carrot.game.player.sample.CountingPlayer;
//import carrot.game.player.sample.PreviousHandPlayer;

/**
 * 総当たり戦をコンソールに出力
 */
public class ConsoleRoundRobin {
	
	public static void start(int numberOfRounds) {
		
		List<JankenPlayer> players = getPlayers();
		RoundRobinCompetition compet = new RoundRobinCompetition(numberOfRounds, players);
		
		compet.start(
				game -> gameStarting(game),
				gameResult -> gameFinished(gameResult),
				winPoints -> competitionFinished(winPoints));
	}

	private static List<JankenPlayer> getPlayers() {
		
		return Arrays.asList(
//				new AlwaysPaPlayer(),
//				new CountingPlayer(),
//				new PreviousHandPlayer()
				);
		
	}

	private static void gameStarting(JankenMatch game) {
		// ゲーム開始時
		System.out.println(
				"P1:" + game.player1.getClass().getSimpleName() 
				+ " vs P2:" + game.player2.getClass().getSimpleName());
	}

	private static void gameFinished(JankenMatchResult gameResult) {
		// ゲーム結果
		System.out.println(gameResult.format());
		System.out.println("---------------------------------");
		
		// 入力待ちにして一時停止
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	

	private static void competitionFinished(WinPoints winPoints) {
		// 総当たり結果
		System.out.println("========= RESULT =========");
		
		winPoints.sortedList().forEach(x -> {
			System.out.println(x.player.getSimpleName() + ": " + x.points + " (" + x.subPoints + ")");
		});
	}
}
