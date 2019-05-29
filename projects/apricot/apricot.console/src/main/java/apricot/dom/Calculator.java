package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calculator {

	public static Result calculate(TimePeriod timeStamp, WorkShift workShift) {

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
		
		return new Result(
				actualWorkTimesWithoutBreak,
				actualOverworkTimesWithoutBreak,
				actualBreakTimes);
		
	}
	
	public static class Result {
		public final List<TimePeriod> workTimes;
		public final List<TimePeriod> overworkTimes;
		public final List<TimePeriod> breakTimes;
		
		public Result(
				List<TimePeriod> workTimes,
				List<TimePeriod> overworkTimes,
				List<TimePeriod> breakTimes) {
			
			this.workTimes = workTimes;
			this.overworkTimes = overworkTimes;
			this.breakTimes = breakTimes;
		}
		
		public int sumWorkTime() {
			return workTimes.stream()
					.collect(Collectors.summingInt(p -> p.minutesOfLength()));
		}
		
		public int sumOverworkTime() {
			return overworkTimes.stream()
					.collect(Collectors.summingInt(p -> p.minutesOfLength()));
		}
		
		public int sumBreakTime() {
			return breakTimes.stream()
					.collect(Collectors.summingInt(p -> p.minutesOfLength()));
		}
	}
}
