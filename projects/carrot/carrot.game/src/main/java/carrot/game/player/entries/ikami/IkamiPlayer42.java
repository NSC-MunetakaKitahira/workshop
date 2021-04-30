package carrot.game.player.entries.ikami;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

public class IkamiPlayer42 implements JankenPlayer {

	@Override
	public void newGame() {
	}

	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {

		Random random = new Random();
		int randomValue = random.nextInt(3);
		switch (randomValue) {
		case 0:
			return JankenHand.GU;
		case 1:
			return JankenHand.PA;
		default:
			return JankenHand.CHOKI;
		}
	}
}
