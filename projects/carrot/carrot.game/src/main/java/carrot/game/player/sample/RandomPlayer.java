package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

/**
 * ランダムに手を選ぶプレイヤー（課題がつまらなくなるのでランダム禁止）
 */
public class RandomPlayer implements JankenPlayer {

	private Random random;
	
	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		
		int value = random.nextInt(3);
		switch (value) {
		case 0:
			return JankenHand.GU;
		case 1:
			return JankenHand.CHOKI;
		default:
			return JankenHand.PA;
		}
	}

}
