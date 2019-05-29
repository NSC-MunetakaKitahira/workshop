package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

	public static void calculate(TimePeriod timeStamp, WorkShift workShift) {

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = timeStamp.getDuplicationWith(workShift.getWorkTime());
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = new ArrayList<>();
		for (TimePeriod overworkTime : workShift.getOverworkTimes()) {
			TimePeriod duplication = timeStamp.getDuplicationWith(overworkTime);
			
			if (duplication == null) continue;
			
			actualOverworkTimes.add(duplication);
		}
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = new ArrayList<>();
		for (TimePeriod breakTime : workShift.getBreakTimes()) {
			TimePeriod duplication = timeStamp.getDuplicationWith(breakTime);
			
			if (duplication == null) continue;
			
			actualBreakTimes.add(duplication);
		}
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak;
		if (actualBreakTimes.isEmpty()) {
			actualWorkTimesWithoutBreak = Arrays.asList(actualWorkTime);
		} else {
			actualWorkTimesWithoutBreak = new ArrayList<>();
			for (TimePeriod actualBreakTime : actualBreakTimes) {
				List<TimePeriod> subtraction = actualWorkTime.subtract(actualBreakTime);
				actualWorkTimesWithoutBreak.addAll(subtraction);
				
				if (!subtraction.isEmpty()) break;
			}
		}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak;
		if (actualBreakTimes.isEmpty()) {
			actualOverworkTimesWithoutBreak = actualOverworkTimes;
		} else {
			actualOverworkTimesWithoutBreak = new ArrayList<>();
			
			for (TimePeriod overworkTime : actualOverworkTimes) {
				for (TimePeriod breakTime : actualBreakTimes) {
					List<TimePeriod> subtraction = overworkTime.subtract(breakTime);
					actualOverworkTimesWithoutBreak.addAll(subtraction);
					
					if (!subtraction.isEmpty()) break;
				}
			}
		}
		
		System.out.println("就業時間");
		int sumWorkTime = 0;
		for (TimePeriod period : actualWorkTimesWithoutBreak) {
			System.out.println(period.format());
			sumWorkTime += period.minutesOfLength();
		}
		System.out.println("合計: " + Commons.formatTime(sumWorkTime));
		
		System.out.println("残業時間");
		int sumOverworkTime = 0;
		for (TimePeriod period : actualOverworkTimesWithoutBreak) {
			System.out.println(period.format());
			sumOverworkTime += period.minutesOfLength();
		}
		System.out.println("合計: " + Commons.formatTime(sumOverworkTime));
		
		System.out.println("休憩時間");
		int sumBreakTime = 0;
		for (TimePeriod period : actualBreakTimes) {
			System.out.println(period.format());
			sumBreakTime += period.minutesOfLength();
		}
		System.out.println("合計: " + Commons.formatTime(sumBreakTime));
	}
}
