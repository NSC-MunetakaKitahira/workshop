package carrot.game.player.entries.takakuwa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

public class TakakuwaPlayer3 implements JankenPlayer {
	
	//変数定義
	private int gu;
	private int choki;
	private int pa;
	private int rand;
	private int hand;
	private List<Integer> handList = new ArrayList<Integer>();
	
	//学習データをもとにリスト作成
	public void makeHandList() {
		while(gu > 0) {
			handList.add(2);
			gu--;
		}
		while(choki > 0) {
			handList.add(0);
			choki--;
		}
		while(pa > 0) {
			handList.add(1);
			pa--;
		}
		
	}
	
	//初期化
	@Override
	public void newGame() {
		gu = 0;
		choki = 0;
		pa = 0;
		handList.clear();
	}
	
	//メイン
	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {
		if (!(currentGameStatus.isFirstRound()) && currentGameStatus.round <= 101){
			//100戦目まで学習
			switch(currentGameStatus.previousOpponentHand.toString()) {
			case "GU":
				gu ++;
				break;
			case "CHOKI":
				choki ++;
				break;
			default:
				pa ++;
			}
			//101戦目で学習データから手を作成
			if(currentGameStatus.round == 101) {
				this.makeHandList();
			}
		}
		if(currentGameStatus.round <= 100) {
			//100戦目まではぐーちょきぱー
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
			//リスト数内でランダムな数値を生成
			Random r = new Random();
			rand = r.nextInt(handList.size());

		    // ランダムなリストを取得。
		    hand = handList.get(rand);

			
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
