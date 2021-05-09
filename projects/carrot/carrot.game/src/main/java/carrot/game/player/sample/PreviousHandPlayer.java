package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

/**
 * 直前ラウンドの相手の手に勝つ手を選ぶプレイヤー
 */
public class PreviousHandPlayer implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}
	
	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		
		// 最初のラウンドは固定でチョキ
		if (currentMatchStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}
		
		return currentMatchStatus.previousOpponentHand.handToWin();
	}

}
