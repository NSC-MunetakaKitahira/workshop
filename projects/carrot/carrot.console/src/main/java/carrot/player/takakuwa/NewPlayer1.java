package carrot.player.takakuwa;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;
import java.util.Random;

public class NewPlayer1 implements JankenPlayer {
	
	//乱数格納
	private int hand;
	
	@Override
	public void newGame() {
		
	}

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		//ランダムな０～99998の数値を生成
		Random r = new Random();
		hand = r.nextInt(99999);
		
		//ランダムにじゃんけん
		switch(hand % 3){
		case 0:
			return JankenHand.GU;
		case 1:
			return JankenHand.CHOKI;
		default:
			return JankenHand.PA;
		}
	}

}
