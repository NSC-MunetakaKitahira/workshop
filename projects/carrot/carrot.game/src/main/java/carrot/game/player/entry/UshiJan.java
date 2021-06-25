package carrot.game.player.entry;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;
import java.util.Random;

public class UshiJan implements JankenPlayer {

	private Random random;

	//前回の自分のスコアを入れる箱
	private int myScore = 0 ;

	//戦略を決める変数
	private int tact = 0 ;

	//相手の手をカウントする箱
	private int gc;
	private int cc;
	private int pc;

	//偏りを見る変数
	private int hv;

	//点差を見る
	private int outcome;

	//点差を保存
	private int record;

	//負け回数
	private double loseCount;

	//負け率
	private double LPR;

	//負け率計算のラウンド数
	private int LPR_R=110;

	//勝った回数
	private int winCount;
	//ボーナスタイムの必要勝利数
	private int bonusTime=80;
	//ボーナスの回数
	private int bonusCount;
	//ボーナスタイム終了回数
	private int maxBonus=60;

	//ボーナスが何回起こったか
	private int startBig;
	//ボーナス何回でビッグボーナスか
	private int bigTime=5;
	//ビッグの回数
	private int bigCount;
	//BIGの終了ラウンド数
	private int maxBig=30;


	//負け率計算（変動型)の計算用ラウンド数
	private int roundCount;
	private int borderRound=20;
	//切り替える負け率
	private int borderLPR=25;


	@Override
	public void newGame() {
		this.random = new Random();
		myScore = 0;
		tact = 0;
		record= 0;
		winCount=0;
		bonusCount=0;
		roundCount=0;
		loseCount=0;
		startBig=0;

	}



	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		//相手の手をカウント
		countHand(currentMatchStatus);
		//負けた回数をカウント
		LoseCounting(currentMatchStatus);
		//勝った回数をカウント
		WinCounting(currentMatchStatus);
		//初回はランダム
		if(currentMatchStatus.isFirstRound()) {
			return ran2();
		}
		roundCount++;
		LPR=loseCount/ roundCount*100;

		//【戦略２】10Rごとに集計
		if(currentMatchStatus.round%7==0) {
			handVias();
			gc=cc=pc=0;
		}

		//任意ラウンドで戦略変更の是非を決める
//		if(currentMatchStatus.round%200==0) {
//			outcome(currentMatchStatus);
//			tactSwitch(currentMatchStatus);
//			record();
//		}


//		if(currentMatchStatus.round%LPR_R==0) {
//			//負け率計算
//			LPRcom(currentMatchStatus);
//			LPRswitch();
//		}

		//負け率25％で戦略切り替え（切り替わってから20Rは切り替えられない）
		if(roundCount>borderRound&&LPR>borderLPR) {
			tact++;
			loseCount=0;
			roundCount=0;
		}

		//【ビッグボーナス終了のお知らせ】
		if(bigCount>=maxBig) {
			bigCount=0;
			winCount=0;
			startBig=0;
		}
		//【BIGボーナス発生！！！】
		if(startBig>=bigTime) {
			bigCount++;
			return BIGBonus();
		}

		//【ボーナスタイム終了のお知らせ】
		if(bonusCount>=maxBonus) {
			bonusCount=0;
			winCount=0;
			startBig++;
		}

		//【ボーナスタイム】n勝すると違う戦略を返す
		if(winCount>=bonusTime) {
			bonusCount++;
			return currentMatchStatus.previousOpponentHand.handToWin();
		}

		//戦略を選んで返す
		switch(tact%2) {
		case 0:
			return outcomeSelect(currentMatchStatus);
		default:
			return viasRandom();
		}

	}



	//【戦略１】じゃんけんの結果によって出し方を変える
	public JankenHand outcomeSelect (SubjectiveMatchStatus currentMatchStatus) {
		switch(currentMatchStatus.ownScore - myScore) {
		case 0 :
			myScore = currentMatchStatus.ownScore;
			return ran2();
		case 1 :
			myScore = currentMatchStatus.ownScore;
			return currentMatchStatus.previousOpponentHand.handToWin();
		case 2 :
		case 3 :
			myScore = currentMatchStatus.ownScore;
			return JankenHand.CHOKI;
		default:
			myScore = currentMatchStatus.ownScore;
			return JankenHand.PA;
		}
	}


	//【戦略2】相手の偏りに合わせたランダムを出す
	public JankenHand viasRandom() {
			switch(hv) {
			case 0 :
				return ran0();
			case 1 :
				return ran1();
			case 2 :
				return ran2();
			case 3 :
				return ran2();
			default:
				return JankenHand.CHOKI;
		}
	}

	//相手が前回どの手を出したかカウント
	public void countHand(SubjectiveMatchStatus currentMatchStatus) {

		if (!currentMatchStatus.isFirstRound()) {
			switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				gc++; break;
			case CHOKI:
				cc++; break;
			default:
				pc++; break;
			}
		}
	}

	//偏りを見る
	public void handVias() {
		if (pc>=3) {
			hv=2;
		}else if (cc >=3) {
			hv=1;
		}else if (gc >=3) {
			hv=0;
		}else {
			hv=3;
		}
	}

	//ランダム
	public JankenHand ran0() {
		int value0=random.nextInt(100);
		if(value0<20) {
			return JankenHand.CHOKI;
		}else if(value0<50){
			return JankenHand.GU;
		}else {
			return JankenHand.PA;
		}
	}

	public JankenHand ran1(){
		int value1=random.nextInt(100);
		if(value1<20) {
			return JankenHand.PA;
		}else if(value1<50){
			return JankenHand.GU;
		}else {
			return JankenHand.CHOKI;
		}
	}

	public JankenHand ran2(){
		int value2=random.nextInt(100);
		if(value2<20) {
			return JankenHand.GU;
		}else if(value2<50){
			return JankenHand.PA;
		}else {
			return JankenHand.CHOKI;
		}
	}



	//【戦略の切り替えを行うメソッド】

	//勝っているか負けているか
	public boolean isWin(SubjectiveMatchStatus currentMatchStatus) {
		return currentMatchStatus.ownScore>currentMatchStatus.opponentScore;
	}

	//戦略用変数を変更する
	public void tactSwitch(SubjectiveMatchStatus currentMatchStatus) {
		if(!increase() && !isWin( currentMatchStatus)) {
			tact++;
		}
	}

	//相手との点差を計算
	public void outcome(SubjectiveMatchStatus currentMatchStatus) {
		outcome = currentMatchStatus.ownScore-currentMatchStatus.opponentScore;
	}

	//相手との点差を記録
	public void record() {
		record = outcome;
	}

	//相手との点差が好転していればtrue
	public boolean increase() {
		return outcome > record;
	}

	//負けている割合の計算
	public void LPRcom(SubjectiveMatchStatus currentMatchStatus) {
		LPR = loseCount / LPR_R * 100;
		loseCount=0;
	}

	//勝率が低いときに戦略をスイッチ
	public void LPRswitch() {
		if(LPR>30) {
			tact++;
		}
	}

	//負けた回数をカウント
	public void LoseCounting(SubjectiveMatchStatus currentMatchStatus) {
		if(currentMatchStatus.ownScore - myScore==0) {
			loseCount++;
		}
	}

	//勝った回数をカウント
	public void WinCounting(SubjectiveMatchStatus currentMatchStatus) {
		if(currentMatchStatus.ownScore - myScore>=2) {
			winCount++;
		}
	}

	public JankenHand BIGBonus(){
		int BIG=random.nextInt(100);
		if(BIG<10) {
			return JankenHand.GU;
		}else if(BIG<30){
			return JankenHand.PA;
		}else {
			return JankenHand.CHOKI;
		}
	}

	public JankenHand Bonus(){
		int Bonus=random.nextInt(100);
		if(Bonus<20) {
			return JankenHand.GU;
		}else if(Bonus<50){
			return JankenHand.CHOKI;
		}else {
			return JankenHand.PA;
		}
	}
}
