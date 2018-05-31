package apricot.console;

import java.util.Scanner;

import apricot.dom.*;

public class ApricotConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		WorkShift workShift = WorkShiftRepository.get();
		String startTime;
		String endTime;

		System.out.println("時刻は \"8:30\" の形式で入力してください。");
		
		try(Scanner scan = new Scanner(System.in)) {
			System.out.print("開始時刻 = ");
			startTime = scan.nextLine();
	
			System.out.print("終了時刻 = ");
			endTime = scan.nextLine();
		}
		
		WorkTime wt = new WorkTime(Commons.parseTimeString(startTime), Commons.parseTimeString(endTime), workShift);
		wt.printActualWorkTimeWithoutBreak();
		OverworkTime ot = new OverworkTime(Commons.parseTimeString(startTime), Commons.parseTimeString(endTime), workShift);
		ot.printActualOverWorkTimesWithoutBreak();
		BreakTime bt = new BreakTime(Commons.parseTimeString(startTime), Commons.parseTimeString(endTime), workShift);
		bt.printActualBreakTime();
	}
	
}
