package apricot.console;

import java.util.Scanner;

import apricot.dom.CalculationResult;
import apricot.dom.Commons;
import apricot.dom.TimeOfDay;
import apricot.dom.TimePeriod;
import apricot.dom.WorkShift;
import apricot.dom.WorkShiftRepository;

public class ApricotConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		WorkShift workShift = WorkShiftRepository.get();
		TimePeriod timeStamp = readTimeStamp();
		
		CalculationResult result = workShift.calculate(timeStamp);

		printResult(result);
	}

	private static TimePeriod readTimeStamp() {
		String startTime;
		String endTime;

		System.out.println("時刻は \"8:30\" の形式で入力してください。");
		
		try(Scanner scan = new Scanner(System.in)) {
			System.out.print("開始時刻 = ");
			startTime = scan.nextLine();
	
			System.out.print("終了時刻 = ");
			endTime = scan.nextLine();
		}
		
		return new TimePeriod(new TimeOfDay(startTime), new TimeOfDay(endTime));
	}

	private static void printResult(CalculationResult result) {
		System.out.println("就業時間");
		for (TimePeriod period : result.workTimes) {
			System.out.println(period.format());
		}
		System.out.println("合計: " + Commons.formatTime(result.sumWorkTime()));
		
		System.out.println("残業時間");
		for (TimePeriod period : result.overworkTimes) {
			System.out.println(period.format());
		}
		System.out.println("合計: " + Commons.formatTime(result.sumOverworkTime()));
		
		System.out.println("休憩時間");
		for (TimePeriod period : result.breakTimes) {
			System.out.println(period.format());
		}
		System.out.println("合計: " + Commons.formatTime(result.sumBreakTime()));
	}
	
}
