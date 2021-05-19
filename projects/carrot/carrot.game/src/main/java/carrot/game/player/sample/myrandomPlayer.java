/**
 * gu=90/300
 * pa=60/300
 * choki=150/300*/

package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class myrandomPlayer implements JankenPlayer{

	private Random random;
	
	private int gu;
	private int choki;
	private int pa;

	@Override
	public void newGame() {
		gu=choki=pa=0;
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		int value = random.nextInt(300);
		
			if (value>=0 && value<150) {
				return JankenHand.CHOKI;
			}
			
			if (value>150 && value<=240) {
				return JankenHand.GU;
			}
			
			else {
				return JankenHand.PA;
			}
			
			
	}
}
