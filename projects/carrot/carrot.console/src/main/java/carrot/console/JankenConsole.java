package carrot.console;

import carrot.game.player.hirako.Hirako_DynamicRandomPlayer;
import carrot.game.player.hirako.Hirako_PerceptronPlayer;
import carrot.game.player.kitahira.KitahiraExpectedValue;
import carrot.game.player.sample.PreviousHandPlayer;
import carrot.game.player.sample.RandomPlayer;

public class JankenConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		// １ゲームのラウンド数
		int numberOfRounds = 300;
		
		// 一対一の１マッチ限り
		ConsoleOneMatch.start(
				numberOfRounds,
				new KitahiraExpectedValue(),
				new Hirako_DynamicRandomPlayer());
	}
}
