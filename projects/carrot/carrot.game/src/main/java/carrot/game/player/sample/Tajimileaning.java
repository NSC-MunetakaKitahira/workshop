package carrot.game.player.sample;

import java.util.List;
import java.util.Map;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Tajimileaning implements JankenPlayer {

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
		handToIdentify(currentMatchStatus);
		
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
		JankenHand enemyMostHand = getEnemyMostHand();
		
		// 相手が次に出す手の予想
		switch (enemyMostHand) {
			// 予想に対して勝利するように手を出す。
			case GU:
			case PA:
				return enemyMostHand.handToWin();
			case CHOKI:
				if(countGu <= 150) {
					countGu++;
					return enemyMostHand.handToWin();
				}else {
					return JankenHand.CHOKI;	
				}
			default:
				throw new RuntimeException("新概念の手を出そうとしてます。:"+enemyMostHand);
		}
	}

	// 今相手が出した手を記憶し、変数に入れるメソッド
	public void handToIdentify(SubjectiveMatchStatus currentMatchStatus) {
		if(handHistory.size() == 3) {
			handHistory.remove(0);
		}
		
		String identify;
		switch (currentMatchStatus.previousOpponentHand) {
			case GU:
				identify = "g";
				break;
			case CHOKI:
				identify = "c";
				break;
			case PA:
				identify = "p";
				break;
			default:
				throw new RuntimeException("ありえない手を相手が出しました。");
		}
		handHistory.add(identify);
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
	public JankenHand getEnemyMostHand() {
		String twoHand = handHistory.get(1) + handHistory.get(2);
		
		//全switchに共通して落ちてた、最後の手が全て0回だった場合パーを返す。
		if(counter.get(twoHand + "g") == 0
	       && counter.get(twoHand + "c") == 0
	       && counter.get(twoHand + "p") == 0) {
			return JankenHand.PA;
		}
		
		if(moreThanCount(twoHand, "g", "c")
	    && moreThanCount(twoHand, "g", "p")) {
				return JankenHand.GU;
		}
		else if(moreThanCount(twoHand, "c", "g")
		&&  moreThanCount(twoHand, "c", "p")) {
			return JankenHand.CHOKI;
		}
		else if(moreThanCount(twoHand, "p", "g")
		&&  moreThanCount(twoHand, "p", "c")) {
			return JankenHand.PA;
		}
		throw new RuntimeException("ありえない手の組み合わせでした。:"+twoHand);
	}
	
	
	private boolean moreThanCount(String historyTwoHand, String target, String otherHand) {
		return counter.get(historyTwoHand + target) >= counter.get(historyTwoHand + otherHand); 
	}
}