package carrot.game;

import carrot.judge.JankenHand;
import carrot.judge.JankenJudgement;

/**
 * あるラウンド完了時点でのゲーム状況
 */
public class JankenGameStatus {

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

	private JankenGameStatus(
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
	 * ゲームの開始状態を作る
	 */
	public static JankenGameStatus init(int maxRound) {
		return new JankenGameStatus(maxRound, 0, null, null, 0, 0);
	}
	
	/**
	 * ラウンドを処理する
	 * @param player1Hand
	 * @param player2Hand
	 */
	public JankenGameStatus processRound(JankenHand player1Hand, JankenHand player2Hand) {
		JankenJudgement judgement = JankenJudgement.judge(player1Hand, player2Hand);
		return new JankenGameStatus(
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
	public Subjective forPlayer1() {
		return new Subjective(processedRounds + 1, maxRound, player1Score, player2Score, previousPlayer2Hand);
	}
	
	/**
	 * プレイヤー２用のSubjectiveオブジェクトを返す
	 * @return
	 */
	public Subjective forPlayer2() {
		return new Subjective(processedRounds + 1, maxRound, player2Score, player1Score, previousPlayer1Hand);
	}
	
	/**
	 * いずれかのプレイヤーから見た主観的なゲーム状況
	 */
	public static class Subjective {
		
		/** 現在のラウンド */
		public final int round;
		
		/** 最大ラウンド数 */
		public final int maxRound;
		
		/** 自分のスコア */
		public final int ownScore;
		
		/** 相手のスコア */
		public final int opponentScore;
		
		/** 前ラウンドで相手が出した手 */
		public final JankenHand previousOpponentHand;
		
		public Subjective(int round, int maxRound, int ownScore, int opponentScore, JankenHand previousOpponentHand) {
			this.round = round;
			this.maxRound = maxRound;
			this.ownScore = ownScore;
			this.opponentScore = opponentScore;
			this.previousOpponentHand = previousOpponentHand;
		}
		
		/** 最初のラウンドであれば true */
		public boolean isFirstRound() {
			return round == 1;
		}
	}
}
