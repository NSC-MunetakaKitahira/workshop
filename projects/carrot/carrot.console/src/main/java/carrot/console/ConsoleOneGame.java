package carrot.console;

import carrot.janken.game.JankenGame;
import carrot.janken.game.JankenGameResult;
import carrot.janken.game.JankenGameStatus;
import carrot.janken.judge.JankenHand;
import carrot.janken.player.JankenPlayer;
import carrot.janken.player.sample.AlwaysPaPlayer;
import carrot.janken.player.sample.PreviousHandPlayer;

/**
 * 一対一の１ゲームをコンソールに出力
 */
public class ConsoleOneGame {

	public static void start(int numberOfRounds) {
		
		JankenPlayer player1 = new PreviousHandPlayer();
		JankenPlayer player2 = new AlwaysPaPlayer();
		
		System.out.println(
				"P1:" + player1.getClass().getSimpleName() 
				+ " vs P2:" + player2.getClass().getSimpleName());
		
		JankenGame game = new JankenGame(numberOfRounds, player1, player2);
		
		JankenGameResult result = game.start(status -> {
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
