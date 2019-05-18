package carrot.player;

import carrot.game.JankenGameStatus;
import carrot.judge.JankenHand;

public interface JankenPlayer {

	JankenHand nextHand(JankenGameStatus.Subjective currentGameStatus);
}
