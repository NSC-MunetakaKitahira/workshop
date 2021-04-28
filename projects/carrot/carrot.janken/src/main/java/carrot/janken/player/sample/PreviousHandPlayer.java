package carrot.janken.player.sample;

import carrot.janken.judge.JankenHand;
import carrot.janken.player.JankenPlayer;
import carrot.janken.player.SubjectiveGameStatus;

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
		
		// 直前のラウンドで相手が出した手に勝てる手を出す
		return currentGameStatus.previousOpponentHand.handToWin();
	}

}
