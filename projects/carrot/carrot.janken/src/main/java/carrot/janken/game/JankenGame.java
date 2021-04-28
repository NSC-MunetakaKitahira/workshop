package carrot.janken.game;

import java.util.function.Consumer;

import carrot.janken.judge.JankenHand;
import carrot.janken.player.JankenPlayer;
import carrot.janken.player.SubjectiveGameStatus;

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
	
	/**
	 * ゲームを開始
	 * @param roundNotifier 1ラウンドごとにゲーム進行状況を受け取る関数
	 * @return 結果
	 */
	public JankenGameResult start(Consumer<JankenGameStatus> roundNotifier) {
		
		player1.newGame();
		player2.newGame();
		
		JankenGameStatus gameStatus = JankenGameStatus.init(numberOfRounds);
		
		for (int i = 0; i < numberOfRounds; i++) {
			NextHand nextP1Hand = NextHand.attempt(player1, gameStatus.forPlayer1());
			NextHand nextP2Hand = NextHand.attempt(player2, gameStatus.forPlayer2());
			
			if (nextP1Hand.hasCrashed || nextP2Hand.hasCrashed) {
				return JankenGameResult.crashed(nextP1Hand.hasCrashed, nextP2Hand.hasCrashed);
			}
			
			gameStatus = gameStatus.processRound(nextP1Hand.nextHand, nextP2Hand.nextHand);
			roundNotifier.accept(gameStatus);
		}
		
		return JankenGameResult.finished(gameStatus.player1Score, gameStatus.player2Score);
	}
	
	private static class NextHand {
		
		/** 次の手 */
		public final JankenHand nextHand;
		
		/** 次の手が取得できずにクラッシュした場合はtrue */
		public final boolean hasCrashed;
		
		private NextHand(JankenHand nextHand, boolean hasCrashed) {
			this.nextHand = nextHand;
			this.hasCrashed = hasCrashed;
		}

		/**
		 * プログラム全体がクラッシュしないように安全にJankenPlayerからnextHandを取得する
		 * @param player
		 * @param gameStatus
		 * @return
		 */
		public static NextHand attempt(JankenPlayer player, SubjectiveGameStatus gameStatus) {
			try {
				JankenHand hand = player.nextHand(gameStatus);
				if (JankenHand.isValid(hand)) {
					return new NextHand(hand, false);
				}
				
				System.out.println(player.getClass().getSimpleName() + " returns invalid hand: " + hand);
				return new NextHand(null, true);
			} catch (Exception ex) {
				ex.printStackTrace();
				return new NextHand(null, true);
			}
		}
	}

}
