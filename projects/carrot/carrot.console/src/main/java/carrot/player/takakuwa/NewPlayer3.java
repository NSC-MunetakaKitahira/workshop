package carrot.player.takakuwa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class NewPlayer3 implements JankenPlayer {
	
	//初めに勝ち点の期待値4.5になるように手を調整
	private List<Integer> handList = new ArrayList<Integer>() {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			//グー１こ、チョキ１０こ、パー９こ
	        add(0);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(1);
	        add(2);
	        add(2);
	        add(2);
	        add(2);
	        add(2);
	        add(2);
	        add(2);
	        add(2);
	        add(2);
	    }
	};
	
	//handとrandを定義
	private int rand;
	private int hand;

	@Override
	public void newGame() {
		
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		//ランダムな０～99998の数値を生成
		Random r = new Random();
		rand = r.nextInt(handList.size());

	    // シャッフルされたリストの先頭を取得します。
	    hand = handList.get(rand);

	    //じゃんけん
		switch(hand){
		case 0:
			return JankenHand.GU;
		case 1:
			return JankenHand.CHOKI;
		default:
			return JankenHand.PA;
		}
	}
}
