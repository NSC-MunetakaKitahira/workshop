package carrot.game.player.sample;

import java.util.Map;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Tajimileaning implements JankenPlayer {

	private String memory1 = "";
	private String memory2 = "";
	private String memory3 = "";
	private int countGu = 0;

	private Map<String, Integer> counter;
	
	@Override
	public void newGame() {
		counter.clear();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		if (currentMatchStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}

		// 手の記憶
		String oneTimeMemory = handToIdentify(currentMatchStatus);
		// 72行目がtrueでuntilThree, 
		//               falseならtoDropFourだけどmemoryとreferenceDataの結果って、
		//mostHandとreferenceの中でしか使わないから、現状、untilThreeいらないはず。
		//		untilThree(currentMatchStatus, oneTimeMemory);
		String referenceData = recordLastThreeHand(oneTimeMemory);
		if (currentMatchStatus.round <= 4) {
			// データ参照・カウント
			return JankenHand.CHOKI;
		}
		else {
			// 4手目以降
			reference(referenceData);
			// データ参照・カウント
			return decisionNextHand();
		}
	}

	/**
	 * 4ラウンド以降に、最終的に何の手を出すか決める
	 */
	private JankenHand decisionNextHand() {
		JankenHand next = mostHand();
		// 相手が次に出す手の予想
		switch (next) {
			// 予想に対して勝利するように手を出す。
			case GU:
			case PA:
				return next.handToWin();
			case CHOKI:
				if(countGu <= 150) {
					countGu++;
					return next.handToWin();
				}else {
					return JankenHand.CHOKI;	
				}
			default:
				throw new RuntimeException("新概念の手を出そうとしてます。:"+next);
		}
	}

	// 今相手が出した手を記憶し、変数に入れるメソッド
	public String handToIdentify(SubjectiveMatchStatus currentMatchStatus) {
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


	// 4手目以降
	public String recordLastThreeHand(String oneTimeMemory) {
		memory1 = memory2;
		memory2 = memory3;
		memory3 = oneTimeMemory;
		return memory1 + memory2 + memory3;
	}

	// データ参照・カウント
	public void reference(String referenceData) {
		if(counter.containsKey(referenceData)) {
			int count = counter.get(referenceData);
			counter.replace(referenceData, count++);
			return;
		}
		counter.put(referenceData, 0);
	}

	// 直近2手を収集、referenceと参照,3択の内、一番多い手を選出
	public JankenHand mostHand() {
		String twoHand = memory2 + memory3;
		
		//全switchに共通して落ちてた、最後の手が全て0回だった場合パーを返す。
		if(counter.get(twoHand + "0") == 0
	       && counter.get(twoHand + "1") == 0
	       && counter.get(twoHand + "2") == 0) {
			return JankenHand.PA;
		}
		
		if(  counter.get(twoHand + "0") >= counter.get(twoHand + "1")
	    && counter.get(twoHand + "0") >= counter.get(twoHand + "2")) {
				return JankenHand.GU;
		}
		else if(counter.get(twoHand + "1") >= counter.get(twoHand + "0")
		&&   counter.get(twoHand + "1") >= counter.get(twoHand + "2")) {
			return JankenHand.CHOKI;
		}
		else if(    counter.get(twoHand + "2") >= counter.get(twoHand + "0")
				&&  counter.get(twoHand + "2") >= counter.get(twoHand + "1")) {
			return JankenHand.PA;
		}
		throw new RuntimeException("ありえない手の組み合わせでした。:"+twoHand);
	}
}