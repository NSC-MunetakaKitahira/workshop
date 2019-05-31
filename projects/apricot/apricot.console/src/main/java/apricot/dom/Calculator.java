package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	public static void calculate(TimePeriod timeStamp, WorkShift workShift) {
		
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = Commons.getDuplication(timeStamp, workShift.getWorkTimePeriod());
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOverTimePeriod().size(); i++) {
			TimePeriod duplication = Commons.getDuplication(
					timeStamp,
					workShift.getOverTimePeriod().get(i));
			
			if (duplication.length() == 2) {
		
		
			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualOverworkTimes.add(new TimePeriod { duplication[0], duplication[1] });	
			}
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreakTimePeriod().size(); i++) {
			TimePeriod duplication = Commons.getDuplication(
					timeStamp,
					workShift.getBreakTimePeriod().get(i));
			
			if (duplication.length() == 0 || duplication.length() == 1) continue;
		}
		
			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
		 	actualBreakTimes.add(new Integer[] { duplication[0], duplication[1] });
	     	
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualBreakTimes.size(); i++) {
//			int workStart = actualWorkTime[0];
//			int workEnd = actualWorkTime[1];
//			int breakStart = actualBreakTimes.get(i)[0];
//			int breakEnd = actualBreakTimes.get(i)[1];
			TimePeriod workTimePeriod = actualWorkTime;
			TimePeriod breakTimePeriod = actualBreakTimes.get(i);			
			
			TimePeriod subtraction = Commons.getSubtraction(actualWorkTime, actualBreakTimes);
			
			if (subtraction.length == 2) {
				actualWorkTimesWithoutBreak.add(new TimePeriod[] { subtraction[0], subtraction[1] });
				break;
			} else if (subtraction.length == 4) {
				actualWorkTimesWithoutBreak.add(new TimePeriod[] { subtraction[0], subtraction[1] });
				actualWorkTimesWithoutBreak.add(new TimePeriod[] { subtraction[2], subtraction[3] });
				break;
			}
		}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				int overworkStart = actualOverworkTimes.get(i)[0];
				int overworkEnd = actualOverworkTimes.get(i)[1];
				int breakStart = actualBreakTimes.get(j)[0];
				int breakEnd = actualBreakTimes.get(j)[1];
				
				TimePeriod[] subtraction = Commons.getSubtraction(overworkStart, overworkEnd, breakStart, breakEnd);

				if (subtraction.length == 2) {
					actualOverworkTimesWithoutBreak.add(new TimePeriod[] { subtraction[0], subtraction[1] });
					break;
				} else if (subtraction.length == 4) {
					actualOverworkTimesWithoutBreak.add(new TimePeriod[] { subtraction[0], subtraction[1] });
					actualOverworkTimesWithoutBreak.add(new TimePeriod[] { subtraction[2], subtraction[3] });
					break;
				}
			}
		}
		
		System.out.println("就業時間");
		int sumWorkTime = 0;
		for (int i = 0; i < actualWorkTimesWithoutBreak.size(); i++) {
			int start = actualWorkTimesWithoutBreak.get(i)[0];
			int end = actualWorkTimesWithoutBreak.get(i)[1];
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumWorkTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumWorkTime));
		
		System.out.println("残業時間");
		int sumOverworkTime = 0;
		for (int i = 0; i < actualOverworkTimesWithoutBreak.size(); i++) {
			int start = actualOverworkTimesWithoutBreak.get(i)[0];
			int end = actualOverworkTimesWithoutBreak.get(i)[1];
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumOverworkTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumOverworkTime));
		
		System.out.println("休憩時間");
		int sumBreakTime = 0;
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			int start = actualBreakTimes.get(i)[0];
			int end = actualBreakTimes.get(i)[1];
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumBreakTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumBreakTime));
	}
}
