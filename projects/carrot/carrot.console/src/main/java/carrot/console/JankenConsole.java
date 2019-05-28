package carrot.console;

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
		//ConsoleOneGame.start(numberOfRounds);
		
		// 総当たり戦
		ConsoleRoundRobin.start(numberOfRounds);
		
	}
}
