package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {
	// 仕事時間
	public TimePeriod workTimePeriod;

	// 残業時間
	public List<TimePeriod> overTimePeriod;

	// 休憩時間
	private List<TimePeriod> breakTimePeriod;

	public TimePeriod getWork() {
		return workTimePeriod;
	}

	public void setWork(TimePeriod workStart) {
		this.workTimePeriod = workStart;
	}

	public List<TimePeriod> getOvertime() {
		return overTimePeriod;
	}

	public void setOvertime(List<TimePeriod> overtimeStarts) {
		this.overTimePeriod = overtimeStarts;
	}

	public List<TimePeriod> getBreak() {
		return breakTimePeriod;
	}

	public void setBreak(List<TimePeriod> breakStarts) {
		this.breakTimePeriod = breakStarts;
	}

}
