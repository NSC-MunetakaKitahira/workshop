package carrot.janken.player.kitahira;

import carrot.janken.JankenGameStatus.Subjective;
import carrot.janken.JankenHand;
import carrot.janken.JankenPlayer;

public class ProbablyPaPlayer implements JankenPlayer {

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		if (currentGameStatus.round % 4 == 0) {
			return JankenHand.CHOKI;
		}
		
		if (currentGameStatus.round % 9 == 0) {
			return JankenHand.GU;
		}
		
		return JankenHand.PA;
		
	}

}
