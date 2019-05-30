package apricot.console;

import java.util.Scanner;

import apricot.dom.Calculator;
import apricot.dom.ShowResult;
import apricot.dom.TimePeriod;
import apricot.dom.WorkShift;
import apricot.dom.WorkShiftRepository;
import apricot.dom.TimeOfDay;

public class ApricotConsole {

	/**
	 * エントリポイント
	 * 
	 * @param args コマンドライン引数は使わない
	 */
	public static void main(String[] args) {
		
		WorkShift workShift = WorkShiftRepository.getTestdata();
		TimeOfDay startTime;
		TimeOfDay endTime;

		System.out.println("時刻は \"8:30\" の形式で入力してください。");
		
		try(Scanner scan = new Scanner(System.in)) {
			System.out.print("開始時刻 = ");
			startTime = new TimeOfDay(scan.nextLine());
	
			System.out.print("終了時刻 = ");
			endTime = new TimeOfDay(scan.nextLine());
		}
		
		TimePeriod parseTime = new TimePeriod(startTime, endTime);
		
		ShowResult.show(Calculator.calculate(parseTime, workShift));
	}
	
}
