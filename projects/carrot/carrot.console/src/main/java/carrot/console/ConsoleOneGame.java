package carrot.console;

import carrot.game.judge.JankenHand;
import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.match.JankenMatchStatus;
import carrot.game.player.JankenPlayer;
import carrot.game.player.sample.AlwaysPaPlayer;
import carrot.game.player.sample.PreviousHandPlayer;

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
		
		JankenMatch game = new JankenMatch(numberOfRounds, player1, player2);
		
		JankenMatchResult result = game.start(status -> {
			System.out.println(format(status));
		});
		
		System.out.println(result.format());
	}

	private static String format(JankenMatchStatus status) {
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
