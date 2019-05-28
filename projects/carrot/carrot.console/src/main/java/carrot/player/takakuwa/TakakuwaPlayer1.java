package carrot.player.takakuwa;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TakakuwaPlayer1 implements JankenPlayer {
	
	//変数定義
	private List<Integer> handList = new ArrayList<Integer>();
	private int rand;
	private int hand;
	
	@Override
	public void newGame() {		
		handList.clear();
		handList.add(0);
		handList.add(1);
		handList.add(2);
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		//２回目以降なら前の相手に勝つ手を追加
		if (!(currentGameStatus.isFirstRound())) {
			switch(currentGameStatus.previousOpponentHand.handToWin().toString()){
			case "GU":
				handList.add(0);
				break;
			case "CHOKI":
				handList.add(1);
				break;
			default:
				handList.add(2);
			}
		}
		
		//ランダムな数値を生成
		Random r = new Random();
		rand = r.nextInt(handList.size());

	    // リストから取得します。
	    hand = handList.get(rand);

		
		//ランダムにじゃんけん
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
