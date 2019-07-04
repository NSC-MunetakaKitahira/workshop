package apricot.dom.record;

import apricot.dom.time.Minutes;
import apricot.dom.time.TimePeriods;

/**
 * 出退勤時刻に基づく実際の勤務実績
 */
public class WorkRecord {
	
	/** 就業時間 */
	public final TimePeriods workTimes;
	
	/** 残業時間 */
	public final TimePeriods overworkTimes;
	
	/** 休憩時間 */
	public final TimePeriods breakTimes;
	
	public WorkRecord(
			TimePeriods workTimes,
			TimePeriods overworkTimes,
			TimePeriods breakTimes) {
		
		this.workTimes = workTimes;
		this.overworkTimes = overworkTimes;
		this.breakTimes = breakTimes;
	}
	
	public Minutes sumWorkTime() {
		return workTimes.sumMinutesOfLength();
	}
	
	public Minutes sumOverworkTime() {
		return overworkTimes.sumMinutesOfLength();
	}
	
	public Minutes sumBreakTime() {
		return breakTimes.sumMinutesOfLength();
	}
}
