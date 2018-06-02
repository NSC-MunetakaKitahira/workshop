package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	/**
	 * 定時の勤務時間帯
	 */
	private TimePeriod workTime;
	
	/**
	 * 残業時間帯のリスト
	 */
	private List<TimePeriod> overworkTimes;
	
	/**
	 * 残業時間帯のリスト
	 */
	private List<TimePeriod> breakTimes;

	public TimePeriod getWorkTime() {
		return workTime;
	}

	public void setWorkTime(TimePeriod workTime) {
		this.workTime = workTime;
	}

	public List<TimePeriod> getOverworkTimes() {
		return overworkTimes;
	}

	public void setOverworkTimes(List<TimePeriod> overworkTimes) {
		this.overworkTimes = overworkTimes;
	}

	public List<TimePeriod> getBreakTimes() {
		return breakTimes;
	}

	public void setBreakTimes(List<TimePeriod> breakTimes) {
		this.breakTimes = breakTimes;
	}
	
}
