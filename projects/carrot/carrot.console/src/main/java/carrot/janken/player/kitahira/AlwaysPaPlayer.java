package carrot.janken.player.kitahira;

import carrot.janken.JankenHand;
import carrot.janken.JankenPlayer;
import carrot.janken.JankenGameStatus.Subjective;

public class AlwaysPaPlayer implements JankenPlayer {

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		return JankenHand.PA;
	}

}
