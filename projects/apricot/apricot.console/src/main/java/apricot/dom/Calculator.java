package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
	
	public static TimeOfWork calculate(TimePeriod parseTime, WorkShift workShift) {
		
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = parseTime.getDuplication(workShift.worktime);
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = actualTimeDuplication(parseTime, workShift.overtimes);
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = actualTimeDuplication(parseTime, workShift.breaktimes);
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = actualTimesWithoutBreak(actualWorkTime, actualBreakTimes);
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak = actualTimesWithoutBreak(actualOverworkTimes, actualBreakTimes);
		
		//就業時間、残業時間、休憩時間の合計を求める
		return new TimeOfWork(actualWorkTimesWithoutBreak, actualOverworkTimesWithoutBreak, actualBreakTimes);
	}
	
	// 実残業時間帯: 出勤～退勤と、各時間帯との重複
	private static List<TimePeriod> actualTimeDuplication(TimePeriod parseTime, List<TimePeriod> times) {
		List<TimePeriod> actualTimes = new ArrayList<>();
		for (TimePeriod time : times) {
			TimePeriod duplication = parseTime.getDuplication(time);
			if (duplication == null) continue;

			actualTimes.add(duplication);
		}

		return actualTimes;
	}
	
	//実休憩時間帯に重複している部分を除外
	private static List<TimePeriod> actualTimesWithoutBreak(TimePeriod actualTime, List<TimePeriod> actualBreakTimes){
		List<TimePeriod> actualTimesWithoutBreak = new ArrayList<>();
		if(actualTime == null) return actualTimesWithoutBreak;
		if(actualBreakTimes.size() == 0) {
			actualTimesWithoutBreak.add(actualTime);
			return actualTimesWithoutBreak;
		}
		for (TimePeriod actualBreakTime : actualBreakTimes) {
			TimePeriod[] subtraction = actualTime.getSubtraction(actualBreakTime);
			if (subtraction.length == 1) {
				actualTimesWithoutBreak.add(subtraction[0]);
				break;
			} else if (subtraction.length == 2) {
				actualTimesWithoutBreak.add(subtraction[0]);
				actualTimesWithoutBreak.add(subtraction[1]);
				break;
			}
		}
		return actualTimesWithoutBreak;
	}
	
	private static List<TimePeriod> actualTimesWithoutBreak(List<TimePeriod> actualTimes, List<TimePeriod> actualBreakTimes){
		List<TimePeriod> actualTimesWithoutBreak = new ArrayList<>();
		for (TimePeriod actualTime : actualTimes) {
			TimePeriod[] subtraction = new TimePeriod[] {};
			if(actualBreakTimes.size() == 0) {
				actualTimesWithoutBreak.add(actualTime);
				continue;
			}
			for (TimePeriod actualBreakTime : actualBreakTimes) {
				subtraction = actualTime.getSubtraction(actualBreakTime);
			}
			if (subtraction.length == 1) {
				actualTimesWithoutBreak.add(subtraction[0]);
			} else if (subtraction.length == 2) {
				actualTimesWithoutBreak.add(subtraction[0]);
				actualTimesWithoutBreak.add(subtraction[1]);
			}
		}
		return actualTimesWithoutBreak;
	}
}
