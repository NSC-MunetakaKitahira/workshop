package carrot.janken.game;

public class JankenGameResult {
	/** プレイヤー１のスコア */
	public final int player1Score;
	
	/** プレイヤー２のスコア */
	public final int player2Score;
	
	/** プレイヤー１の処理がクラッシュしたか */
	public final boolean hasPlayer1Crashed;
	
	/** プレイヤー２の処理がクラッシュしたか */
	public final boolean hasPlayer2Crashed;

	private JankenGameResult(int player1Score, int player2Score, boolean hasPlayer1Crashed, boolean hasPlayer2Crashed) {
		this.player1Score = player1Score;
		this.player2Score = player2Score;
		this.hasPlayer1Crashed = hasPlayer1Crashed;
		this.hasPlayer2Crashed = hasPlayer2Crashed;
	}
	
	/**
	 * 最後までゲームが完了した
	 * @param player1Score
	 * @param player2Score
	 * @return
	 */
	public static JankenGameResult finished(int player1Score, int player2Score) {
		return new JankenGameResult(player1Score, player2Score, false, false);
	}
	
	/**
	 * クラッシュした
	 * @param hasPlayer1Crashed
	 * @param hasPlayer2Crashed
	 * @return
	 */
	public static JankenGameResult crashed(boolean hasPlayer1Crashed, boolean hasPlayer2Crashed) {
		return new JankenGameResult(0, 0, hasPlayer1Crashed, hasPlayer2Crashed);
	}
	
	public ResultClass resultClass() {
		if (isDraw()) {
			return ResultClass.DRAW;
		}
		
		if (hasPlayer1Crashed) {
			return ResultClass.PLAYER2_WIN;
		}
		
		if (hasPlayer2Crashed) {
			return ResultClass.PLAYER1_WIN;
		}
		
		return player1Score > player2Score
				? ResultClass.PLAYER1_WIN
				: ResultClass.PLAYER2_WIN;
	}
	
	public int subPoints() {
		return Math.abs(player1Score - player2Score);
	}

	public String format() {
		if (hasCrashed()) {
			return formatCrashed();
		}
		
		return formatFinished();
	}

	private String formatFinished() {
		String scores = "(" + player1Score + " - " + player2Score + ")";
		
		if (player1Score == player2Score) {
			return "Draw " + scores;
		}
		
		String winner = player1Score > player2Score ? "Player1" : "Player2";
		return "Winner: " + winner + " " + scores;
	}
	
	private String formatCrashed() {
		if (hasPlayer1Crashed && hasPlayer2Crashed) {
			return "Draw (both players crashed)";
		} else if (hasPlayer1Crashed) {
			return "Winner: Player2 (Player1 crashed)";
		} else {
			return "Winner: Player1 (Player2 crashed)";
		}
	}
	
	private boolean isDraw() {
		return hasBothCrashed() || player1Score == player2Score;
	}
	
	private boolean hasCrashed() {
		return hasPlayer1Crashed || hasPlayer2Crashed;
	}
	
	private boolean hasBothCrashed() {
		return hasPlayer1Crashed && hasPlayer2Crashed;
	}

	
	public static enum ResultClass {
		PLAYER1_WIN,
		PLAYER2_WIN,
		DRAW,
	}
}
