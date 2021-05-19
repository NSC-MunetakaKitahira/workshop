package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;
import java.util.Random;

public class mei implements JankenPlayer {

	private Random random;

	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if(currentMatchStatus.round <= 200) {
		if (currentMatchStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}

		return currentMatchStatus.previousOpponentHand.handToWin();
		}else {
			int value = random.nextInt(100);
			if (value < 70) {
				return JankenHand.CHOKI;
			}else {
				return JankenHand.PA;
			}
		}
	}

}
