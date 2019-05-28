package carrot.player.sakura;

import java.util.Random;
import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class proto2Player implements JankenPlayer {
	
	/*　ランダムに次の手を決めるプレイヤー(ポイントのためにパーとチョキしか出さないあさましさ)*/
	
	@Override
	public void newGame() {
		
	}
	
	Random rand;

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		rand = new Random();
			int acaso = rand.nextInt(2);
			
		switch(acaso) {
		case 0:
		return JankenHand.PA;
		case 1:
		return JankenHand.CHOKI;
		}
		return null;
	}

}
