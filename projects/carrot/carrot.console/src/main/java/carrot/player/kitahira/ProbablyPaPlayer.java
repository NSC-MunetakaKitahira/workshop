package carrot.player.kitahira;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

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
