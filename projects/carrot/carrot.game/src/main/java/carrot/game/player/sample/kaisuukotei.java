package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class kaisuukotei implements JankenPlayer {
	
	
	@Override
	public void newGame() {
		
	}
	
	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		
		if (currentMatchStatus.round<=60) {
				return JankenHand.PA;
		}
		
		else if (currentMatchStatus.round>=61 && currentMatchStatus.round<=210) {
				return JankenHand.CHOKI;
		}
		
		else if (currentMatchStatus.round>=211) {
				return JankenHand.GU;
		}
		
		return null;
		
	}
}
// 完成