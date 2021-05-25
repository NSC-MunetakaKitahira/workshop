package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;
import java.util.Random;

public class NewPro implements JankenPlayer {
	
	
	private Random random;
	@Override
	public void newGame() {
	this.random =new Random();	
	}
	
	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		
		if (currentMatchStatus.isFirstRound()) {
			
			return JankenHand.CHOKI;
		}
		int i=random.nextInt(11);
		if(0<=i&&i<=3) {
			return JankenHand.GU;
		}
		else if(4<=i&&i<=8) {
			return JankenHand.CHOKI;
		}
		else {
			return JankenHand.PA;
			
		}
	}

}
