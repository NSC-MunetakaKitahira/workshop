package apricot.console;

import java.util.Scanner;

public class ApricotConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		String workStartIn;
		String workEndIn;

		System.out.println("時刻は \"8:30\" の形式で入力してください。");
		
		try(Scanner scan = new Scanner(System.in)) {
			System.out.print("開始時刻 = ");
			workStartIn = scan.nextLine();
	
			System.out.print("終了時刻 = ");
			workEndIn = scan.nextLine();
		}
		
		apricot.dom.Process.process(apricot.dom.FormatChange.parseTimeString(workStartIn), apricot.dom.FormatChange.parseTimeString(workEndIn));
	}	
}
