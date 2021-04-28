package carrot.janken.player;

import carrot.janken.judge.JankenHand;

public interface JankenPlayer {
	
	void newGame();

	JankenHand nextHand(SubjectiveGameStatus currentGameStatus);
}
