package carrot.game.player.sample;

import java.util.HashMap;
import java.util.Map;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Tajimilearning_KatouRefactor implements JankenPlayer {
		
		/** 過去の相手の出した手 */
		private Map<Integer, JankenHand> oppnentHands;
		
		/** 直前3回分の相手が出した手のパターンのカウント */
		private int[][][] handPatternCount = new int[3][3][3];
		
		/** 自分がグーを出している回数　*/
		private int countGu;
		

		@Override
		public void newGame() {
			this.oppnentHands = new HashMap<>();
			this.handPatternCount = new int[][][]{
				{{0, 0 ,0 }, {0, 0 ,0 }, {0, 0 ,0 }}, 
				{{0, 0 ,0 }, {0, 0 ,0 }, {0, 0 ,0 }}, 
				{{0, 0 ,0 }, {0, 0 ,0 }, {0, 0 ,0 }}};
			this.countGu = 0;
		}

		@Override
		public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
			// 1ラウンド目
			if (currentMatchStatus.isFirstRound()) {
				return JankenHand.CHOKI;
			}
			
			// 前回の相手の出した手の記録
			
			recodeHand(currentMatchStatus);
			
			// 学習期間中はチョキ固定
			if (currentMatchStatus.round <= 4) {
				return JankenHand.CHOKI;
			}
			
			// パターンのカウント
			countPattern(currentMatchStatus.round);
			
			if(currentMatchStatus.round == 300) {
				System.out.println("ラスト！");
			}
			// 自分が出す手の決定
			return decideHand(currentMatchStatus.round);
		}
		
		/**
		 * 前回の相手の手を記録する
		 * @param currentMatchStatus
		 */
		private void recodeHand(SubjectiveMatchStatus currentMatchStatus) {
			// 前回のラウンド
			int previousRound = currentMatchStatus.round - 1;
			oppnentHands.put(previousRound, currentMatchStatus.previousOpponentHand);
		}
		
		/**
		 * 直前3回分の相手が出した手のパターンをカウントする
		 * @param round
		 */
		private void countPattern(int round) {
			if(round >= 5) {
				/** 直前3回分のパターン　*/
				int[] last3Hands = new int[3];
				for(int i = 0; i <= 2; i++) {
					last3Hands[i] = getValue(this.oppnentHands.get(round - 1 - i));
				}
				// 該当するパターンをインクリメント
				handPatternCount[last3Hands[2]][last3Hands[1]][last3Hands[0]] += 1;
			}
		}
		
		/**
		 * このラウンドで自分が出す手を決める
		 * @param round
		 * @return
		 */
		private JankenHand decideHand(int round) {
			// 前回
			JankenHand last1 = oppnentHands.get(round - 1);
			// 前々回
			JankenHand last2 = oppnentHands.get(round - 2);
			
			int guCount 	= handPatternCount[getValue(last2)][getValue(last1)][getValue(JankenHand.GU)];
			int chokiCount 	= handPatternCount[getValue(last2)][getValue(last1)][getValue(JankenHand.CHOKI)];
			int paCount 	= handPatternCount[getValue(last2)][getValue(last1)][getValue(JankenHand.PA)];
			
			
			JankenHand result;
			
		if(guCount > chokiCount && guCount > paCount) {
				// グーを出してきそうなためパーを出す
				result = JankenHand.PA;
			}
			else if(chokiCount > guCount && chokiCount > paCount) {
				// チョキを出してきそうなためグーを出す
				result = JankenHand.GU;
			}
			else if(paCount > guCount && paCount > chokiCount) {
				// パーを出してきそうなためチョキを出す
				result = JankenHand.CHOKI;
			}
			else if(guCount == chokiCount && guCount > paCount) {
				// グーかチョキを出してきそうなためグーを出す
				result = JankenHand.GU;
			}
			else if(chokiCount == paCount && chokiCount > guCount) {
				// チョキかパーを出してきそうなためチョキを出す
				result = JankenHand.CHOKI;
			}
			else if(paCount == guCount && paCount > chokiCount) {
				// パーかグーを出してきそうなためパーを出す
				result = JankenHand.PA;
			}
			else {
				// 特定できなかった場合はチョキ
				result = JankenHand.CHOKI;
			}
			
			if(result == JankenHand.GU) {
				if(countGu >= 150) {
					result = JankenHand.CHOKI;
				}
				else {
					countGu++;
				}
			}
			return result;
		}
		
		private int getValue(JankenHand hand) {
			switch(hand) {
			case GU:
				// グーの場合
				return 0;
			case CHOKI:
				// チョキの場合
				return 1;
			default:
				// パーの場合
				return 2;
			}
		}
}
