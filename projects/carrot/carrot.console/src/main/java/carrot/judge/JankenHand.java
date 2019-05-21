package carrot.judge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ジャンケンの手役の定義
 */
public enum JankenHand {

	/** グー:2点 */
	GU(0, 2),
	
	/** チョキ:4点 */
	CHOKI(1, 4),
	
	/** パー:5点 */
	PA(2, 5),
	
	;
	
	/** 全ての手役の集合 */
	private static final Set<JankenHand> ALL_HANDS = new HashSet<>(Arrays.asList(JankenHand.values()));
	
	/** 判定ロジック用の値 */
	private final int valueForJudge;
	
	/** 勝利時の得点 */
	public final int winnerGain;
	
	JankenHand(int valueForJudge, int winnerGain) {
		this.valueForJudge = valueForJudge;
		this.winnerGain = winnerGain;
	}
	
	/**
	 * 判定ロジック用の値から手役を返す
	 */
	private static JankenHand fromValueForJudge(int valueForJudge) {
		return ALL_HANDS.stream()
				.filter(h -> h.valueForJudge == valueForJudge)
				.findFirst()
				.get();
	}
	
	/**
	 * 全ての手役で最大の得点を返す
	 */
	public static int maxGain() {
		return ALL_HANDS.stream()
				.max((a, b) -> a.winnerGain - b.winnerGain)
				.get().winnerGain;
	}
	
	public static boolean isValid(JankenHand hand) {
		return ALL_HANDS.contains(hand);
	}
	
	/**
	 * 与えられた手役との対戦結果を返す
	 * @param opponentHand 相手の手役
	 * @return 結果
	 */
	public Judgement competeAgainst(JankenHand opponentHand) {
		if (this == opponentHand) {
			return Judgement.DRAW;
		}
		
		int diff = this.valueForJudge - opponentHand.valueForJudge;
		if (diff == -1 || diff == 2) {
			return Judgement.WIN;
		}
		
		return Judgement.LOSE;
	}
	
	/**
	 * この手役に勝てる手役を返す
	 */
	public JankenHand handToWin() {
		return JankenHand.fromValueForJudge((this.valueForJudge + 3 - 1) % 3);
	}
	
	/**
	 * この手役に負ける手役を返す
	 */
	public JankenHand handToLose() {
		return JankenHand.fromValueForJudge((this.valueForJudge + 1) % 3);
	}
	
	/**
	 * コンソール出力用にフォーマット
	 */
	public String format() {
		switch (this) {
		case GU: return " G U ";
		case CHOKI: return "CHOKI";
		case PA: return " P A ";
		default: throw new RuntimeException("unknown hand: " + this);
		}
	}
	
	public enum Judgement {
		WIN,
		DRAW,
		LOSE,
	}
}
