package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

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
