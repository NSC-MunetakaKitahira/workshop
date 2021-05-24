package carrot.game.player.entry;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class mine implements JankenPlayer{

	private int gu;
	private int choki;
	private int pa;
	private Random random;

	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		int value=random.nextInt(3);
		int value2 = random.nextInt(2);


		if (gu<90 && pa<60 && choki<150) {
			switch (value) {
			case 0:
				gu++;
				return JankenHand.GU;
			case 1:
				choki++;
				return JankenHand.CHOKI;
			default:
				pa++;
				return JankenHand.PA;
			}
		}

		else if (gu>=90 && pa<60 && choki<150) {
			switch (value2) {
			case 0:
				pa++;
				return JankenHand.PA;
			default:
				choki++;
				return JankenHand.CHOKI;
			}
		}

		else if (gu<90 && pa>=60 && choki<150) {
			switch (value2) {
			case 0:
				gu++;
				return JankenHand.GU;
			default:
				choki++;
				return JankenHand.CHOKI;
			}
		}

		else if (gu<90 && pa<60 && choki>=150) {
			switch (value2) {
			case 0:
				gu++;
				return JankenHand.GU;
			default:
				pa++;
				return JankenHand.PA;
			}
		}

		else if (gu>=90 && pa>=60 && choki<150) {
			return JankenHand.CHOKI;
			}

		else if (gu>=90 && pa<60 && choki>=150) {
			return JankenHand.PA;
		}

		else {
			return JankenHand.GU;
		}

	}

}
