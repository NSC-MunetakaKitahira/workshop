package carrot.player.kitahira;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class PreviousHandPlayer implements JankenPlayer {

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		if (currentGameStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}
		
		return currentGameStatus.previousOpponentHand.handToWin();
	}

}
