package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	/**
	 * 定時の勤務時間帯
	 */
	private final TimePeriod workTime;
	
	/**
	 * 残業時間帯のリスト
	 */
	private final List<TimePeriod> overworkTimes;
	
	/**
	 * 残業時間帯のリスト
	 */
	private final List<TimePeriod> breakTimes;
	
	public WorkShift(TimePeriod workTime, List<TimePeriod> overworkTimes, List<TimePeriod> breakTimes) {
		this.workTime = workTime;
		this.overworkTimes = overworkTimes;
		this.breakTimes = breakTimes;
	}

	public CalculateResult calculate(TimePeriod timeStamp) {

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = timeStamp.getDuplicationWith(workTime);
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = getDuplications(actualWorkTime, overworkTimes);
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = getDuplications(actualWorkTime, breakTimes);
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak;
		if (actualBreakTimes.isEmpty()) {
			actualWorkTimesWithoutBreak = Arrays.asList(actualWorkTime);
		} else {
			actualWorkTimesWithoutBreak = getSubtractions(actualWorkTime, actualBreakTimes);
		}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak;
		if (actualBreakTimes.isEmpty()) {
			actualOverworkTimesWithoutBreak = actualOverworkTimes;
		} else {
			actualOverworkTimesWithoutBreak = new ArrayList<>();
			for (TimePeriod overworkTime : actualOverworkTimes) {
				actualOverworkTimesWithoutBreak.addAll(
						getSubtractions(overworkTime, actualBreakTimes));
			}
		}
		
		return new CalculateResult(
				actualWorkTimesWithoutBreak,
				actualOverworkTimesWithoutBreak,
				actualBreakTimes);
	}
	
	private static List<TimePeriod> getDuplications(TimePeriod base, List<TimePeriod> list) {
		return list.stream()
				.map(p -> p.getDuplicationWith(base))
				.filter(dup -> dup != null)
				.collect(Collectors.toList());
	}
	
	private static List<TimePeriod> getSubtractions(TimePeriod base, List<TimePeriod> subtractors) {
		return subtractors.stream()
				.flatMap(sub -> base.subtract(sub).stream())
				.collect(Collectors.toList());
	}
	
	public static class CalculateResult {
		public final List<TimePeriod> workTimes;
		public final List<TimePeriod> overworkTimes;
		public final List<TimePeriod> breakTimes;
		
		public CalculateResult(
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
