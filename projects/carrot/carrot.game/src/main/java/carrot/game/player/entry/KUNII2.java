package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class KUNII2 implements JankenPlayer {

	private double gwin;
	private double cwin;
	private double pwin;
	private Random random;
	private int myscore;

	@Override
	public void newGame() {
		this.random = new Random();
		gwin=cwin=pwin=myscore=0;
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		vias(currentMatchStatus);

		if (currentMatchStatus.round%100==0) {
			gwin=cwin=pwin=0;
		}

		return select();

	}

	private void vias(SubjectiveMatchStatus currentMatchStatus) {
		if (Score(currentMatchStatus)==1) {
			myscore = currentMatchStatus.ownScore;
		}else if (Score(currentMatchStatus)==2) {
			myscore = currentMatchStatus.ownScore;
			gwin++;
		}else if (Score(currentMatchStatus)==4) {
			myscore = currentMatchStatus.ownScore;
			cwin++;
		}else if (Score(currentMatchStatus)==5) {
			myscore = currentMatchStatus.ownScore;
			pwin++;
		}else {
			myscore = currentMatchStatus.ownScore;
		}
	}

	private int Score(SubjectiveMatchStatus currentMatchStatus) {
		return currentMatchStatus.ownScore-myscore;
	}

	private JankenHand select() {
		if (gwin>cwin && gwin>pwin) {
			return randomgu();
		}else if (cwin>pwin) {
			return randomchoki();
		}else {
			return randompa();
		}
	}

	private JankenHand randomchoki() {
		int hand =random.nextInt(10);
		if (hand<5) {
			return JankenHand.CHOKI;
		}else if (hand>=5 && hand<8) {
			return JankenHand.PA;
		}else {
			return JankenHand.GU;
		}
	}

	private JankenHand randomgu() {
		int hand =random.nextInt(10);
		if (hand<5) {
			return JankenHand.GU;
		}else if (hand>=5 && hand<8) {
			return JankenHand.CHOKI;
		}else {
			return JankenHand.PA;
		}
	}

	private JankenHand randompa() {
		int hand =random.nextInt(10);
		if (hand<4) {
			return JankenHand.PA;
		}else if (hand>=4 && hand<8) {
			return JankenHand.GU;
		}else {
			return JankenHand.CHOKI;
		}
	}

	private JankenHand Random() {
		int value = random.nextInt(21);

		if (value>=0 && value<4) {
			return JankenHand.CHOKI;
		}

		else if (value>4 && value<=14) {
			return JankenHand.GU;
		}

		else {
			return JankenHand.PA;
		}
	}

//	private JankenHand KUNII(SubjectiveMatchStatus currentMatchStatus) {
//		if (Score(currentMatchStatus)==1) {
//			myscore = currentMatchStatus.ownScore;
//			return currentMatchStatus.previousOpponentHand.handToLose();
//		}else if (Score(currentMatchStatus)>=2) {
//			myscore = currentMatchStatus.ownScore;
//			return currentMatchStatus.previousOpponentHand.handToWin();
//		}else {
//			myscore = currentMatchStatus.ownScore;
//			return Random();
//		}
//	}
//
}
