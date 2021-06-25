package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;
import java.util.Random;

public class meimei implements JankenPlayer {

	private Random random;
	private int gu;
	private int choki;
	private int pa;

	@Override
	public void newGame() {
		this.random = new Random();
		gu = choki = pa = 0;
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if (currentMatchStatus.round <= currentMatchStatus.maxRound * 0.33) {
			if (currentMatchStatus.isFirstRound()) {
				return JankenHand.CHOKI;
			}

			return currentMatchStatus.previousOpponentHand.handToWin();

		} else if (currentMatchStatus.round <= currentMatchStatus.maxRound * 0.67) {
			int value = random.nextInt(100);
			if (value < 70) {
				return JankenHand.CHOKI;
			} else {
				return JankenHand.PA;
			}

		} else {
			if (currentMatchStatus.ownScore > currentMatchStatus.opponentScore) {
				if (currentMatchStatus.round <= currentMatchStatus.maxRound * 0.735) {
					return JankenHand.CHOKI;
				} else {
					int value = random.nextInt(100);
					if (value < 70) {
						return JankenHand.CHOKI;
					} else {
						return JankenHand.PA;
					}
				}

			} else {
					return currentMatchStatus.previousOpponentHand.handToWin();
			}
		}
	}

}
