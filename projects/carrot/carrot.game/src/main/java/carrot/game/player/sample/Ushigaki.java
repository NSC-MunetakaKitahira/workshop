package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;
import java.util.Random;

public class Ushigaki implements JankenPlayer {

	private int gc;
	private int cc;
	private int pc;
	private int hv;
	private Random random;

	@Override
	public void newGame() {
		gc=cc=pc=0;
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		countHand(currentMatchStatus);

		//10Rごとに集計
		
		if(currentMatchStatus.round%10==0) {
			handVias();
			gc=cc=pc=0;
		}

		return select();

	}


	//カウント
	public void countHand(SubjectiveMatchStatus currentMatchStatus) {
		
		if (!currentMatchStatus.isFirstRound()) {
			switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				gc++; break;
			case CHOKI:
				cc++; break;
			default:
				pc++; break;
			}
		}
	}

	//偏りを見る

	public void handVias() {
		if (gc>=5) {
			hv=0;
		}

		else if (cc >=5) {
			hv=1;
		}
		else if (pc >= 5) {
			hv=2;
		}
		else {
			hv=3;
		}
	}


	//出す手を選ぶ
	public JankenHand select() {
		switch(hv) {
			case 0 :
				return ran0();
			case 1 :
				return ran1();
			case 2 :
				return ran2();
			case 3 :
				return ran2();
			default:
				return JankenHand.CHOKI;
		}
	}


	//ランダム


	public JankenHand ran0() {
		int value0=random.nextInt(3);
		
		switch(value0) {
			case 0 :
				return JankenHand.GU;
			case 1 :
				return JankenHand.PA;
			default:
				return JankenHand.PA;
		}
	}

	public JankenHand ran1(){
		int value1=random.nextInt(3);
		
		switch(value1) {
			case 0 :
				return JankenHand.GU;
			case 1 :
				return JankenHand.CHOKI;
			default:
				return JankenHand.CHOKI;
		}
	}

	public JankenHand ran2(){
		int value2=random.nextInt(3);
		switch(value2) {
		case 0:
			return JankenHand.PA;
		case 1:
			return JankenHand.CHOKI;
		default:
			return JankenHand.CHOKI;
		}
	}

}
