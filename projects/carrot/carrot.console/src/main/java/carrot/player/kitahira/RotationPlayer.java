package carrot.player.kitahira;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class RotationPlayer implements JankenPlayer {

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		switch (currentGameStatus.round % 3) {
		case 0: return JankenHand.GU;
		case 1: return JankenHand.CHOKI;
		default: return JankenHand.PA;
		}
	}

}
