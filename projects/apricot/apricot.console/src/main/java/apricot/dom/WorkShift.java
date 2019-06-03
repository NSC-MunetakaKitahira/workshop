package apricot.dom;

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

	public CalculationResult calculate(TimePeriod timeStamp) {

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = timeStamp.getDuplicationWith(workTime);
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = timeStamp.getDuplicationWith(overworkTimes);
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = timeStamp.getDuplicationWith(breakTimes);
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = excludeBreakTimes(
				actualWorkTime, actualBreakTimes);
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak = excludeBreakTimesFromOverworkTimes(
				actualOverworkTimes, actualBreakTimes);
		
		return new CalculationResult(
				actualWorkTimesWithoutBreak,
				actualOverworkTimesWithoutBreak,
				actualBreakTimes);
	}

	private static List<TimePeriod> excludeBreakTimes(
			TimePeriod actualWorkTime,
			List<TimePeriod> actualBreakTimes) {
		
		if (actualBreakTimes.isEmpty()) {
			return Arrays.asList(actualWorkTime);
		}
		
		return actualWorkTime.subtract(actualBreakTimes);
	}

	private static List<TimePeriod> excludeBreakTimesFromOverworkTimes(
			List<TimePeriod> actualOverworkTimes,
			List<TimePeriod> actualBreakTimes) {
		
		if (actualBreakTimes.isEmpty()) {
			return actualOverworkTimes;
		}
		
		return actualOverworkTimes.stream()
				.flatMap(overwork -> overwork.subtract(actualBreakTimes).stream())
				.collect(Collectors.toList());
	}
}
