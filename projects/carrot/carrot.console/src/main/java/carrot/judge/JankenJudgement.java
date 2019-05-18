package carrot.judge;

/**
 * ジャンケンの判定結果
 */
public class JankenJudgement {

	public final int player1Gain;
	public final int player2Gain;
	
	private JankenJudgement(int player1Gain, int player2Gain) {
		this.player1Gain = player1Gain;
		this.player2Gain = player2Gain;
	}
	
	public static JankenJudgement judge(JankenHand player1Hand, JankenHand player2Hand) {
		
		switch (player1Hand.competeAgainst(player2Hand)) {
		case WIN:
			return new JankenJudgement(player1Hand.winnerGain, 0);
		case LOSE:
			return new JankenJudgement(0, player2Hand.winnerGain);
		default:
			return new JankenJudgement(0, 0);
		}
		
	}
	
}
