package carrot.player.kitahira;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

/**
 * 相手が最も頻繁に出す手をそのまま返すプレイヤー
 */
public class CountingPlayer implements JankenPlayer {

	private int gu = 0;
	private int choki = 0;
	private int pa = 0;
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {

		if (!currentGameStatus.isFirstRound()) {
			// 相手の手をカウント
			if (currentGameStatus.previousOpponentHand == JankenHand.GU) {
				gu++;
			} else if (currentGameStatus.previousOpponentHand == JankenHand.CHOKI) {
				choki++;
			} else {
				pa++;
			}
		}
		
		return mostFrequentlyHand();
	}
	
	private JankenHand mostFrequentlyHand() {
		if (gu > choki && gu > pa) {
			return JankenHand.GU;
		}
		
		if (choki > pa) {
			return JankenHand.CHOKI;
		}
		
		return JankenHand.PA;
	}
}
