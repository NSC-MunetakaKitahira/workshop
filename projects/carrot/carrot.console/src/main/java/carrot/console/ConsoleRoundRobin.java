package carrot.console;

import java.util.Arrays;

import carrot.game.JankenGame;
import carrot.game.JankenGame.Result;
import carrot.game.roundrobin.RoundRobinCompetition;
import carrot.game.roundrobin.WinPoints;
import carrot.player.kitahira.AlwaysPaPlayer;
import carrot.player.kitahira.PreviousHandPlayer;

/**
 * 総当たり戦をコンソールに出力
 */
public class ConsoleRoundRobin {

	public static void start(int numberOfRounds) {
		
		RoundRobinCompetition compet = new RoundRobinCompetition(numberOfRounds, Arrays.asList(
				new AlwaysPaPlayer(),
				new PreviousHandPlayer()
				));
		
		compet.start(
				game -> gameStarting(game),
				gameResult -> gameFinished(gameResult),
				winPoints -> competitionFinished(winPoints));
	}

	private static void gameStarting(JankenGame game) {
		// ゲーム開始時
		System.out.println(
				"P1:" + game.player1.getClass().getSimpleName() 
				+ " vs P2:" + game.player2.getClass().getSimpleName());
	}

	private static void gameFinished(Result gameResult) {
		// ゲーム結果
		System.out.println(gameResult.format());
		System.out.println("---------------------------------");
		
		// 入力待ちにして一時停止
		ConsoleInput.waitEnter();
	}
	

	private static void competitionFinished(WinPoints winPoints) {
		// 総当たり結果
		System.out.println("========= RESULT =========");
		
		winPoints.sortedList().forEach(x -> {
			System.out.println(x.player.getSimpleName() + ": " + x.points + " (" + x.subPoints + ")");
		});
	}
}
