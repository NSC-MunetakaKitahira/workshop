package carrot.game.player.entries.ikami;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

public class IkamiPlayer32 implements JankenPlayer {
	@Override
	public void newGame() {
	}

	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {
		return JankenHand.GU;
	}

}
