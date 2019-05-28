package carrot.player.umemura;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;


public class UmemuraChokiPaPlayer implements JankenPlayer{

	private int round = 0;
	@Override
	public void newGame() {
		// 何もしない
	}

	public JankenHand nextHand(Subjective currentGameStatus) {
		
		if(round%2 == 0) {
			round = 1;
			return JankenHand.PA;
		}else {
			round = 0;
			return JankenHand.CHOKI;
		}
	}
}
