package apricot.dom;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤務時間の計算結果
 */
public class CalculationResult {
	
	/** 就業時間 */
	public final List<TimePeriod> workTimes;
	
	/** 残業時間 */
	public final List<TimePeriod> overworkTimes;
	
	/** 休憩時間 */
	public final List<TimePeriod> breakTimes;
	
	public CalculationResult(
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
