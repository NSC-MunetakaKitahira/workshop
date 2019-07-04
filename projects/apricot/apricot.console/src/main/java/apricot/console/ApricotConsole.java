package apricot.console;

import java.util.Scanner;

import apricot.dom.record.WorkRecord;
import apricot.dom.time.TimeOfDay;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;
import apricot.dom.workshift.WorkShift;
import apricot.dom.workshift.WorkShiftRepository;

public class ApricotConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {

		TimePeriod timeStamp = readTimeStamp();
		
		WorkShift workShift = WorkShiftRepository.get();
		
		WorkRecord record = workShift.workRecord(timeStamp);
		
		print(record);
	}

	private static TimePeriod readTimeStamp() {
		String startTime;
		String endTime;

		print("時刻は \"8:30\" の形式で入力してください。");
		
		try(Scanner scan = new Scanner(System.in)) {
			print("開始時刻 = ");
			startTime = scan.nextLine();
	
			print("終了時刻 = ");
			endTime = scan.nextLine();
		}
		
		return new TimePeriod(new TimeOfDay(startTime), new TimeOfDay(endTime));
	}

	private static void print(WorkRecord result) {
		print("就業時間");
		print(result.workTimes);
		print("合計: " + result.sumWorkTime().format());
		
		print("残業時間");
		print(result.overworkTimes);
		print("合計: " + result.sumOverworkTime().format());
		
		print("休憩時間");
		print(result.breakTimes);
		print("合計: " + result.sumBreakTime().format());
	}
	
	private static void print(TimePeriods periods) {
		periods.stream().forEach(p -> print(p.format()));
	}
	
	private static void print(String text) {
		System.out.println(text);
	}
}
