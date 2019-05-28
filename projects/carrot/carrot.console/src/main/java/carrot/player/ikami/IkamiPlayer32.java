package carrot.player.ikami;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;
//import java.util.ArrayList;
//import carrot.player.kitahira.PreviousHandPlayer;
import carrot.player.ikami.IkamiPlayer32;

public class IkamiPlayer32 implements JankenPlayer {
		@Override
	public void newGame() {
	}
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		return JankenHand.GU;
	}
 
}
