package carrot.player.takakuwa;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class CrashPlayer implements JankenPlayer {
	
	@Override
	public void newGame() {
		
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		//Crashさせて必ず引き分け
		return null;
	}
}