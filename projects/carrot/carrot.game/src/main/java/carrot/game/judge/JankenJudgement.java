package carrot.game.judge;

/**
 * ジャンケンの判定結果
 */
public class JankenJudgement {

	public final JankenHand player1Hand;
	public final JankenHand player2Hand;
	public final int player1Gain;
	public final int player2Gain;
	
	private JankenJudgement(JankenHand player1Hand, JankenHand player2Hand) {
		
		this.player1Hand = player1Hand;
		this.player2Hand = player2Hand;

		int p1Gain = 0;
		int p2Gain = 0;
		switch (player1Hand.competeAgainst(player2Hand)) {
		case WIN:
			p1Gain = player1Hand.winnerGain;
			break;
		case LOSE:
			p2Gain = player2Hand.winnerGain;
			break;
		case DRAW:
			// 引き分けは両者に1点
			p1Gain = p2Gain = 1;
			break;
		}
		
		this.player1Gain = p1Gain;
		this.player2Gain = p2Gain;
	}
	
	public static JankenJudgement judge(JankenHand player1Hand, JankenHand player2Hand) {
		return new JankenJudgement(player1Hand, player2Hand);
	}
	
}
