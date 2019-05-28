package carrot.player.takakuwa;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class NewPlayer4 implements JankenPlayer {

	//前の手を保存
	private int hand;
	
	@Override
	public void newGame() {
		hand = 0;
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {

		//じゃんけん
		switch(hand){
		case 0:
			hand = 1;
			return JankenHand.GU;
		case 1:
			hand = 2;
			return JankenHand.CHOKI;
		default:
			hand = 0;
			return JankenHand.PA;
		}
	}
}
