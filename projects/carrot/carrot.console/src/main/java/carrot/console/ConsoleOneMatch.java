package carrot.console;

import carrot.game.judge.JankenHand;
import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.match.JankenMatchStatus;
import carrot.game.player.JankenPlayer;

/**
 * 一対一の１ゲームをコンソールに出力
 */
public class ConsoleOneMatch {

	public static void start(int numberOfRounds, JankenPlayer player1, JankenPlayer player2) {
		
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

		String player1Result = "    ";
		String player2Result = "    ";
		switch (status.previousPlayer1Hand.competeAgainst(status.previousPlayer2Hand)) {
		case WIN:
			player1Result = "WIN ";
			break;
		case LOSE:
			player2Result = "WIN ";
			break;
		case DRAW:
			break;
		default:
			break;
		}
		
		return formatRound(status.processedRounds, status.maxRound)
				+ " P1("
				+ status.previousPlayer1Hand.format()
				+ ") "
				+ player1Result
				+ " : "
				+ formatScore(status.player1Score, status.maxRound)
				+ " - "
				+ formatScore(status.player2Score, status.maxRound)
				+ " :P2("
				+ status.previousPlayer2Hand.format()
				+ ") "
				+ player2Result;
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
