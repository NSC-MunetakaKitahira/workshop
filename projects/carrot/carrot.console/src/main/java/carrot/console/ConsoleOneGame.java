package carrot.console;

import carrot.game.JankenGame;
import carrot.game.JankenGameStatus;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;
//import carrot.player.kitahira.AlwaysPaPlayer;
//import carrot.player.kitahira.PreviousHandPlayer;
//import carrot.player.kitahira.CountingPlayer;
import carrot.player.takakuwa.NewPlayer1;
//import carrot.player.takakuwa.NewPlayer2;
//import carrot.player.takakuwa.NewPlayer3;
//import carrot.player.takakuwa.NewPlayer4;
//import carrot.player.takakuwa.NewPlayer5;
//import carrot.player.takakuwa.TakakuwaPlayer3;
import carrot.player.takakuwa.NewPlayer7;

/**
 * 一対一の１ゲームをコンソールに出力
 */
public class ConsoleOneGame {

	public static void start(int numberOfRounds) {
		
		//JankenPlayer player1 = new PreviousHandPlayer();
		JankenPlayer player1 = new NewPlayer1();
		//JankenPlayer player2 = new AlwaysPaPlayer();
		JankenPlayer player2 = new NewPlayer7();
		
		System.out.println(
				"P1:" + player1.getClass().getSimpleName() 
				+ " vs P2:" + player2.getClass().getSimpleName());
		
		JankenGame game = new JankenGame(numberOfRounds, player1, player2);
		
		JankenGame.Result result = game.start(status -> {
			System.out.println(format(status));
		});
		
		System.out.println(result.format());
	}

	private static String format(JankenGameStatus status) {
		return formatRound(status.processedRounds, status.maxRound)
				+ " P1("
				+ status.previousPlayer1Hand.format()
				+ "): "
				+ formatScore(status.player1Score, status.maxRound)
				+ " - "
				+ formatScore(status.player2Score, status.maxRound)
				+ " :P2("
				+ status.previousPlayer2Hand.format()
				+ ")";
	}
	
	private static String formatRound(int currentRound, int maxRound) {
		int length = String.valueOf(maxRound).length();
		return String.format("[%" + length + "d]", currentRound);
	}
	
	private static String formatScore(int currentScore, int maxRound) {
		int length = String.valueOf(maxRound * JankenHand.maxGain()).length();
		return String.format("%" + length + "d", currentScore);
	}
	
}
