package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

//自作プレイヤーです


public class HiranoKPlayer implements JankenPlayer{

	private Random random;

	int gamecount;
	int value;


	public int gu;
	public int choki;
	public int pa;

	@Override
	public void newGame() {
		// TODO 自動生成されたメソッド・スタブ
		gamecount = 0;
		gu = choki = pa = 0;
		this.random = new Random();

	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentGameStatus) {

		this.value  = random.nextInt(100);

		if (currentGameStatus.isFirstRound()) {
			return JankenHand.GU;
		}
//		int currentRound = currentGameStatus.round;
//		System.out.print(currentRound);
		System.out.print(gu);
		System.out.print(choki);
		System.out.print(pa);

		// TODO 自動生成されたメソッド・スタブ

		if(currentGameStatus.ownScore > currentGameStatus.opponentScore + 50) {
			gamecount++;
			return JankenHand.CHOKI;
		} else {
			countRecord(currentGameStatus);

//前半戦
			if(gamecount <= 80) {

				switch (gamecount % 4 ) {

					case 0:
						return currentGameStatus.previousOpponentHand.handToWin();
					//return JankenHand.GU;

					case 1:
						if(value <= 25) {
							return JankenHand.GU;

						} else if(value <= 80) {
							return JankenHand.CHOKI;

						} else {
							return JankenHand.PA;
						}

					case 2:
						return JankenHand.PA;

					case 3:
						return JankenHand.CHOKI;

					default:
						throw new RuntimeException("エラーです。");
				}
//中盤戦
			} else if( gamecount <= 200 ){

				if(value <= 10) {
					return JankenHand.GU;

				} else if(value <= 15) {
					return JankenHand.CHOKI;

				} else if(value <= 20) {
					return JankenHand.PA;

				} else {

					if(gu > choki && gu > pa) {
						return JankenHand.PA;

					} else if(choki > pa + gu) {
						return JankenHand.GU;
						
					} else if(choki > pa) {
						return JankenHand.CHOKI;

					 }else
						return JankenHand.CHOKI;
				}



//後半戦
			} else {

				if(value <= 70) {
					countRecord(currentGameStatus);
					return currentGameStatus.previousOpponentHand.handToWin();

				} else if(value <= 99) {

					if(gu > choki && gu > pa) {
						return JankenHand.PA;

					} else if(choki > pgu ) {
						return JankenHand.GU;

					} else
						return JankenHand.CHOKI;

				} else {
					return JankenHand.CHOKI;
				}

			}

		}

	}

	private void countRecord(SubjectiveMatchStatus currentGameStatus) {
		gamecount++;
		switch (currentGameStatus.previousOpponentHand) {
			case GU:
				gu++; break;
			case CHOKI:
				choki++; break;
			default:
				pa++; break;
		}
	}
}
