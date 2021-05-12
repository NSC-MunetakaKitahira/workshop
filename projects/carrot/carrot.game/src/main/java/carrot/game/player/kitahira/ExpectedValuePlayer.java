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

public class ExpectedValuePlayer implements JankenPlayer {

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
		
		return previousHand = records.optimumHand();
	}
	
	static class Records {
		
		private Random random = new Random();
		
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
			double draw = probability(hand, DRAW) * 1;
			double lose = probability(hand, LOSE) * hand.handToWin().winnerGain * -1;
			return win + draw + lose;
		}
		
		private List<Record> amplifiedRecords() {
			
			List<Record> results = new ArrayList<>();
			
			for (int i = records.size() - 1; i >= 0; i--) {
				int count = records.size() - i;
				Record record = records.get(i);
				
				if (count < 10) {
					results.add(record);
					results.add(record);
					results.add(record);
				}
				
				if (count < 30) {
					results.add(record);
					results.add(record);
				}
				
				results.add(record);
			}
			
			return results;
		}
		
		JankenHand optimumHand() {
			
			double rateGu, rateChoki, ratePa;
			{
				double gu = expectedValueOf(GU);
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
