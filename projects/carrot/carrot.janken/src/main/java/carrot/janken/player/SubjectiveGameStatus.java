package carrot.janken.player;

import carrot.janken.judge.JankenHand;

/**
 * いずれかのプレイヤーから見た主観的なゲーム状況
 */
public class SubjectiveGameStatus {
	
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
	
	public SubjectiveGameStatus(int round, int maxRound, int ownScore, int opponentScore, JankenHand previousOpponentHand) {
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
