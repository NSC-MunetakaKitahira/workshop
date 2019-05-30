package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {
	// 仕事時間
	public TimePeriod workTimePeriod;
	/**
	 * 始業時刻
	 */
	// private int workStart;

	/**
	 * 終業時刻（定時）
	 */
	// private int workEnd;
	// 残業時間
	public List<TimePeriod> overTimePeriod;

	/**
	 * 残業の開始時刻のリスト
	 */
	// private List<Integer> overtimeStarts;

	/**
	 * 残業の終了時刻のリスト 開始時刻と同じインデックスの要素がペア
	 */
	// private List<Integer> overtimeEnds;

	// private List <TimePeriod> overTimePeriod;

	/**
	 * 休憩の開始時刻のリスト
	 */
	private List<TimePeriod> breakTimePeriod;
	// private List<Integer> breakStarts;

	/**
	 * 休憩の終了時刻のリスト 開始時刻と同じインデックスの要素がペア
	 */
	// private List<Integer> breakEnds;

	public TimePeriod getWork() {
		return workTimePeriod;
	}

	public void setWork(TimePeriod workStart) {
		this.workTimePeriod = workStart;
	}

//	public TimePeriod getWorkEnd() {
//		return workTimePeriod;
//	}

//	public void setWorkEnd(TimePeriod workEnd) {
//		this.workTimePeriod = workEnd;
//	}

//	public List<TimePeriod> getOvertimeStarts() {
//		return overTimePeriod;
//	}

	public void setOvertime(List<TimePeriod> overtimeStarts) {
		this.overTimePeriod = overtimeStarts;
	}

//	public List<TimePeriod> getOvertimeEnds() {
//		return overTimePeriod;
//	}

//	public void setOvertimeEnds(List<TimePeriod> overtimeEnds) {
//		this.overTimePeriod = overtimeEnds;
//	}

	public List<TimePeriod> getBreak() {
		return breakTimePeriod;
	}

	public void setBreak(List<TimePeriod> breakStarts) {
		this.breakTimePeriod = breakStarts;
	}

//	public List<TimePeriod> getBreakEnds() {
//		return breakTimePeriod;
//	}

//	public void setBreakA(List<TimePeriod> breakEnds) {
//		this.breakTimePeriod = breakEnds;
//	}

	public List<TimePeriod> getOvertime() {
		// TODO 自動生成されたメソッド・スタブ
		return overTimePeriod;
	}
}
