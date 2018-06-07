package apricot.console;

import java.util.List;
import java.util.Scanner;

import apricot.dom.Calculator;
import apricot.dom.Commons;
import apricot.dom.Time;
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
		String startTime;
		String endTime;

		System.out.println("時刻は \"8:30\" の形式で入力してください。");
		
		try(Scanner scan = new Scanner(System.in)) {
			System.out.print("開始時刻 = ");
			startTime = scan.nextLine();
	
			System.out.print("終了時刻 = ");
			endTime = scan.nextLine();
		}
		
		TimePeriod actualTimes= new TimePeriod(startTime,endTime);
		
//		System.out.println("確認用:"+actualTimes.getStartTime()+""+actualTimes.getEndTime());
	
		Calculator cal=new Calculator(actualTimes, workShift);
		
		cal.calculate();
	}
	
}
