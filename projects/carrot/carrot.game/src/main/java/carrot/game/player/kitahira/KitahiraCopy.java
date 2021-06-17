package carrot.game.player.kitahira;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;

import carrot.game.judge.JankenHand;
import static carrot.game.judge.JankenHand.*;
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
	private CopyPlayers mirror;
	
	@Override
	public void newGame() {
		myPreviousHand = null;
		opponentHands = new ArrayList<>();
		mirror = new CopyPlayers();
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

	private static class CopyPlayers {

		/**
		 * key: プレイヤー名
		 * value: コピープレイヤーのインスタンス
		 */
		private Map<String, JankenPlayer> copyPlayers = initPlayers();
		
		/**
		 * key: プレイヤー名
		 * value: コピープレイヤーが出した手の記録
		 */
		private Map<String, Record> records = initRecords();
		
		public void nextHand(SubjectiveMatchStatus reversedStatus) {
			copyPlayers.forEach((playerName, player) -> {
				JankenHand nextHand = tryNextHand(player, reversedStatus);
				records.get(playerName).add(nextHand);
			});
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
		 * 最も一致率の高いプレイヤーの次の手を返す
		 * @return
		 */
		public JankenHand mostProbableNextHand(List<JankenHand> opponentHands) {
			
			// 初手は人気手を探す
			if (opponentHands.isEmpty()) {
				return mostPopularHand();
			}
			
			// 最も一致率の高いコピープレイヤーの記録を探す
			Record mostProbable = records.values().stream()
					.max(Comparator.comparing(r -> r.countMatches(opponentHands)))
					.get();
			
			System.out.println(mostProbable.playerName);
			
			return mostProbable.latestHand();
		}

		/**
		 * 全コピープレイヤーの人気手を返す
		 * @return
		 */
		private JankenHand mostPopularHand() {
			
			Map<JankenHand, Integer> hands = new HashMap<>();
			hands.put(GU, 0);
			hands.put(CHOKI, 0);
			hands.put(PA, 0);
			
			for (Record record : records.values()) {
				hands.compute(record.latestHand(), (hand, count) -> count + 1);
			}
			
			return hands.entrySet().stream()
					.max(Comparator.comparing(e -> e.getValue()))
					.get()
					.getKey();
		}
		
		private static Map<String, JankenPlayer> initPlayers() {
			return OTHER_PLAYER_CLASSES.stream()
					.map(c -> create(c))
					.peek(p -> { try { p.newGame(); } catch (Exception ex) {} })
					.collect(toMap(p -> p.getClass().getName(), p -> p));
		}
		
		private static Map<String, Record> initRecords() {
			return OTHER_PLAYER_CLASSES.stream()
					.map(c -> c.getName())
					.collect(toMap(n -> n, n -> new Record(n)));
		}
		
		private static List<Class<? extends JankenPlayer>> OTHER_PLAYER_CLASSES;
		static {
			OTHER_PLAYER_CLASSES = new Reflections("carrot")
					.getSubTypesOf(JankenPlayer.class)
					.stream()
					.filter(c -> !c.equals(KitahiraCopy.class))
					.collect(toList());
		}
		
		private static JankenPlayer create(Class<?> playerClass) {
			try {
				return (JankenPlayer) playerClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 各コピープレイヤーの出した手の記録
	 */
	private static class Record {
		
		final String playerName;
		
		final List<JankenHand> hands = new ArrayList<>();

		public Record(String playerName) {
			this.playerName = playerName;
		}
		
		public void add(JankenHand hand) {
			hands.add(hand);
		}
		
		public JankenHand latestHand() {
			return hands.get(hands.size() - 1);
		}
		
		/**
		 * targetsとの一致数を返す
		 * @param targets
		 * @return
		 */
		public int countMatches(List<JankenHand> targets) {
			
			int matches = 0;
			for (int i = 0; i < targets.size(); i++) {
				if (hands.get(i) == targets.get(i)) {
					matches++;
				}
			}
			
			return matches;
		}
	}
}
