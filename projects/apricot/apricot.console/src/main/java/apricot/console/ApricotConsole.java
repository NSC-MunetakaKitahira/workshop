package apricot.console;

import java.util.List;
import java.util.Scanner;

import apricot.dom.CommonUtil;
import apricot.dom.time.TimePeriod;
import apricot.dom.workshift.WorkShift;
import apricot.dom.workshift.WorkShiftRepository;

public class ApricotConsole {

	public static void main(String[] args) {

		TimePeriod timeStamp = readTimeStamp();
		
		WorkShift workShift = WorkShiftRepository.get();

		List<TimePeriod> actualNormalWorks = workShift.actualNormalWorks(timeStamp);
		List<TimePeriod> actualOvertimeWorks = workShift.actualOvertimeWorks(timeStamp);
		
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

	private static void print(List<TimePeriod> actualNormalWorks, List<TimePeriod> actualOvertimeWorks) {
		print("NORMAL WORKING");
		print(actualNormalWorks);
		print("total " + CommonUtil.format(CommonUtil.calculateTime(actualNormalWorks)));
		
		print("OVERTIME WORKING");
		print(actualOvertimeWorks);
		print("total " + CommonUtil.format(CommonUtil.calculateTime(actualOvertimeWorks)));
	}
	
	private static void print(List<TimePeriod> periods) {
		periods.stream().forEach(p -> print(p.format()));
	}
	
	private static void print(String text) {
		System.out.println(text);
	}
}
