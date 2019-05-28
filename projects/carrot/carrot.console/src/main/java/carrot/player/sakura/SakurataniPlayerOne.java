package carrot.player.sakura;

import java.util.Random;
import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class SakurataniPlayerOne implements JankenPlayer {
	
	/*　ランダムに次の手を決めるプレイヤー*/
	
	@Override
	public void newGame() {
		
	}

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		Random rand;
		rand = new Random();
			int acaso = rand.nextInt(3);
			
		switch(acaso) {
		case 0:
		return JankenHand.PA;
		case 1:
		return JankenHand.GU;
		case 2:
	    return JankenHand.CHOKI;
		}
		return null;
	}

}
