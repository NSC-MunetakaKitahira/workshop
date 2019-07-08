package apricot.console;

import java.util.Scanner;

import apricot.dom.CommonUtil;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;
import apricot.dom.workshift.WorkShift;
import apricot.dom.workshift.WorkShiftRepository;

public class ApricotConsole {

	public static void main(String[] args) {

		TimePeriod timeStamp = readTimeStamp();
		
		WorkShift workShift = WorkShiftRepository.get();

		TimePeriods actualNormalWorks = workShift.actualNormalWorks(timeStamp);
		TimePeriods actualOvertimeWorks = workShift.actualOvertimeWorks(timeStamp);
		
		print(actualNormalWorks, actualOvertimeWorks);
	}

	private static TimePeriod readTimeStamp() {
		String startTime;
		String endTime;

		print("please input like as '8:30'");
		
		try(Scanner scan = new Scanner(System.in)) {
			print("start = ");
			startTime = scan.nextLine();
	
			print("end = ");
			endTime = scan.nextLine();
		}
		
		return new TimePeriod(CommonUtil.parse(startTime), CommonUtil.parse(endTime));
	}

	private static void print(TimePeriods actualNormalWorks, TimePeriods actualOvertimeWorks) {
		print("NORMAL WORKING");
		print(actualNormalWorks);
		print("total " + CommonUtil.format(actualNormalWorks.calculateTime()));
		
		print("OVERTIME WORKING");
		print(actualOvertimeWorks);
		print("total " + CommonUtil.format(actualOvertimeWorks.calculateTime()));
	}
	
	private static void print(TimePeriods periods) {
		periods.forEach(p -> print(p.format()));
	}
	
	private static void print(String text) {
		System.out.println(text);
	}
}
