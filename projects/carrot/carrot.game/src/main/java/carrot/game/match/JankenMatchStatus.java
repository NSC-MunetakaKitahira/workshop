package carrot.game.match;

import carrot.game.judge.JankenHand;
import carrot.game.judge.JankenJudgement;
import carrot.game.player.SubjectiveGameStatus;

/**
 * あるラウンド完了時点でのマッチ状況
 */
public class JankenMatchStatus {

	/** 最大ラウンド数 */
	public final int maxRound;
	
	/** 処理済みのラウンド数 */
	public final int processedRounds;
	
	/** 前ラウンドのプレイヤー１の手 */
	public final JankenHand previousPlayer1Hand;
	
	/** 前ラウンドのプレイヤー２の手 */
	public final JankenHand previousPlayer2Hand;
	
	/** 現在のプレイヤー１のスコア */
	public final int player1Score;
	
	/** 現在のプレイヤー２のスコア */
	public final int player2Score;

	private JankenMatchStatus(
			int maxRound,
			int round,
			JankenHand previousPlayer1Hand,
			JankenHand previousPlayer2Hand,
			int player1Score,
			int player2Score) {
		
		this.maxRound = maxRound;
		this.processedRounds = round;
		this.previousPlayer1Hand = previousPlayer1Hand;
		this.previousPlayer2Hand = previousPlayer2Hand;
		this.player1Score = player1Score;
		this.player2Score = player2Score;
	}
	
	/**
	 * マッチの開始状態を作る
	 */
	public static JankenMatchStatus init(int maxRound) {
		return new JankenMatchStatus(maxRound, 0, null, null, 0, 0);
	}
	
	/**
	 * ラウンドを処理する
	 * @param player1Hand
	 * @param player2Hand
	 */
	public JankenMatchStatus processRound(JankenHand player1Hand, JankenHand player2Hand) {
		JankenJudgement judgement = JankenJudgement.judge(player1Hand, player2Hand);
		return new JankenMatchStatus(
				maxRound,
				processedRounds + 1,
				player1Hand,
				player2Hand,
				player1Score + judgement.player1Gain,
				player2Score + judgement.player2Gain);
	}
	
	/**
	 * プレイヤー１用のSubjectiveオブジェクトを返す
	 * @return
	 */
	public SubjectiveGameStatus forPlayer1() {
		return new SubjectiveGameStatus(processedRounds + 1, maxRound, player1Score, player2Score, previousPlayer2Hand);
	}
	
	/**
	 * プレイヤー２用のSubjectiveオブジェクトを返す
	 * @return
	 */
	public SubjectiveGameStatus forPlayer2() {
		return new SubjectiveGameStatus(processedRounds + 1, maxRound, player2Score, player1Score, previousPlayer1Hand);
	}
}
