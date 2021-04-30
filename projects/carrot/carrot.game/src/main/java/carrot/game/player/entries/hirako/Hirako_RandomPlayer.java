package carrot.game.player.entries.hirako;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

public class Hirako_RandomPlayer implements JankenPlayer {

	public void newGame() {
		// 何もしない
	}

	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {

		Random r = new Random();
		int num = r.nextInt(11);
		if       (0<=num && num<=3) {
			return JankenHand.GU;
		}else if(4<=num && num<=8) {
			return JankenHand.CHOKI;
		}else if(9<=num && num<=10) {
			return JankenHand.PA;
		}else {
			System.out.printf("\n###RandIntError %d\n",num);
			return JankenHand.PA;
		}

	}

}