package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

/**
 * ランダムに手を選ぶプレイヤー
 */
public class ShinodaPlayer implements JankenPlayer {

	private JankenHand previousOwnHand;
	private Random random;

	private int gu;
	private int choki;
	private int pa;
	private int myGu;
	private int myChoki;
	private int myPa;

	@Override
	public void newGame() {
		this.random = new Random();
		gu = choki = pa = 0;
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if (currentMatchStatus.round == 1) {
			return chokiRandom();

		} else if (currentMatchStatus.round < 10) {
			switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				gu++;
				break;
			case CHOKI:
				choki++;
				break;
			default:
				pa++;
				break;
			}

			switch (previousOwnHand) {
			case GU:
				myGu++;
				break;
			case CHOKI:
				myChoki++;
				break;
			default:
				myPa++;
				break;
			}
			return chokiRandom();
		}

		else {
			switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				gu++;
				break;
			case CHOKI:
				choki++;
				break;
			default:
				pa++;
				break;
			}

			switch (previousOwnHand) {
			case GU:
				myGu++;
				break;
			case CHOKI:
				myChoki++;
				break;
			default:
				myPa++;
				break;
			}
			return NormalRandom(currentMatchStatus);
		}

	}

	private JankenHand chokiRandom() {

		int value = random.nextInt(100);
		if (value < 25) {
			previousOwnHand = JankenHand.GU;
			return JankenHand.GU;
		} else if (value < 35) {
			previousOwnHand = JankenHand.PA;
			return JankenHand.PA;
		} else {
			previousOwnHand = JankenHand.CHOKI;
			return JankenHand.CHOKI;
		}

	}

	private JankenHand chokiGuRandom() {

		int value = random.nextInt(100);
		if (value < 25) {
			previousOwnHand = JankenHand.PA;
			return JankenHand.PA;
		} else if (value < 35) {
			previousOwnHand = JankenHand.GU;
			return JankenHand.GU;
		} else {
			previousOwnHand = JankenHand.CHOKI;
			return JankenHand.CHOKI;
		}

	}

	private JankenHand paRandom() {

		int value = random.nextInt(100);
		if (value < 25) {
			previousOwnHand = JankenHand.GU;
			return JankenHand.GU;
		} else if (value < 35) {
			previousOwnHand = JankenHand.CHOKI;
			return JankenHand.CHOKI;
		} else {
			previousOwnHand = JankenHand.PA;
			return JankenHand.PA;
		}

	}

	private JankenHand guRandom() {

		int value = random.nextInt(100);
		if (value < 25) {
			previousOwnHand = JankenHand.PA;
			return JankenHand.PA;
		} else if (value < 35) {
			previousOwnHand = JankenHand.CHOKI;
			return JankenHand.CHOKI;
		} else {
			previousOwnHand = JankenHand.GU;
			return JankenHand.GU;
		}

	}

	private JankenHand NormalRandom(SubjectiveMatchStatus currentMatchStatus) {

		// 自分が勝っているときに呼び出す
		if (currentMatchStatus.ownScore > currentMatchStatus.opponentScore) {
			if (gu > choki && gu > pa && myGu > myPa && myGu > myChoki) {
				previousOwnHand = JankenHand.CHOKI;
				return JankenHand.CHOKI;
			} else if (gu > choki && gu > pa && myChoki > myPa && myChoki > myGu) {
				return chokiRandom();
			} else if (gu > choki && gu > pa && myPa > myGu && myPa > myGu) {
				previousOwnHand = JankenHand.PA;
				return JankenHand.PA;
			}

			else if (choki > gu && choki > pa && myGu > myPa && myGu > myChoki) {
				previousOwnHand = JankenHand.GU;
				return JankenHand.GU;
			} else if (choki > gu && choki > pa && myChoki > myPa && myChoki > myGu) {
				previousOwnHand = JankenHand.CHOKI;
				return JankenHand.CHOKI;
			} else if (choki > gu && choki > pa && myPa > myGu && myPa > myGu) {
				return chokiRandom();
			}

			else if (pa > gu && pa > choki && myGu > myPa && myGu > myChoki) {
				return chokiRandom();
			} else if (pa > gu && pa > choki && myChoki > myPa && myChoki > myGu) {
				previousOwnHand = JankenHand.CHOKI;
				return JankenHand.CHOKI;
			} else if (pa > gu && pa > choki && myPa > myGu && myPa > myGu) {
				previousOwnHand = JankenHand.PA;
				return JankenHand.PA;
			} else {
				previousOwnHand = JankenHand.GU;
				return JankenHand.GU;
			}
		}
//自分がまけているときに呼び出す
		else if (currentMatchStatus.ownScore < currentMatchStatus.opponentScore) {
			if (gu > choki && gu > pa && myGu > myPa && myGu > myChoki) {
				previousOwnHand = JankenHand.PA;
				return JankenHand.PA;
			} else if (gu > choki && gu > pa && myChoki > myPa && myChoki > myGu) {

				return paRandom();
			} else if (choki > gu && choki > pa && myGu > myPa && myGu > myChoki) {
				previousOwnHand = JankenHand.CHOKI;
				return JankenHand.CHOKI;
			} else if (choki > gu && choki > pa && myChoki > myPa && myChoki > myGu) {

				return chokiGuRandom();
			} else if (choki > gu && choki > pa && myPa > myGu && myPa > myGu) {

				return guRandom();
			}

			else if (pa > gu && pa > choki && myGu > myPa && myGu > myChoki) {
				previousOwnHand = JankenHand.CHOKI;
				return JankenHand.CHOKI;
			} else if (pa > gu && pa > choki && myPa > myChoki && myPa > myGu) {

				return chokiRandom();
			} else {

				return chokiRandom();
			}
		} else {
			int value = random.nextInt(3);
			switch (value) {
			case 0:
				previousOwnHand = JankenHand.GU;
				return JankenHand.GU;
			case 1:
				previousOwnHand = JankenHand.CHOKI;
				return JankenHand.CHOKI;
			default:
				previousOwnHand = JankenHand.PA;
				return JankenHand.PA;
			}
		}

	}

}
