package carrot.game.player.sample;

import java.util.*;
import java.lang.Math;
import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Tajimi implements JankenPlayer {

//　手の最大記憶数の設定
	private final int maxMemory = 5;
	private final int maxSevenMemory = 7;
	private final int maxTenMemory = 10;

//	参照データ数切り替え基準敗北率の設定
	int CTP = 28;
	int CTP2 = 32;

//	許容連敗数の決定
	int okLoseingStreak = 4;

//	その他変数
	String referenceHand;
	int rememberMyScore;
	int oneTimePa;
	int oneTimeGu;
	int oneTimeChoki;
	int loseChoki;
	int losePa;
	int loseGu;
	int losingStreakCount;
	double losePerChoki;
	double losePerGu;
	double losePerPa;
	private Random random;

//	参照手数ごとのデータ作り用List
	private List<String> oneTimeMemory = new ArrayList<String>();
	private List<String> oneTimeMemory2 = new ArrayList<String>();
	private List<String> oneTimeMemory3 = new ArrayList<String>();

//	学習データ格納Map、自分の出した手記録Map
	private Map<String, Integer> dataCount = new HashMap<String, Integer>();
	private Map<String, Integer> handCount = new HashMap<String, Integer>();

	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

//		連敗していたらランダムで手を返す
		if (losingStreakCount > okLoseingStreak) {

			roundProcessing(currentMatchStatus, dataCount);
			return returnRandom();
		}

//		１stラウンド
		if (currentMatchStatus.isFirstRound()) {

			handCounter("Choki");
			return JankenHand.CHOKI;
		}

//		毎ラウンド共通処理
		roundProcessing(currentMatchStatus, dataCount);

//		以下戦術切り替え処理、回答手選出、敗北率計算処理
		if (currentMatchStatus.round <= maxMemory) {

			handCounter("Choki");
			oneTimeChoki++;
			return JankenHand.CHOKI;
		}

		if (currentMatchStatus.round <= 1500) {

			referenceProgram(currentMatchStatus, oneTimeMemory, oneTimeMemory2, oneTimeMemory3);
			return mostHand(maxMemory, oneTimeMemory);

		}

		else if (currentMatchStatus.round >= 1501 && 2600 >= currentMatchStatus.round) {

			computeLose();

			if (CTPmes(1)) {

				referenceProgram(currentMatchStatus, oneTimeMemory, oneTimeMemory2, oneTimeMemory3);
				return mostHand(maxSevenMemory, oneTimeMemory2);
			}

			else if (CTPmes(2)) {

				referenceProgram(currentMatchStatus, oneTimeMemory, oneTimeMemory2, oneTimeMemory3);
				return mostHand(maxTenMemory, oneTimeMemory3);
			}
		}
		else if (Math.abs(currentMatchStatus.ownScore - currentMatchStatus.opponentScore) < 750) {

			return JankenHand.CHOKI;

		} else {

			computeLose();

			if (CTPmes(1)) {

				referenceProgram(currentMatchStatus, oneTimeMemory, oneTimeMemory2, oneTimeMemory3);
				return mostHand(maxSevenMemory, oneTimeMemory2);
			}
			else if (CTPmes(2)) {

				referenceProgram(currentMatchStatus, oneTimeMemory, oneTimeMemory2, oneTimeMemory3);
				return mostHand(maxTenMemory, oneTimeMemory3);
			}
		}

		return mostHand(maxMemory, oneTimeMemory);
	}

//	相手の手を文字列化
	private String getHand(SubjectiveMatchStatus currentMatchStatus) {
		switch (currentMatchStatus.previousOpponentHand) {
		case GU:
			return "g";

		case CHOKI:
			return "c";

		default:
			return "p";
		}
	}

//	毎ラウンドの処理メソッド（相手の出した手記録、前回の勝敗確認、スコア記録）
	private void roundProcessing(SubjectiveMatchStatus currentMatchStatus, Map<String, Integer> dataCount) {
		recordHand(currentMatchStatus);
		recordHand2(currentMatchStatus);
		recordHand3(currentMatchStatus);
		loseCount(currentMatchStatus, dataCount);
		rememberMyScore = currentMatchStatus.ownScore;

	}

//	データ格納三種同時処理メソッド
	private void referenceProgram(SubjectiveMatchStatus currentMatchStatus, List<String> oneTime1,
			List<String> oneTime2, List<String> oneTime3) {
		referenceData(currentMatchStatus, oneTime1);
		referenceData(currentMatchStatus, oneTime2);
		referenceData(currentMatchStatus, oneTime3);
	}

//	戦術切り替え基準の敗北率判断メソッド
	private boolean CTPmes(int number) {
		if (number == 1) {
			return losePerChoki >= CTP || losePerGu >= CTP || losePerPa >= CTP;
		} else {
			return losePerChoki >= CTP2 || losePerGu >= CTP2 || losePerPa >= CTP2;
		}
	}

//	前回の相手の手を記憶するメソッド（５手）
	private void recordHand(SubjectiveMatchStatus currentMatchStatus) {
		// 5手ごとなので一番古い記録を消す。
		if (oneTimeMemory.size() == maxMemory) {
			oneTimeMemory.remove(0);
		}
		// 普通に追加
		oneTimeMemory.add(getHand(currentMatchStatus));
	}

//	前回の相手の手を記憶するメソッド（7手）
	private void recordHand2(SubjectiveMatchStatus currentMatchStatus) {
		// 7手ごとなので一番古い記録を消す。
		if (oneTimeMemory2.size() == maxSevenMemory) {
			oneTimeMemory2.remove(0);
		}
		// 普通に追加
		oneTimeMemory2.add(getHand(currentMatchStatus));
	}

//	前回の相手の手を記憶するメソッド（10手）
	private void recordHand3(SubjectiveMatchStatus currentMatchStatus) {
		// 10手ごとなので一番古い記録を消す。
		if (oneTimeMemory3.size() == maxTenMemory) {
			oneTimeMemory3.remove(0);
		}
		// 普通に追加
		oneTimeMemory3.add(getHand(currentMatchStatus));
	}

//	データを参照・格納・カウントするメソッド
	private void referenceData(SubjectiveMatchStatus currentMatchStatus, List<String> oneTimememory) {
		// 文字列連結
		String concatenationData = String.join("", oneTimememory);
		if (1 <= currentMatchStatus.round && currentMatchStatus.round <= 500) {
			count(1, concatenationData);
		} else if (501 <= currentMatchStatus.round && currentMatchStatus.round <= 1500) {
			count(2, concatenationData);
		} else {
			count(2, concatenationData);
		}

	}

//	カウントメソッド
	private void count(int weight, String concatenationData) {
		// 参照する文字列がすでに存在する場合、カウント+1，ない場合1をセット。
		if (dataCount.get(concatenationData) != null) {
			// 実際はカウント変数に現在の数値入れて、その数値を＋1して置き換える。
			int count = dataCount.get(concatenationData);
			count = weight + count;
			dataCount.replace(concatenationData, count);
		}
		// そうでない場合は新しく要素を作って、カウントを1にする
		else {
			dataCount.put(concatenationData, 1);
		}
	}

//	直近2手収集・データ参照・最多手選出するメソッド
	private JankenHand mostHand(int maxMemory, List<String> oneTimeMemory) {
		referenceHand = "";
		// 直近(maxMemory-1)手を文字列化

		for (int i = 1; i <= maxMemory - 1; i++) {
			String aiu = oneTimeMemory.get(i);
			referenceHand += aiu;
		}
		// 参照データがすべて0の場合
		if (dataCount.get(referenceHand + "g") == null && dataCount.get(referenceHand + "c") == null
				&& dataCount.get(referenceHand + "p") == null) {
			handCounter("Choki");
			oneTimeChoki++;
			return JankenHand.CHOKI;
		}
		if (dataCount.get(referenceHand + "g") == null || dataCount.get(referenceHand + "p") == null
				|| dataCount.get(referenceHand + "c") == null) {
			if (whichNull(referenceHand, "g", "p", "c")) {
				handCounter("Pa");
				oneTimePa++;
				return JankenHand.PA;
			} else if (whichNull(referenceHand, "p", "g", "c")) {
				handCounter("Choki");
				oneTimeChoki++;
				return JankenHand.CHOKI;
			} else if (whichNull(referenceHand, "c", "g", "p")) {
				handCounter("Gu");
				oneTimeGu++;
				return JankenHand.GU;
			} else if (whichNull2(referenceHand, "g", "c", "p")) {

				if (dataCount.get(referenceHand + "g") >= dataCount.get(referenceHand + "c")) {
					handCounter("Pa");
					oneTimePa++;
					return JankenHand.PA;
				} else {
					handCounter("Gu");
					oneTimeGu++;
					return JankenHand.GU;
				}
			} else if (whichNull2(referenceHand, "c", "p", "g")) {

				if (dataCount.get(referenceHand + "p") >= dataCount.get(referenceHand + "c")) {
					handCounter("Choki");
					oneTimeChoki++;
					return JankenHand.CHOKI;
				} else {
					handCounter("Gu");
					oneTimeGu++;
					return JankenHand.GU;
				}
			} else if (whichNull2(referenceHand, "p", "g", "c")) {

				if (dataCount.get(referenceHand + "p") >= dataCount.get(referenceHand + "g")) {
					handCounter("Choki");
					oneTimeChoki++;
					return JankenHand.CHOKI;
				} else {
					handCounter("Pa");
					oneTimePa++;
					return JankenHand.PA;
				}
			}
		}

		// その他
		if (whichHand(referenceHand, "p", "c") && whichHand(referenceHand, "p", "g")) {
			// 最多手がパー
			handCounter("Choki");
			oneTimeChoki++;
			return JankenHand.CHOKI;
		} else if (whichHand(referenceHand, "c", "g") && whichHand(referenceHand, "c", "p")) {
			// 最多手がチョキ
			handCounter("Gu");
			oneTimeGu++;
			return JankenHand.GU;
		} else if (whichHand(referenceHand, "g", "c") && whichHand(referenceHand, "g", "p")) {
			// 最多手がグー
			handCounter("Pa");
			oneTimePa++;
			return JankenHand.PA;
		}
		throw new RuntimeException("ありえない手の組み合わせでした。:" + referenceHand);
	}

//	最多手判断処理メソッド
	private boolean whichHand(String referenceHand, String compareHand1, String compareHand2) {
		return dataCount.get(referenceHand + compareHand1) >= dataCount.get(referenceHand + compareHand2);
	}

	private boolean whichNull(String referenceHand, String notNull, String yesNullOne, String yesNullTwo) {
		return dataCount.get(referenceHand + notNull) != null && dataCount.get(referenceHand + yesNullOne) == null
				&& dataCount.get(referenceHand + yesNullTwo) == null;
	}

	private boolean whichNull2(String referenceHand, String notNullOne, String notNullTwo, String yesNull) {
		return dataCount.get(referenceHand + notNullOne) != null && dataCount.get(referenceHand + notNullTwo) != null
				&& dataCount.get(referenceHand + yesNull) == null;
	}

//	勝敗結果判別、学習信頼度の調整メソッド
	private void loseCount(SubjectiveMatchStatus currentMatchStatus, Map<String, Integer> dataCount) {
		int scoreDifference = currentMatchStatus.ownScore - rememberMyScore;
		int oneTime[] = { oneTimeChoki, oneTimeGu, oneTimePa };
		switch (scoreDifference) {
		case 0:
			losingStreakCount++;
			for (int i = 0; i < oneTime.length; i++) {
				if (oneTime[i] == 1) {
					switch (i) {
					case 0:
						if (dataCount.get(referenceHand + "p") != null) {
							int count = dataCount.get(referenceHand + "p");
							count--;
							dataCount.replace(referenceHand + "p", count);
						}
						loseChoki++;
						break;
					case 1:
						if (dataCount.get(referenceHand + "c") != null) {
							int count2 = dataCount.get(referenceHand + "c");
							count2--;
							dataCount.replace(referenceHand + "c", count2);
						}
						loseGu++;
						break;
					default:
						if (dataCount.get(referenceHand + "g") != null) {
							int count3 = dataCount.get(referenceHand + "g");
							count3--;
							dataCount.replace(referenceHand + "g", count3);
						}
						losePa++;
						break;
					}
				}
			}
			break;
		case 1:
			losingStreakCount = 0;
			break;
		default:
			losingStreakCount = 0;
			for (int i = 0; i < oneTime.length; i++) {
				if (oneTime[i] == 1) {
					switch (i) {
					case 0:
						if (dataCount.get(referenceHand + "p") != null) {
							int count = dataCount.get(referenceHand + "p");
							count++;
							dataCount.replace(referenceHand + "p", count);
						}
						break;
					case 1:
						if (dataCount.get(referenceHand + "c") != null) {
							int count2 = dataCount.get(referenceHand + "c");
							count2++;
							dataCount.replace(referenceHand + "c", count2);
						}
						break;
					default:
						if (dataCount.get(referenceHand + "g") != null) {
							int count3 = dataCount.get(referenceHand + "g");
							count3++;
							dataCount.replace(referenceHand + "g", count3);
						}
						break;
					}
				}
			}
			break;
		}
		oneTimeChoki = oneTimeGu = oneTimePa = 0;
	}

//	出した手の数を記憶
	private void handCounter(String handName) {
		// 参照する文字列がすでに存在する場合、カウント+1，ない場合1をセット。
		if (handCount.get(handName) != null) {
			// 実際はカウント変数に現在の数値入れて、その数値を＋1して置き換える。
			int count = handCount.get(handName);
			count++;
			handCount.replace(handName, count);
		}
		// そうでない場合は新しく要素を作って、カウントを1にする
		else {
			handCount.put(handName, 1);
		}
	}

//　敗北率計算メソッド
	private void computeLose() {
		if (handCount.containsKey("Choki") == false || handCount.containsKey("Pa") == false
				|| handCount.containsKey("Gu") == false) {
		} else {
			losePerChoki = (double) loseChoki / handCount.get("Choki") * 100;
			losePerGu = (double) loseGu / handCount.get("Gu") * 100;
			losePerPa = (double) losePa / handCount.get("Pa") * 100;
		}
	}

//	ランダム戦術返答メソッド
	private JankenHand returnRandom() {

		int value = random.nextInt(100);

		if (value < 20) {
			return JankenHand.GU;
		} else if (value < 55) {
			return JankenHand.PA;
		} else {
			return JankenHand.CHOKI;
		}
	}

}
