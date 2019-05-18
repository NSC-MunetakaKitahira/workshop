package carrot.console;

import carrot.janken.JankenGame;
import carrot.janken.JankenPlayer;

public class GameWithPrinting {

	private final int numberOfRounds;
	private final JankenPlayer player1;
	private final JankenPlayer player2;
	
	public GameWithPrinting(int numberOfRounds, JankenPlayer player1, JankenPlayer player2) {
		this.numberOfRounds = numberOfRounds;
		this.player1 = player1;
		this.player2 = player2;
	}

	public void start() {
		System.out.println(
				"P1:" + player1.getClass().getSimpleName() 
				+ " vs P2:" + player2.getClass().getSimpleName());
		
		JankenGame game = new JankenGame(numberOfRounds, player1, player2);
		
		JankenGame.Result result = game.start(status -> {
			System.out.println(status.format());
		});
		
		System.out.println(result.format());
	}
}
