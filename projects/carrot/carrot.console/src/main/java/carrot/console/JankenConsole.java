package carrot.console;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import carrot.console.file.elimination.JsonSingleElimination;
import carrot.game.player.JankenPlayer;
import carrot.game.player.sample.AlwaysPaPlayer;
import carrot.game.player.sample.CountingPlayer;
import carrot.game.player.sample.PreviousHandPlayer;

public class JankenConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		// １ゲームのラウンド数
		int numberOfRounds = 300;
		
		// 一対一の１ゲーム限り
		//start(numberOfRounds);
		
		// 総当たり戦
		//ConsoleRoundRobin.start(numberOfRounds);
		
		List<JankenPlayer> players = Arrays.asList(
				new AlwaysPaPlayer(),
				new CountingPlayer(),
				new PreviousHandPlayer()
				);
		
		JsonSingleElimination.start(
				numberOfRounds,
				players,
				Paths.get("F:\\biz\\Temp\\2021\\05\\work\\janken-result.json"));
	}
}
