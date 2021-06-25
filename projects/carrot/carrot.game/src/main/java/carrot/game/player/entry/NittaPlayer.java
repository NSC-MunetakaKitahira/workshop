package carrot.game.player.sample;



import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;
import java.util.Random;

public class NittaPlayer implements JankenPlayer {


	private int opsScore;
	private int myScore;
	private int count;
	public Random random;

	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		if (currentMatchStatus.isFirstRound()) // currentMatch～のisFirstRundから持ってくる
		{
			return JankenHand.CHOKI; // 1回目
		}
		switch (currentMatchStatus.ownScore - myScore) {
		case 0:

			if (currentMatchStatus.opponentScore - opsScore == 2) {
				opsScore = currentMatchStatus.opponentScore;
				return exceptCHOKI();
			} else if (currentMatchStatus.opponentScore - opsScore == 4) {
				opsScore = currentMatchStatus.opponentScore;
				return exceptPA();
			} else {
				opsScore = currentMatchStatus.opponentScore;
				return exceptGU();
			}
		case 1:
			if (count == 1) {
				count = 2;
				myScore = currentMatchStatus.ownScore;
				
				return JankenHand.CHOKI;

			} else if (count == 2) {
				count = 3;
				myScore = currentMatchStatus.ownScore;
			
				return JankenHand.PA;
			} else {
				count = 1;
				myScore = currentMatchStatus.ownScore;
			
				return JankenHand.GU;
			}
		case 2:
			myScore = currentMatchStatus.ownScore;
			return JankenHand.GU;

		case 4:
			myScore = currentMatchStatus.ownScore;
			return JankenHand.CHOKI;

		default:
			myScore = currentMatchStatus.ownScore;
			return JankenHand.PA;

		}

	}

	public JankenHand exceptGU() {
		int value = random.nextInt(2);
		switch (value) {
		case 0:
			return JankenHand.CHOKI;
		default:
			return JankenHand.PA;
		}
	}

	public JankenHand exceptCHOKI() {
		int value = random.nextInt(2);
		switch (value) {
		case 0:
			return JankenHand.GU;
		default:
			return JankenHand.PA;
		}
	}

	public JankenHand exceptPA() {
		int value = random.nextInt(2);
		switch (value) {
		case 0:
			return JankenHand.CHOKI;
		default:
			return JankenHand.GU;
		}
	}

}
