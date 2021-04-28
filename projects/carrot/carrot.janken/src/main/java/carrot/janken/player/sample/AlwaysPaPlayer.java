package carrot.janken.player.sample;

import carrot.janken.judge.JankenHand;
import carrot.janken.player.JankenPlayer;
import carrot.janken.player.SubjectiveGameStatus;

/**
 * パーしか出さないプレイヤー
 */
public class AlwaysPaPlayer implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}

	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {
		
		// 常にパー
		return JankenHand.PA;
	}

}
