package carrot.janken.player.kitahira;

import carrot.janken.JankenHand;
import carrot.janken.JankenPlayer;
import carrot.janken.JankenGameStatus.Subjective;

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
