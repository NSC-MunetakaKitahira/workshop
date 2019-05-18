package carrot.janken;

/**
 * 現在のゲーム状況
 */
public class JankenGameStatus {

	private final int maxRound;
	private int round = 0;
	private JankenHand previousPlayer1Hand = null;
	private JankenHand previousPlayer2Hand = null;
	private int player1Score = 0;
	private int player2Score = 0;

	public JankenGameStatus(int maxRound) {
		this.maxRound = maxRound;
	}
	
	/** 現在のラウンド数 */
	public int getRound() {
		return round;
	}

	public JankenHand getPreviousPlayer1Hand() {
		return previousPlayer1Hand;
	}

	public JankenHand getPreviousPlayer2Hand() {
		return previousPlayer2Hand;
	}

	/** 現在のプレイヤー１のスコア */
	public int getPlayer1Score() {
		return player1Score;
	}

	/** 現在のプレイヤー２のスコア */
	public int getPlayer2Score() {
		return player2Score;
	}
	
	public void nextRound(JankenHand player1Hand, JankenHand player2Hand) {
		JankenJudgement judgement = JankenJudgement.judge(player1Hand, player2Hand);
		round++;
		previousPlayer1Hand = player1Hand;
		previousPlayer2Hand = player2Hand;
		player1Score += judgement.player1Gain;
		player2Score += judgement.player2Gain;
	}
	
	public Subjective forPlayer1() {
		return new Subjective(round, maxRound, player1Score, player2Score, previousPlayer2Hand);
	}
	
	public Subjective forPlayer2() {
		return new Subjective(round, maxRound, player2Score, player1Score, previousPlayer1Hand);
	}
	
	public String format() {
		return formatRound(round, maxRound)
				+ " P1" + formatHand(previousPlayer1Hand) + ": " + formatScore(player1Score, maxRound)
				+ "  - " + formatScore(player2Score, maxRound) + " :P2" + formatHand(previousPlayer2Hand);
	}
	
	private static String formatRound(int currentRound, int maxRound) {
		int length = String.valueOf(maxRound).length();
		return String.format("[%" + length + "d]", currentRound);
	}
	
	private static String formatScore(int currentScore, int maxRound) {
		int length = String.valueOf(maxRound * JankenHand.maxGain()).length();
		return String.format("%" + length + "d", currentScore);
	}
	
	private static String formatHand(JankenHand hand) {
		return "(" + hand.format() + ")";
	}
	
	public static class Subjective {
		
		public final int round;
		public final int maxRound;
		public final int ownScore;
		public final int opponentScore;
		public final JankenHand previousOpponentHand;
		
		public Subjective(int round, int maxRound, int ownScore, int opponentScore, JankenHand previousOpponentHand) {
			this.round = round;
			this.maxRound = maxRound;
			this.ownScore = ownScore;
			this.opponentScore = opponentScore;
			this.previousOpponentHand = previousOpponentHand;
		}
		
		/** 最初のラウンドであれば true */
		public boolean isFirstRound() {
			return round == 0;
		}
	}
}
