package carrot.game.player.kitahira;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

import static carrot.game.judge.JankenHand.*;
import static carrot.game.judge.JankenHand.Judgement.*;
import carrot.game.judge.JankenHand.Judgement;

public class KitahiraExpectedValue implements JankenPlayer {

	private Records records;
	private JankenHand previousHand;
	
	@Override
	public void newGame() {
		records = new Records();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentGameStatus) {
		
		if (currentGameStatus.isFirstRound()) {
			return previousHand = JankenHand.CHOKI;
		}
		
		records.add(previousHand, currentGameStatus.previousOpponentHand);
		
		return previousHand = records.optimumHand(currentGameStatus);
	}
	
	static class Records {
		
		private Random random = new Random();
		private boolean isYabaiMode = false;
		
		final List<Record> records = new ArrayList<>(Arrays.asList(
				new Record(GU, WIN),
				new Record(CHOKI, WIN),
				new Record(PA, WIN)));
		
		void add(JankenHand myHand, JankenHand opponentHand) {
			records.add(new Record(
					myHand,
					myHand.competeAgainst(opponentHand)));
		}
		
		double expectedValueOf(JankenHand hand) {

			double win = probability(hand, WIN) * hand.winnerGain;
			double draw = probability(hand, DRAW) * 0.6;
			double lose = probability(hand, LOSE) * hand.handToWin().winnerGain * -2;
			return win + draw + lose;
		}
		
		JankenHand optimumHand(SubjectiveMatchStatus status) {
			
			double rateGu, rateChoki, ratePa;
			{
				double gu = expectedValueOf(GU) * 0.5;
				double choki = expectedValueOf(CHOKI);
				double pa = expectedValueOf(PA);

				// 全て正値になるよう値をずらす
				double rev = Math.min(0, Math.min(Math.min(gu, choki), pa));
				rateGu = gu + rev;
				rateChoki = choki + rev;
				ratePa = pa + rev;
			}
			
			double sum = rateGu + rateChoki + ratePa;
			double rand = random.nextDouble() * sum;
			
			JankenHand hand = choiceHand(rateGu, ratePa, rand);
			
			int remainRounds = Math.max(1, status.maxRound - status.round);
			if (status.round > 50 && status.ownScore + (4000 / remainRounds) < status.opponentScore) {
				isYabaiMode = true;
			}
			
			if (isYabaiMode) {
				if (status.ownScore > status.opponentScore) {
					isYabaiMode = false;
				}
			}
			
			if (isYabaiMode) {
				System.out.print("  yaba  ");
				return random.nextBoolean() ? hand : hand.handToLose();
			}
			
			return hand;
		}

		private JankenHand choiceHand(double rateGu, double ratePa, double rand) {
			if (rand < rateGu) {
				return GU;
			}
			if (rand < ratePa) {
				return PA;
			}
			return CHOKI;
		}
		
		private double probability(JankenHand hand, Judgement judgement) {
			
			List<Record> recordsTheHand = amplifiedRecords().stream()
					.filter(r -> r.hand.equals(hand))
					.collect(toList());
			
			long count = recordsTheHand.stream()
					.filter(r -> r.judgement.equals(judgement))
					.count();
			
			return (double) count / (double) recordsTheHand.size();
		}
		
		/**
		 * 最近の実績を増幅して返す（新しい実績ほど重みが強いとみなす）
		 * @return
		 */
		private List<Record> amplifiedRecords() {
			
			List<Record> results = new ArrayList<>();
			
			for (int i = records.size() - 1; i >= 0; i--) {
				int count = records.size() - i;
				Record record = records.get(i);
				
				if (count < 10) {
					results.add(record);
					results.add(record);
					results.add(record);
					results.add(record);
				}
				
				if (count < 30) {
					results.add(record);
					results.add(record);
					results.add(record);
				}
				
				results.add(record);
			}
			
			return results;
		}
	}

	/**
	 * 1ラウンドごとの結果
	 */
	static class Record {
		final JankenHand hand;
		final Judgement judgement;
		
		public Record(JankenHand hand, Judgement judgement) {
			this.hand = hand;
			this.judgement = judgement;
		}
	}
	
}
