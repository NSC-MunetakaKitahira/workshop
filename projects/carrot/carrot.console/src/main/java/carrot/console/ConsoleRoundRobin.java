package carrot.console;

import java.util.Arrays;

import carrot.game.JankenGame;
import carrot.game.JankenGame.Result;
import carrot.game.roundrobin.RoundRobinCompetition;
import carrot.game.roundrobin.WinPoints;
import carrot.player.kitahira.AlwaysPaPlayer;
import carrot.player.kitahira.PreviousHandPlayer;
import carrot.player.kitahira.CountingPlayer;
import carrot.player.takakuwa.NewPlayer1;
import carrot.player.takakuwa.TakakuwaPlayer1;
import carrot.player.takakuwa.NewPlayer3;
import carrot.player.takakuwa.NewPlayer4;
import carrot.player.takakuwa.TakakuwaPlayer2;
import carrot.player.takakuwa.TakakuwaPlayer3;
import carrot.player.takakuwa.CrashPlayer;
import carrot.player.takakuwa.NewPlayer7;

/**
 * 総当たり戦をコンソールに出力
 */
public class ConsoleRoundRobin {

	public static void start(int numberOfRounds) {
		
		RoundRobinCompetition compet = new RoundRobinCompetition(numberOfRounds, Arrays.asList(
				new AlwaysPaPlayer(),
				new PreviousHandPlayer(),
				new CountingPlayer(),
				new NewPlayer1(), //乱数
				new TakakuwaPlayer1(), //相手の出した手に勝てる手を学習
				new NewPlayer3(), //期待値
				new NewPlayer4(), //ぐーちょきぱー
				new NewPlayer7(), //例のやつ
				new TakakuwaPlayer2(), //負けないようにする
				new TakakuwaPlayer3(),  //100戦ぐーちょきぱーで学習して学習をもとに出す
				new CrashPlayer() //クラッシュさせて必ず引き分け
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
