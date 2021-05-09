package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

/**
 * 相手が最も頻繁に出す手をそのまま返すプレイヤー
 */
public class CountingPlayer implements JankenPlayer {

	private int gu;
	private int choki;
	private int pa;
	
	@Override
	public void newGame() {
		// カウントをリセット
		gu = choki = pa = 0;
	}
	
	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if (!currentMatchStatus.isFirstRound()) {
			// 相手の手をカウント
			switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				gu++; break;
			case CHOKI:
				choki++; break;
			default:
				pa++; break;
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
