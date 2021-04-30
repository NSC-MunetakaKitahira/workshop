package carrot.console;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import carrot.console.file.elimination.JsonSingleElimination;
import carrot.game.player.JankenPlayer;
import carrot.game.player.entries.hirako.Hirako_DynamicRandomPlayer;
import carrot.game.player.entries.hirako.Hirako_PerceptronPlayer;
import carrot.game.player.entries.hirako.Hirako_RandomPlayer;
import carrot.game.player.entries.ikami.IkamiPlayer32;
import carrot.game.player.entries.ikami.IkamiPlayer42;
import carrot.game.player.entries.oozono.oozono1;
import carrot.game.player.entries.oozono.oozono2;
import carrot.game.player.entries.oozono.oozono3;
import carrot.game.player.entries.sakura.SakurataniPlayerOne;
import carrot.game.player.entries.sakura.SakurataniPlayerThree;
import carrot.game.player.entries.sakura.SakurataniPlayerTwo;
import carrot.game.player.entries.takakuwa.TakakuwaPlayer1;
import carrot.game.player.entries.takakuwa.TakakuwaPlayer2;
import carrot.game.player.entries.takakuwa.TakakuwaPlayer3;
import carrot.game.player.entries.umemura.UmemuraChokiPaPlayer;
import carrot.game.player.entries.umemura.UmemuraHighPointPlayer;
import carrot.game.player.entries.umemura.UmemuraRandomPlayer;

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
				);
		Collections.shuffle(players);
		
		JsonSingleElimination.start(
				numberOfRounds,
				players,
				Paths.get("F:\\biz\\Temp\\2021\\05\\work\\janken-result.json"));
	}
}
