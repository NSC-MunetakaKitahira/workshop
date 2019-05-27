package carrot.console;

import java.util.Arrays;

import carrot.game.JankenGame;
import carrot.game.JankenGame.Result;
import carrot.game.roundrobin.RoundRobinCompetition;
import carrot.game.roundrobin.WinPoints;
import carrot.player.oozono1;
import carrot.player.oozono2;
import carrot.player.oozono3;
import carrot.player.hirako.Hirako_DynamicRandomPlayer;
import carrot.player.hirako.Hirako_PerceptronPlayer;
import carrot.player.hirako.Hirako_RandomPlayer;
import carrot.player.ikami.IkamiPlayer32;
import carrot.player.ikami.IkamiPlayer42;
import carrot.player.kitahira.AlwaysPaPlayer;
import carrot.player.kitahira.AlwaysPaPlayer2;
import carrot.player.kitahira.AlwaysPaPlayer3;
import carrot.player.kitahira.PreviousHandPlayer;
import carrot.player.sakura.SakurataniPlayerOne;
import carrot.player.sakura.SakurataniPlayerThree;
import carrot.player.sakura.SakurataniPlayerTwo;
import carrot.player.takakuwa.TakakuwaPlayer1;
import carrot.player.takakuwa.TakakuwaPlayer2;
import carrot.player.takakuwa.TakakuwaPlayer3;
import carrot.player.umemura.UmemuraChokiPaPlayer;
import carrot.player.umemura.UmemuraHighPointPlayer;
import carrot.player.umemura.UmemuraRandomPlayer;

/**
 * 総当たり戦をコンソールに出力
 */
public class ConsoleRoundRobin {

	public static void start(int numberOfRounds) {
		
		RoundRobinCompetition compet = new RoundRobinCompetition(numberOfRounds, Arrays.asList(
				new Hirako_DynamicRandomPlayer(),
				new Hirako_PerceptronPlayer(),
				new Hirako_RandomPlayer(),
				new IkamiPlayer32(),
				new IkamiPlayer42(),
				new SakurataniPlayerOne(),
				new SakurataniPlayerTwo(),
				new SakurataniPlayerThree(),
				new TakakuwaPlayer1(),
				new TakakuwaPlayer2(),
				new TakakuwaPlayer3(),
				new UmemuraChokiPaPlayer(),
				new UmemuraHighPointPlayer(),
				new UmemuraRandomPlayer(),
				new oozono1(),
				new oozono2(),
				new oozono3()
				));
		
		compet.start(
				game -> gameStarting(game),
				gameResult -> gameFinished(gameResult),
				winPoints -> competitionFinished(winPoints));
	}

	private static void gameStarting(JankenGame game) {
		// ゲーム開始時
		System.out.println(
				"P1:" + game.player1.getClass().getSimpleName() 
				+ " vs P2:" + game.player2.getClass().getSimpleName());
	}

	private static void gameFinished(Result gameResult) {
		// ゲーム結果
		System.out.println(gameResult.format());
		System.out.println("---------------------------------");
		
		// 入力待ちにして一時停止
		//ConsoleInput.waitEnter();
	}
	

	private static void competitionFinished(WinPoints winPoints) {
		// 総当たり結果
		System.out.println("========= RESULT =========");
		
		winPoints.sortedList().forEach(x -> {
			System.out.println(x.player.getSimpleName() + ": " + x.points + " (" + x.subPoints + ")");
		});
	}
}
