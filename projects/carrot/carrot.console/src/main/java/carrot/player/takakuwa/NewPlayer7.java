package carrot.player.takakuwa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class NewPlayer7 implements JankenPlayer {
	
	//変数定義
	private int pre_hand;
	private int rand;
	private int hand;
	
	//手ごとのリスト
	private List<Integer> handListGu = new ArrayList<Integer>();
	private List<Integer> handListChoki = new ArrayList<Integer>();
	private List<Integer> handListPa = new ArrayList<Integer>();
	
	
	//初期化
	@Override
	public void newGame() {
		handListGu.clear();
		handListChoki.clear();
		handListPa.clear();
	}
	
	//リスト作成
	public void addHand(int hand) {
		if(pre_hand == 0) handListGu.add(hand);
		else if(pre_hand == 1) handListChoki.add(hand);
		else handListPa.add(hand);

	}
	
	//メイン
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		if (!(currentGameStatus.isFirstRound()) && currentGameStatus.round <= 101){
			//100戦目まで学習
			switch(currentGameStatus.previousOpponentHand.toString()) {
			case "GU":
				addHand(0);
				break;
			case "CHOKI":
				addHand(1);
				break;
			default:
				addHand(2);
			}
		}
		if(currentGameStatus.round <= 100) {
			//100戦目まではぐーちょきぱー
			pre_hand = (currentGameStatus.round - 1) % 3;
			switch((currentGameStatus.round - 1) % 3) {
			case 0:
				return JankenHand.GU;
			case 1:
				return JankenHand.CHOKI;
			default:
				return JankenHand.PA;
			}
		}
		else {

			//100戦目以降学習をもとに戦う
			if(pre_hand == 0) {
				//リスト数内でランダムな数値を生成
				Random r = new Random();				
				rand = r.nextInt(handListGu.size());
	
			    // ランダムなリストを取得。
			    hand = handListGu.get(rand);
			}
			else if(pre_hand == 1) {
				//リスト数内でランダムな数値を生成
				Random r = new Random();				
				rand = r.nextInt(handListChoki.size());
	
			    // ランダムなリストを取得。
			    hand = handListChoki.get(rand);				
			}
			else {
				//リスト数内でランダムな数値を生成
				Random r = new Random();				
				rand = r.nextInt(handListPa.size());
	
			    // ランダムなリストを取得。
			    hand = handListPa.get(rand);			
			}
			
			//自分の前の手を更新
			pre_hand = hand;
			
			//リストをもとにランダムにじゃんけんor出した順に
			//switch(handList.get((currentGameStatus.round - 1) % 100)){
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
}
