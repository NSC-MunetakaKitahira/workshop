package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class UuuusuiPlayer implements JankenPlayer {

	private boolean feverTimeNow = false;
	private boolean superFeverTimeNow = false;
	int myGu;
	int myChi;
	int myPa;
	int nanika;
	int yourGu;
	int yourChi;
	int yourPa;

	int feverTimeMinutes;

	@Override
	public void newGame() {

	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		if (!currentMatchStatus.isFirstRound()) {
			// 相手の手をカウント
			switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				yourGu++;
				break;
			case CHOKI:
				yourChi++;
				break;
			default:
				yourPa++;
				break;
			}
		}
		// fever time中の戦略
		if (feverTimeNow) {
			feverTimeIsFifteenMinutes(currentMatchStatus);
			feverTimeMinutes++;
			myChi++;
			return JankenHand.CHOKI;
		}
		// super fever time中の戦略
		if (superFeverTimeNow) {
			feverTimeIsFifteenMinutes(currentMatchStatus);
			feverTimeMinutes++;
			myPa++;
			return JankenHand.PA;
		}
		// fever time開始の条件
		if (yourChi == myChi && myChi > 10) {
			feverTimeNow = true;
		}
		if (yourGu == myGu && myGu > 10) {
			feverTimeNow = true;
		}
		if (yourPa == myPa && myPa > 10) {
			feverTimeNow = true;
		}
		if (myGu % 60 == 0 && myGu > 0) {
			feverTimeNow = true;
		}
		if (myChi % 90 == 0 && myChi > 0) {
			superFeverTimeNow = true;
		}
		// 相手の得点が奇数の時の戦略
		if (currentMatchStatus.opponentScore % 2 == 1) {

			JankenHand[] allHand = new JankenHand[3];
			allHand[0] = JankenHand.CHOKI;
			allHand[1] = JankenHand.GU;
			allHand[2] = JankenHand.PA;
			countMyHand(currentMatchStatus);
			return allHand[currentMatchStatus.opponentScore % 3];
		}
		// 相手の得点が奇数の時の戦略
		if (currentMatchStatus.opponentScore % 2 == 0) {
			JankenHand[] allHand = new JankenHand[3];
			allHand[0] = JankenHand.GU;
			allHand[1] = JankenHand.PA;
			allHand[2] = JankenHand.CHOKI;
			countMyHandTwo(currentMatchStatus);
			return allHand[currentMatchStatus.opponentScore % 3];
		}

		return JankenHand.CHOKI;
	}

	// 相手の得点が奇数の時の戦略で自分が出した手をカウントするメソッド
	public void countMyHand(SubjectiveMatchStatus currentMatchStatus) {
		JankenHand[] allHand = new JankenHand[3];
		allHand[0] = JankenHand.CHOKI;
		allHand[1] = JankenHand.GU;
		allHand[2] = JankenHand.PA;

		if (currentMatchStatus.opponentScore % 3 == 0) {
			myChi++;
		}
		if (currentMatchStatus.opponentScore % 3 == 1) {
			myGu++;
		}
		if (currentMatchStatus.opponentScore % 3 == 2) {
			myPa++;
		}
	}

	// 相手の得点が偶数の時の戦略で自分が出した手をカウントする
	public void countMyHandTwo(SubjectiveMatchStatus currentMatchStatus) {
		JankenHand[] allHand = new JankenHand[3];
		allHand[0] = JankenHand.GU;
		allHand[1] = JankenHand.PA;
		allHand[2] = JankenHand.CHOKI;

		if (currentMatchStatus.opponentScore % 3 == 0) {
			myGu++;
		}
		if (currentMatchStatus.opponentScore % 3 == 1) {
			myPa++;
		}
		if (currentMatchStatus.opponentScore % 3 == 2) {
			myChi++;
		}
	}

	// fever timeが15分経過したら終了させ、カウントをリセット
	public void feverTimeIsFifteenMinutes(SubjectiveMatchStatus currentMatchStatus) {
		if (feverTimeMinutes == 14) {
			feverTimeNow = false;
			superFeverTimeNow = false;
			feverTimeMinutes = -1;

		}

	}

}
