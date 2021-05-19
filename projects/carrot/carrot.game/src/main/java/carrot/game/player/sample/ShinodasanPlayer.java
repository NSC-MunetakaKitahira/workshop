package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

/**
 * ランダムに手を選ぶプレイヤー
 */
public class ShinodasanPlayer implements JankenPlayer {

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
		if(currentMatchStatus.round < 10) {
		return KatayoriRandom();}

		else {
			return NormalRandom(currentMatchStatus);
		}
		
		}



	private JankenHand KatayoriRandom() {
		int value = random.nextInt(100);
		if (value <15) {
			previousOwnHand =JankenHand.GU;
			return JankenHand.GU;
		}
		else if (value<40 && value>15) {
			previousOwnHand =JankenHand.PA;
			return JankenHand.PA;
		}
		else {
			previousOwnHand =JankenHand.CHOKI;
			return JankenHand.CHOKI;
		}

	}

	private JankenHand NormalRandom(SubjectiveMatchStatus currentMatchStatus) {


				switch (currentMatchStatus.previousOpponentHand) {
				case GU:
					gu++; break;
				case CHOKI:
					choki++; break;
				default:
					pa++; break;
				}

				switch (previousOwnHand) {
				case GU:
					myGu++; break;
				case CHOKI:
					myChoki++; break;
				default:
					myPa++; break;
				}
if(currentMatchStatus.ownScore>currentMatchStatus.opponentScore) {
				if(gu > choki && gu > pa && myGu > myPa && myGu > myChoki) {
				return JankenHand.CHOKI;
				}
				else if(gu > choki && gu > pa && myChoki > myPa && myChoki > myGu) {
				return KatayoriRandom();
				}
				else if(gu > choki && gu > pa && myPa > myGu && myPa > myGu) {
				return JankenHand.PA;
				}
				
				else if(choki > gu && choki > pa &&  myGu > myPa && myGu > myChoki) {
					return JankenHand.GU;
					}
				else if(choki > gu && choki > pa && myChoki > myPa && myChoki > myGu) {
					return JankenHand.CHOKI;
					}
				else if(choki > gu && choki > pa && myPa > myGu && myPa > myGu) {
					return KatayoriRandom();
				}
				
				
				else if(pa > gu && pa > choki &&  myGu > myPa &&  myGu > myChoki) {
					return KatayoriRandom();
				}
				else if(pa > gu && pa > choki && myChoki > myPa && myChoki > myGu) {
					return JankenHand.CHOKI;
					}
				else if(pa > gu && pa > choki && myPa > myGu && myPa > myGu) {
					return JankenHand.PA;
				}
				else {
					return JankenHand.GU;
				}
}
else if(currentMatchStatus.ownScore<currentMatchStatus.opponentScore) {
	if(gu > choki && gu > pa && myGu > myPa && myGu > myChoki) {
		return JankenHand.PA;
		}
		else if(gu > choki && gu > pa && myChoki > myPa && myChoki > myGu) {
		return JankenHand.PA;
		}
		else if(gu > choki && gu > pa && myPa > myGu && myPa > myGu) {
		return JankenHand.CHOKI;
		}
		
		else if(choki > gu && choki > pa &&  myGu > myPa && myGu > myChoki) {
			return JankenHand.CHOKI;
			}
		else if(choki > gu && choki > pa && myChoki > myPa && myChoki > myGu) {
			return JankenHand.GU;
			}
		else if(choki > gu && choki > pa && myPa > myGu && myPa > myGu) {
			return JankenHand.GU;
		}
		
		
		else if(pa > gu && pa > choki &&  myGu > myPa &&  myGu > myChoki) {
			return JankenHand.CHOKI;
		}
		else if(pa > gu && pa > choki && myChoki > myPa && myChoki > myGu) {
			return JankenHand.CHOKI;
			}
		else if(pa > gu && pa > choki && myPa > myGu && myPa > myGu) {
			return JankenHand.GU;
		}
		else {
			return JankenHand.PA;
		}
}
else {
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

	}

	}



