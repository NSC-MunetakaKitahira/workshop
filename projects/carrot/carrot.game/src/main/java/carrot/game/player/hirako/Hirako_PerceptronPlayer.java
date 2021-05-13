package carrot.game.player.hirako;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Hirako_PerceptronPlayer implements JankenPlayer {

	final boolean DEBUG_FLAG_MASTER 		= false;

	final boolean DEBUG_FLAG_FEEDBACK 	= true;
	final boolean DEBUG_FLAG_LOSS 		= true;
	final boolean DEBUG_FLAG_WEIGHT 		= true;
	final boolean DEBUG_FLAG_UNIT 		= true;
	final boolean DEBUG_FLAG_OUTPUT 		= true;
	final boolean DEBUG_FLAG_LASTHAND 	= true;

	final boolean LOSS_FLAG  = true;

	int[] history = new int[301];

	final double LEARNING_RATE = 0.0002;				//学習率

	double[][]  input  = new double[6][3];		//入力値　過去6回分＊グーチョキパー3種を0or1で取る
	double[][][]weight = new double[6][3][3];		//重みベクトル
	double[]    bias   = new double[3];			//バイアス（閾値）
	double[]    unit   = new double[3];			//ユニット値
	double[]    output = new double[3];			//出力値　これをsoftmaxで多クラス分類する
	double      loss;								//損失関数
	double[]    right  = new double[3];			//正解

	int tgt_index_output = 0;						//どのじゃんけんの手を出すか


	public void newGame() {
		int[] history = new int[301];

		history = null;

		input  = new double[6][3];
		weight = new double[6][3][3];
		bias   = new double[3];
		unit   = new double[3];
		output = new double[3];
		loss   = 0;
		right  = new double[3];

		tgt_index_output = 0;
	}



	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentGameStatus) {
		

		//全メソッド共通
		//GU=0, CHOKI=1, PA=2, 異常=-1;


		if(currentGameStatus.round==1) {

			//初期化.重み行列
			for(int i=0;i<weight.length;i++) {
				for(int j=0;j<weight[i].length;j++) {
					for(int k=0;k<weight[i][j].length;k++) {
						weight[i][j][k]=0.1;
					}
				}
			}
			for(int i=0;i<bias.length;i++) {
				bias[i]=0.5;
			}
		}



		if(currentGameStatus.round!=1) {
			//相手の手を記録
			//加えて、パーセプトロン用に正解の値も確保する。
			if(currentGameStatus.previousOpponentHand==JankenHand.GU) {
				history[currentGameStatus.round-1]=0;
				right[0]= 1;	right[1]= 0;	right[2]= 0;
			}else if(currentGameStatus.previousOpponentHand==JankenHand.CHOKI) {
				history[currentGameStatus.round-1]=1;
				right[0]= 0;	right[1]= 1;	right[2]= 0;
			}else if(currentGameStatus.previousOpponentHand==JankenHand.PA) {
				history[currentGameStatus.round-1]=2;
				right[0]= 0;	right[1]= 0;	right[2]=1;
			}else {
				System.out.printf("###HistoryWriteError\n");
				history[currentGameStatus.round-1]=-1;
				System.out.printf("###RightDataWriteError\n");
				right[0]= 0;	right[1]= 0;	right[2]= 0;
			}
		}



		if(currentGameStatus.round>=8) {
			//7戦目で初めてﾊﾟｰｾﾌﾟﾄﾛﾝ→8戦目で7戦目のデータを元に重み行列の修正を図る
			//いわゆる学習
			//逐次データ（っていか現在から６戦前までという条件）なのでオンライン学習になる（バッジ学習できない）

			if(DEBUG_FLAG_FEEDBACK && DEBUG_FLAG_MASTER) {
				System.out.printf("DEBUG round[%3d] ", currentGameStatus.round - 1);

				switch(tgt_index_output) {
				case 0:
					System.out.printf("predict=( G U ),");
					break;
				case 1:
					System.out.printf("predict=(CHOKI),");
					break;
				case 2:
					System.out.printf("predict=( P A ),");
					break;
				default:
					System.out.printf("predict=#ERROR#,");
					break;
				}

				switch(currentGameStatus.previousOpponentHand) {
				case GU:
					System.out.printf("real=( G U ),");
					if(tgt_index_output == 0) {System.out.printf("OK!");}
					else {System.out.printf("NG");}
					break;
				case CHOKI:
					System.out.printf("real=(CHOKI),");
					if(tgt_index_output == 1) {System.out.printf("OK!");}
					else {System.out.printf("NG");}
					break;
				case PA:
					System.out.printf("real=( P A ),");
					if(tgt_index_output == 2) {System.out.printf("OK!");}
					else {System.out.printf("NG");}
					break;
				default:
					System.out.printf("real=#ERROR#,");
					break;
				}
				System.out.printf("\n");
			}

			//まず損失関数を計算する（もし作れたらフラグで切り替える。）

			loss = 0;
			for(int i=0;i<right.length;i++) {
				loss += -(right[i] * Math.log(output[i]));
			}

			if(DEBUG_FLAG_LOSS && DEBUG_FLAG_MASTER) {
				System.out.printf("DEBUG   loss   =%f \n",loss);
			}

			for(int i=0;i<unit.length;i++) {
				for(int j=0;j<input.length;j++) {
					for(int k=0;k<input[j].length;k++) {
						//重み行列更新用の式は数学的複雑なので、3分割する。
						double temp_A,temp_B,temp_C;

						temp_C = input[j][k];

						temp_B = unit[i] * (1 - unit[i]);

						temp_A = right[i] / output[i];


						//二度と手計算で偏微分＋重み更新式なんてしねえ、Python使わせろ。

						if(DEBUG_FLAG_WEIGHT && DEBUG_FLAG_MASTER) {
							System.out.printf("DEBUG weight[%d][%d][%d] <- weight - %f * (%f * %f * %f)",j,k,i,LEARNING_RATE,temp_A,temp_B,temp_C);
							System.out.printf("|| before(%f)->",weight[j][k][i]);
							weight[j][k][i] = weight[j][k][i] - (LEARNING_RATE * temp_A * temp_B * temp_C);
							System.out.printf("after(%f)\n",weight[j][k][i]);
						}else {
							weight[j][k][i] = weight[j][k][i] - (LEARNING_RATE * temp_A * temp_B * temp_C);
						}


					}
				}
			}


		}



		if(currentGameStatus.round<=6) {
			//データを溜めるので1~6戦目まではナッシュ均衡プレイヤーと同じ振る舞いをする

			Random r = new Random();
			int num = r.nextInt(11);
			if       (0<=num && num<=3) {
				return JankenHand.GU;
			}else if(4<=num && num<=8) {
				return JankenHand.CHOKI;
			}else if(9<=num && num<=10) {
				return JankenHand.PA;
			}else {
				System.out.printf("\n###RandIntError %d\n",num);
				return JankenHand.PA;
			}

		}else {
			//7戦目以降は溜めた履歴を元に敵の手を単純パーセプトロンで予想する。

			//初期化.ユニット値
			for(int i=0;i<unit.length;i++) {
				unit[i]=0;
			}

			//まずinputに過去6戦分のデータを入れる
			for(int i=0;i<6;i++) {
				if(history[currentGameStatus.round-1-i]==0) {
					input[i][0]=1;
					input[i][1]=0;
					input[i][2]=0;
				}else if(history[currentGameStatus.round-1-i]==1) {
					input[i][0]=0;
					input[i][1]=1;
					input[i][2]=0;
				}else if(history[currentGameStatus.round-1-i]==2) {
					input[i][0]=0;
					input[i][1]=0;
					input[i][2]=1;
				}else {
					input[i][0]=0;
					input[i][1]=0;
					input[i][2]=0;
					System.out.printf("###ParceptronInputError \n");
				}
			}

			//inputとweightからunit値を求める
			for(int i=0;i<unit.length;i++) {

				unit[i] += bias[i];//偏りを追加

				for(int j=0;j<input.length;j++) {
					for(int k=0;k<input[j].length;k++) {
						unit[i] += input[j][k] * weight[j][k][i];//配列インデックスの順番がj,k,iなので注意
					}
				}
			}

			if(DEBUG_FLAG_UNIT && DEBUG_FLAG_MASTER) {
				System.out.printf("DEBUG   unit[0]=%f,   unit[1]=%f,   unit[2]=%f\n", unit[0],unit[1],unit[2]);
			}

			//unitから活性化関数(ソフトマックス関数)を用いて出力値を求める。
			double sum_unit_exp = 0;
			sum_unit_exp = Math.exp(unit[0]) + Math.exp(unit[1]) + Math.exp(unit[2]);
			for(int i=0;i<output.length;i++) {
				output[i] = Math.exp(unit[i]) / sum_unit_exp;
			}

			if(DEBUG_FLAG_OUTPUT && DEBUG_FLAG_MASTER) {
				System.out.printf("DEBUG output[0]=%f, output[1]=%f, output[2]=%f\n", output[0],output[1],output[2]);
			}

			//出力値の最高を元に、出す手を決定する。
			tgt_index_output = 0;
			for(int i=0;i<output.length;i++) {
				if(output[tgt_index_output]<output[i]) {
					tgt_index_output = i;
				}
			}
			//注意、敵の出す手を予想している
			switch(tgt_index_output) {
			case 0:
				//敵はグー→パー勝ち
				if(DEBUG_FLAG_LASTHAND && DEBUG_FLAG_MASTER) {System.out.printf("DEBUG max_index=0->precict ENEMY hand =( G U ) -> so winner My hand =( P A )\n");}
				return JankenHand.PA;
			case 1:
				//敵はチョキ→グー勝ち
				if(DEBUG_FLAG_LASTHAND && DEBUG_FLAG_MASTER) {System.out.printf("DEBUG max_index=1->precict ENEMY hand =(CHOKI) -> so winner My hand =( G U )\n");}
				return JankenHand.GU;
			case 2:
				//敵はパー→チョキ勝ち
				if(DEBUG_FLAG_LASTHAND && DEBUG_FLAG_MASTER) {System.out.printf("DEBUG max_index=2->precict ENEMY hand =( P A ) -> so winner My hand =(CHOKI)\n");}
				return JankenHand.CHOKI;
			default:
				//不正な場合にクラッシュすると負けなのでパーで
				//初心者はグーを出しやすいらしい
				System.out.printf("###OutputDeterminingError");
				return JankenHand.PA;
			}
		}
	}
}