package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

/**
 * 直前ラウンドの相手の手に勝つ手を選ぶプレイヤー
 */
public class PreviousHandPlayer implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}
	
	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {
		
		// 最初のラウンドは固定でチョキ
		if (currentGameStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}
		
		return currentGameStatus.previousOpponentHand.handToWin();
	}

}
