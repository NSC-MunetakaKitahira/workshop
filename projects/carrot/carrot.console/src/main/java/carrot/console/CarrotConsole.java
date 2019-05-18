package carrot.console;

import java.util.Arrays;

import carrot.game.RoundRobinCompetition;
import carrot.janken.player.kitahira.AlwaysPaPlayer;
import carrot.janken.player.kitahira.IncrementalPlayer;
import carrot.janken.player.kitahira.PreviousHandPlayer;
import carrot.janken.player.kitahira.ProbablyPaPlayer;
import carrot.janken.player.kitahira.RotationPlayer;

public class CarrotConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		int numberOfRounds = 100;
//		JankenPlayer player1 = new PreviousHandPlayer();
//		JankenPlayer player2 = new IncrementalPlayer();
//		GameWithPrinting game = new GameWithPrinting(numberOfRounds, player1, player2);
//		game.start();
		
		RoundRobinCompetition compet = new RoundRobinCompetition(numberOfRounds, Arrays.asList(
				new AlwaysPaPlayer(),
				new IncrementalPlayer(),
				new PreviousHandPlayer(),
				new ProbablyPaPlayer(),
				new RotationPlayer()
				));
		compet.start();
		
	}
	
}
