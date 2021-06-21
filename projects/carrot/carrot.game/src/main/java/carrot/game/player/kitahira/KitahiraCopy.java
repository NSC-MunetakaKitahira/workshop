package carrot.game.player.kitahira;

import static carrot.game.judge.JankenHand.*;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.reflections.Reflections;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

/**
 * 対戦相手のプレイヤーを推定し、そのプレイヤーが出す次の手に勝てる手を出すプレイヤー
 * 1. 自分を除く全JankenPlayerをアプリケーション内から検索、インスタンス化し、コピープレイヤーとして保持する
 * 2. 一手ごとに全コピープレイヤーのnextHandを呼び出して記録する
 * 3. 対戦相手の出す手を記録していき、各ラウンド時点までに出してきた手の一致率から対戦相手を推定する
 * 4. 「推定した対戦相手」のコピープレイヤーが次に出す手に勝てる手を出す
 */
public class KitahiraCopy implements JankenPlayer {
	
	/** 自身の前回の手 */
	private JankenHand myPreviousHand;
	
	/** 相手の出した手の記録 */
	private List<JankenHand> opponentHands;
	
	/** Mirror */
	private Simulator mirror;
	
	@Override
	public void newGame() {
		myPreviousHand = null;
		opponentHands = new ArrayList<>();
		mirror = new Simulator();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus status) {
		
		if (!status.isFirstRound()) {
			opponentHands.add(status.previousOpponentHand);
		}
		
		mirror.nextHand(reverse(status, myPreviousHand));
		
		return myPreviousHand = mirror.mostProbableNextHand(opponentHands).handToWin();
	}
	
	/**
	 * SubjectiveMatchStatusをコピープレイヤーに渡すために反転させる
	 * @param status
	 * @param myPreviousHand
	 * @return
	 */
	private static SubjectiveMatchStatus reverse(SubjectiveMatchStatus status, JankenHand myPreviousHand) {
		
		return new SubjectiveMatchStatus(
				status.round,
				status.maxRound,
				status.opponentScore,
				status.ownScore,
				myPreviousHand);
		
	}
	
	private static JankenHand getMost(List<JankenHand> hands) {
		
		Map<JankenHand, Integer> counters = new HashMap<>();
		counters.put(GU, 0);
		counters.put(CHOKI, 0);
		counters.put(PA, 0);
		
		hands.forEach(hand -> {
			if (hand == null) return;
			counters.compute(hand, (h, count) -> count + 1);
		});
	
		return counters.entrySet().stream()
				.max(Comparator.comparing(e -> e.getValue()))
				.get()
				.getKey();
	}

	private static class Simulator {
		
		/**
		 * key: プレイヤー名
		 * value: コピープレイヤー
		 */
		private Map<String, CopyPlayerContainer> containers = initCopies();
		
		public void nextHand(SubjectiveMatchStatus reversedStatus) {
			containers.values().forEach(p -> p.nextHand(reversedStatus));
		}
		
		/**
		 * 最も一致率の高いプレイヤーの次の手を返す
		 * @return
		 */
		public JankenHand mostProbableNextHand(List<JankenHand> opponentHands) {
			
			// 初手は人気手を探す
			if (opponentHands.isEmpty()) {
				return mostPopularHand();
			}
			
			// 最も一致率の高いコピープレイヤーの記録を探す
			CopyPlayerContainer mostProbable = containers.values().stream()
					.max(Comparator.comparing(r -> r.rateOfConcordance(opponentHands)))
					.get();
			
			return mostProbable.getMostProbableNextHand();
		}

		/**
		 * 全コピープレイヤーの人気手を返す
		 * @return
		 */
		private JankenHand mostPopularHand() {
			
			List<JankenHand> hands = containers.values().stream()
				.flatMap(c -> c.instances.stream())
				.map(i -> i.latestHand())
				.collect(toList());

			return getMost(hands);
		}
		
		private static Map<String, CopyPlayerContainer> initCopies() {
			return Players.OTHER_PLAYER_CLASSES.stream()
					.map(c -> new CopyPlayerContainer(c))
					.peek(c -> c.newGame())
					.collect(toMap(p -> p.playerName, p -> p));
		}
	}
	
	/**
	 * コピープレイヤーのインスタンスをたくさん放り込むやつ
	 */
	private static class CopyPlayerContainer {
		
		private static final int size = 4;
		
		final String playerName;
		
		final List<CopyPlayerInstance> instances;
		
		public CopyPlayerContainer(Class<? extends JankenPlayer> playerClass) {
			
			playerName = playerClass.getName();
			
			instances = IntStream.range(0, size)
					.mapToObj(n -> new CopyPlayerInstance(Players.create(playerClass)))
					.collect(toList());
		}
		
		public void newGame() {
			instances.forEach(i -> i.newGame());
		}
		
		public void nextHand(SubjectiveMatchStatus reversedStatus) {
			instances.forEach(i -> i.nextHand(reversedStatus));
		}
		
		public JankenHand getMostProbableNextHand() {
			
			return getMost(
					instances.stream().map(i -> i.latestHand()).collect(toList()));
		}
		
		public double rateOfConcordance(List<JankenHand> targets) {
			
			return instances.stream()
					.collect(Collectors.averagingDouble(i -> i.rateOfConcordance(targets)));
			
		}
	}
	
	/**
	 * コピープレイヤーのインスタンス
	 */
	private static class CopyPlayerInstance {
		
		final JankenPlayer player;
		
		final List<JankenHand> hands = new ArrayList<>();

		public CopyPlayerInstance(JankenPlayer player) {
			this.player = player;
		}
		
		public void newGame() {
			player.newGame();
		}
		
		public JankenHand latestHand() {
			return hands.get(hands.size() - 1);
		}
		
		public void nextHand(SubjectiveMatchStatus reversedStatus) {
			JankenHand nextHand = tryNextHand(player, reversedStatus);
			hands.add(nextHand);
		}
		
		/**
		 * コピープレイヤーがnextHandでクラッシュしたときに巻き込まれないように、クラッシュ時はnullを返す
		 * @param player
		 * @param status
		 * @return
		 */
		private static JankenHand tryNextHand(JankenPlayer player, SubjectiveMatchStatus status) {
			try {
				return player.nextHand(status);
			} catch (Exception ex) {
				return null;
			}
		}
		
		/**
		 * targetsとの一致数を返す
		 * @param targets
		 * @return
		 */
		public double rateOfConcordance(List<JankenHand> targets) {
			
			int matches = 0;
			for (int i = 0; i < targets.size(); i++) {
				if (hands.get(i) == targets.get(i)) {
					matches++;
				}
			}
			
			return (double) matches / targets.size();
		}
	}
	
	/**
	 * JankenPlayerのクラスやインスタンスを取扱う
	 */
	private static class Players {
		
		private static List<Class<? extends JankenPlayer>> OTHER_PLAYER_CLASSES;
		static {
			OTHER_PLAYER_CLASSES = new Reflections("carrot")
					.getSubTypesOf(JankenPlayer.class)
					.stream()
					.filter(c -> !c.equals(KitahiraCopy.class) && !c.getName().contains("Kitahira"))
					.collect(toList());
		}
		
		private static JankenPlayer create(Class<? extends JankenPlayer> playerClass) {
			try {
				return playerClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
