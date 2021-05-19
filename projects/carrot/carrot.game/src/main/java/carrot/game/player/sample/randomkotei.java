package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class randomkotei implements JankenPlayer {

	private Random random;

	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if (currentMatchStatus.round>=1 && currentMatchStatus.round<=5) {
			return randomHand();
		}

		if (currentMatchStatus.round>=6 && currentMatchStatus.round<=10) {
			return Choki();
		}

		if (currentMatchStatus.round>=11 && currentMatchStatus.round<=15) {
			return randomHand();
		}

		if (currentMatchStatus.round>=16 && currentMatchStatus.round<=20) {
			return Choki();
		}

		if (currentMatchStatus.round>=21 && currentMatchStatus.round<=25) {
			return randomHand();
		}

		if (currentMatchStatus.round>=26 && currentMatchStatus.round<=30) {
			return Choki();
		}

		if (currentMatchStatus.round>=31 && currentMatchStatus.round<=35) {
			return randomHand();
		}

		if (currentMatchStatus.round>=36 && currentMatchStatus.round<=40) {
			return Choki();
		}

		if (currentMatchStatus.round>=41 && currentMatchStatus.round<=45) {
			return randomHand();
		}

		if (currentMatchStatus.round>=46 && currentMatchStatus.round<=50) {
			return Choki();
		}

		if (currentMatchStatus.round>=51 && currentMatchStatus.round<=55) {
			return randomHand();
		}

		if (currentMatchStatus.round>=56 && currentMatchStatus.round<=60) {
			return Choki();
		}

		if (currentMatchStatus.round>=61 && currentMatchStatus.round<=65) {
			return randomHand();
		}

		if (currentMatchStatus.round>=66 && currentMatchStatus.round<=70) {
			return Choki();
		}

		if (currentMatchStatus.round>=71 && currentMatchStatus.round<=75) {
			return randomHand();
		}

		if (currentMatchStatus.round>=76 && currentMatchStatus.round<=80) {
			return Choki();
		}

		if (currentMatchStatus.round>=81 && currentMatchStatus.round<=85) {
			return randomHand();
		}

		if (currentMatchStatus.round>=86 && currentMatchStatus.round<=90) {
			return Choki();
		}

		if (currentMatchStatus.round>=91 && currentMatchStatus.round<=95) {
			return randomHand();
		}

		if (currentMatchStatus.round>=96 && currentMatchStatus.round<=100) {
			return Choki();
		}

		if (currentMatchStatus.round>=101 && currentMatchStatus.round<=105) {
			return randomHand();
		}

		if (currentMatchStatus.round>=106 && currentMatchStatus.round<=110) {
			return Choki();
		}

		if (currentMatchStatus.round>=111 && currentMatchStatus.round<=115) {
			return randomHand();
		}

		if (currentMatchStatus.round>=116 && currentMatchStatus.round<=120) {
			return Choki();
		}


		if (currentMatchStatus.round>=121 && currentMatchStatus.round<=125) {
			return randomHand();
		}

		if (currentMatchStatus.round>=126 && currentMatchStatus.round<=130) {
			return Choki();
		}

		if (currentMatchStatus.round>=131 && currentMatchStatus.round<=135) {
			return randomHand();
		}

		if (currentMatchStatus.round>=136 && currentMatchStatus.round<=140) {
			return Choki();
		}

		if (currentMatchStatus.round>=141 && currentMatchStatus.round<=145) {
			return randomHand();
		}

		if (currentMatchStatus.round>=146 && currentMatchStatus.round<=150) {
			return Choki();
		}

		if (currentMatchStatus.round>=151 && currentMatchStatus.round<=155) {
			return randomHand();
		}

		if (currentMatchStatus.round>=156 && currentMatchStatus.round<=160) {
			return Choki();
		}

		if (currentMatchStatus.round>=161 && currentMatchStatus.round<=165) {
			return randomHand();
		}

		if (currentMatchStatus.round>=166 && currentMatchStatus.round<=170) {
			return Choki();
		}

		if (currentMatchStatus.round>=171 && currentMatchStatus.round<=175) {
			return randomHand();
		}

		if (currentMatchStatus.round>=176 && currentMatchStatus.round<=180) {
			return Choki();
		}

		if (currentMatchStatus.round>=181 && currentMatchStatus.round<=185) {
			return randomHand();
		}

		if (currentMatchStatus.round>=186 && currentMatchStatus.round<=190) {
			return Choki();
		}

		if (currentMatchStatus.round>=191 && currentMatchStatus.round<=195) {
			return randomHand();
		}

		if (currentMatchStatus.round>=196 && currentMatchStatus.round<=200) {
			return Choki();
		}

		if (currentMatchStatus.round>=201 && currentMatchStatus.round<=205) {
			return randomHand();
		}

		if (currentMatchStatus.round>=206 && currentMatchStatus.round<=210) {
			return Choki();
		}

		if (currentMatchStatus.round>=211 && currentMatchStatus.round<=215) {
			return randomHand();
		}

		if (currentMatchStatus.round>=216 && currentMatchStatus.round<=220) {
			return Choki();
		}

		if (currentMatchStatus.round>=221 && currentMatchStatus.round<=225) {
			return randomHand();
		}

		if (currentMatchStatus.round>=226 && currentMatchStatus.round<=230) {
			return Choki();
		}

		if (currentMatchStatus.round>=231 && currentMatchStatus.round<=235) {
			return randomHand();
		}

		if (currentMatchStatus.round>=236 && currentMatchStatus.round<=240) {
			return Choki();
		}

		if (currentMatchStatus.round>=241 && currentMatchStatus.round<=245) {
			return randomHand();
		}

		if (currentMatchStatus.round>=246 && currentMatchStatus.round<=250) {
			return Choki();
		}

		if (currentMatchStatus.round>=251 && currentMatchStatus.round<=255) {
			return randomHand();
		}

		if (currentMatchStatus.round>=266 && currentMatchStatus.round<=270) {
			return Choki();
		}

		if (currentMatchStatus.round>=271 && currentMatchStatus.round<=275) {
			return randomHand();
		}

		if (currentMatchStatus.round>=276 && currentMatchStatus.round<=280) {
			return Choki();
		}

		if (currentMatchStatus.round>=281 && currentMatchStatus.round<=285) {
			return randomHand();
		}

		if (currentMatchStatus.round>=286 && currentMatchStatus.round<=290) {
			return Choki();
		}

		if (currentMatchStatus.round>=291 && currentMatchStatus.round<=295) {
			return randomHand();
		}

		else {
			return Choki();
		}



	}

	private JankenHand randomHand() {

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

	private JankenHand Choki() {
		return JankenHand.CHOKI;
	}
}
