package carrot.player;

import carrot.game.JankenGameStatus;
import carrot.judge.JankenHand;

public interface JankenPlayer {
	
	void newGame();

	JankenHand nextHand(JankenGameStatus.Subjective currentGameStatus);
}
