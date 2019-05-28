package carrot.player.umemura;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

import java.util.Random;

public class UmemuraRandomPlayer implements JankenPlayer{
	
	private Random random = new Random();
	@Override
	public void newGame() {
		// 何もしない
	}
	
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		int res = random.nextInt(3);
		//とにかくランダムな手を返す
		if(res == 0) {
			return JankenHand.GU;
		}else if(res == 1) {
			return JankenHand.CHOKI;
		}else if(res == 2) {
			return JankenHand.PA;
		}else {
			//万が一にもどれにも当てはまらない場合が発生してしまったらグー
			return JankenHand.GU;
		}
	}

}
