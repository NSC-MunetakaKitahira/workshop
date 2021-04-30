package carrot.game.player;

import carrot.game.judge.JankenHand;

public interface JankenPlayer {
	
	void newGame();

	JankenHand nextHand(SubjectiveGameStatus currentGameStatus);
}
