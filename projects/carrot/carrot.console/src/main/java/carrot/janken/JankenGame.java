package carrot.janken;

import java.util.function.Consumer;

/**
 * ゲーム進行をコントロールするクラス
 */
public class JankenGame {
	
	/** このゲームのラウンド数 */
	public final int numberOfRounds;
	
	/** プレイヤー１ */
	public final JankenPlayer player1;
	
	/** プレイヤー２ */
	public final JankenPlayer player2;

	public JankenGame(int numberOfRounds, JankenPlayer player1, JankenPlayer player2) {
		this.numberOfRounds = numberOfRounds;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public Result start(Consumer<JankenGameStatus> statusNotifier) {
		
		JankenGameStatus gameStatus = new JankenGameStatus(numberOfRounds);
		
		for (int i = 0; i < numberOfRounds; i++) {
			NextHand nextP1Hand = NextHand.attempt(player1, gameStatus.forPlayer1());
			NextHand nextP2Hand = NextHand.attempt(player2, gameStatus.forPlayer2());
			
			if (nextP1Hand.hasCrashed || nextP2Hand.hasCrashed) {
				return Result.crashed(nextP1Hand.hasCrashed, nextP2Hand.hasCrashed);
			}
			
			gameStatus.nextRound(nextP1Hand.nextHand, nextP2Hand.nextHand);
			
			statusNotifier.accept(gameStatus);
		}
		
		return Result.finished(gameStatus.getPlayer1Score(), gameStatus.getPlayer2Score());
	}
	
	private static class NextHand {
		
		public final JankenHand nextHand;
		public final boolean hasCrashed;
		
		private NextHand(JankenHand nextHand, boolean hasCrashed) {
			this.nextHand = nextHand;
			this.hasCrashed = hasCrashed;
		}

		public static NextHand attempt(JankenPlayer player, JankenGameStatus.Subjective gameStatus) {
			try {
				return new NextHand(player.nextHand(gameStatus), false);
			} catch (Exception ex) {
				return new NextHand(null, true);
			}
		}
	}
	
	/**
	 * ゲームの結果
	 */
	public static class Result {
		/** プレイヤー１のスコア */
		public final int player1Score;
		
		/** プレイヤー２のスコア */
		public final int player2Score;
		
		/** プレイヤー１の処理がクラッシュしたか */
		public final boolean hasPlayer1Crashed;
		
		/** プレイヤー２の処理がクラッシュしたか */
		public final boolean hasPlayer2Crashed;

		private Result(int player1Score, int player2Score, boolean hasPlayer1Crashed, boolean hasPlayer2Crashed) {
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
		public static Result finished(int player1Score, int player2Score) {
			return new Result(player1Score, player2Score, false, false);
		}
		
		/**
		 * クラッシュした
		 * @param hasPlayer1Crashed
		 * @param hasPlayer2Crashed
		 * @return
		 */
		public static Result crashed(boolean hasPlayer1Crashed, boolean hasPlayer2Crashed) {
			return new Result(0, 0, hasPlayer1Crashed, hasPlayer2Crashed);
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
	}
	
	public enum ResultClass {
		PLAYER1_WIN,
		PLAYER2_WIN,
		DRAW,
	}
}
