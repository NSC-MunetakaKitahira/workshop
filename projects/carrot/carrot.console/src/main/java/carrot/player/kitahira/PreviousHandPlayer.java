package carrot.player.kitahira;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class PreviousHandPlayer implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		// 最初のラウンドは固定でチョキ
		if (currentGameStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}
		
		// 直前のラウンドで相手が出した手に勝てる手を出す
		return currentGameStatus.previousOpponentHand.handToWin();
	}

}
