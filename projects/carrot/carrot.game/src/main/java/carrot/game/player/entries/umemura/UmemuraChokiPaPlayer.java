package carrot.game.player.entries.umemura;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;


public class UmemuraChokiPaPlayer implements JankenPlayer{

	private int round = 0;
	@Override
	public void newGame() {
		// 何もしない
	}

	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {
		
		if(round%2 == 0) {
			round = 1;
			return JankenHand.PA;
		}else {
			round = 0;
			return JankenHand.CHOKI;
		}
	}
}
