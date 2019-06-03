package apricot.console;

import java.util.Scanner;

import apricot.dom.Calculator;
import apricot.dom.Commons;
import apricot.dom.WorkShift;
import apricot.dom.WorkShiftRepository;
import apricot.dom.TimePeriod;
import apricot.dom.TimeOfDay;

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
		
		TimePeriod stamp = new TimePeriod(new TimeOfDay(Commons.parseTimeString(startTime)),new TimeOfDay( Commons.parseTimeString(endTime)));
		
		Calculator.calculate(stamp, workShift);
	}
	
}
