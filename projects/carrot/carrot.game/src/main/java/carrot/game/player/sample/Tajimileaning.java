package carrot.game.player.sample;

import java.util.List;
import java.util.Map;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Tajimileaning implements JankenPlayer {

	//記録する履歴の最大件数
	private final int maxRecord = 3;
	//グーの回数
	private int countGu = 0;
	//手の記録用
	private List<String> handHistory;
	//履歴のカウント用
	private Map<String, Integer> counter;
	
	@Override
	public void newGame() {
		counter.clear();
		handHistory.clear();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		if (currentMatchStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}
		// 手の記憶
		recordEnemyHand(currentMatchStatus);
		
		if (currentMatchStatus.round <= 4) {
			return JankenHand.CHOKI;
		}
		else {
			// データ参照・カウント
			reference();
			return decisionNextHand();
		}
	}

	/**
	 * 4ラウンド以降に、最終的に何の手を出すか決める
	 */
	private JankenHand decisionNextHand() {
		JankenHand expect = expectEnemyHand();
		
		// 相手が次に出す手の予想
		switch (expect) {
			// 予想に対して勝利するように手を出す。
			case GU:
			case PA:
				return expect.handToWin();
			case CHOKI:
				if(countGu <= 150) {
					countGu++;
					return expect.handToWin();
				}
				return JankenHand.CHOKI;	
			default:
				throw new RuntimeException("新概念の手を出そうとしてます。:"+expect);
		}
	}

	// 今相手が出した手を記憶し、変数に入れるメソッド
	public void recordEnemyHand(SubjectiveMatchStatus currentMatchStatus) {
		if(handHistory.size() == maxRecord) {
			//一番古い履歴削除
			handHistory.remove(0);
		}
		handHistory.add(getIdentify(currentMatchStatus));
	}

	// データ参照・カウント
	public void reference() {
		String referenceData = String.join("", handHistory);
		if(counter.containsKey(referenceData)) {
			int count = counter.get(referenceData);
			counter.replace(referenceData, count++);
			return;
		}
		counter.put(referenceData, 0);
	}

	// 直近2手を収集、referenceと参照,3択の内、一番多い手を選出
	public JankenHand expectEnemyHand() {
		String latestTwoHand = handHistory.get(1) + handHistory.get(2);
		
		//全switchに共通して落ちてた、最後の手が全て0回だった場合パーを返す。
		if(counter.get(latestTwoHand + "g") == 0
	       && counter.get(latestTwoHand + "c") == 0
	       && counter.get(latestTwoHand + "p") == 0) {
			return JankenHand.PA;
		}
		
		if(moreThanCount(latestTwoHand, "g", "c")
	    && moreThanCount(latestTwoHand, "g", "p")) {
				return JankenHand.GU;
		}
		else if(moreThanCount(latestTwoHand, "c", "g")
		&&  moreThanCount(latestTwoHand, "c", "p")) {
			return JankenHand.CHOKI;
		}
		else if(moreThanCount(latestTwoHand, "p", "g")
		&&  moreThanCount(latestTwoHand, "p", "c")) {
			return JankenHand.PA;
		}
		throw new RuntimeException("ありえない手の組み合わせでした。:"+latestTwoHand);
	}
	
	private boolean moreThanCount(String historyTwoHand, String targetHand, String otherHand) {
		return counter.get(historyTwoHand + targetHand) >= counter.get(historyTwoHand + otherHand); 
	}
	
	/**
	 * 数値return に変えて、 JankenHandクラスに持っていきたい。
	 * @param currentMatchStatus
	 * @return
	 */
	private String getIdentify(SubjectiveMatchStatus currentMatchStatus) {
		switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				return "g";
			case CHOKI:
				return "c";
			case PA:
				return "p";
			default:
				throw new RuntimeException("ありえない手を相手が出しました。");
		}
	}
}