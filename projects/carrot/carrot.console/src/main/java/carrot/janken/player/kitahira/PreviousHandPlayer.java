package carrot.janken.player.kitahira;

import carrot.janken.JankenHand;
import carrot.janken.JankenPlayer;
import carrot.janken.JankenGameStatus.Subjective;

public class PreviousHandPlayer implements JankenPlayer {

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		if (currentGameStatus.isFirstRound()) {
			return JankenHand.PA;
		}
		
		return currentGameStatus.previousOpponentHand.handToWin();
	}

}
