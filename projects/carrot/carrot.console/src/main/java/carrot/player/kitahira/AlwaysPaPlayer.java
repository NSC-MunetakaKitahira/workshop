package carrot.player.kitahira;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class AlwaysPaPlayer implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		// 常にパー
		return JankenHand.PA;
	}

}
