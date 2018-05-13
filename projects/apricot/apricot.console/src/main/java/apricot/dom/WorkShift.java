package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	/**
	 * 始業時刻
	 */
	private TimeOfDay workStart;
	
	/**
	 * 終業時刻（定時）
	 */
	private TimeOfDay workEnd;
	
	/**
	 * 残業の開始時刻のリスト
	 */
	private List<TimeOfDay> overtimeStarts;
	
	/**
	 * 残業の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */
	private List<TimeOfDay> overtimeEnds;
	
	/**
	 * 休憩の開始時刻のリスト
	 */
	private List<TimeOfDay> breakStarts;
	
	/**
	 * 休憩の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */
	private List<TimeOfDay> breakEnds;
	
	public TimeOfDay getWorkStart() {
		return workStart;
	}
	
	public void setWorkStart(TimeOfDay workStart) {
		this.workStart = workStart;
	}
	
	public TimeOfDay getWorkEnd() {
		return workEnd;
	}
	
	public void setWorkEnd(TimeOfDay workEnd) {
		this.workEnd = workEnd;
	}
	
	public List<TimeOfDay> getOvertimeStarts() {
		return overtimeStarts;
	}
	
	public void setOvertimeStarts(List<TimeOfDay> overtimeStarts) {
		this.overtimeStarts = overtimeStarts;
	}
	
	public List<TimeOfDay> getOvertimeEnds() {
		return overtimeEnds;
	}
	
	public void setOvertimeEnds(List<TimeOfDay> overtimeEnds) {
		this.overtimeEnds = overtimeEnds;
	}
	
	public List<TimeOfDay> getBreakStarts() {
		return breakStarts;
	}
	
	public void setBreakStarts(List<TimeOfDay> breakStarts) {
		this.breakStarts = breakStarts;
	}
	public List<TimeOfDay> getBreakEnds() {
		return breakEnds;
	}
	
	public void setBreakEnds(List<TimeOfDay> breakEnds) {
		this.breakEnds = breakEnds;
	}
}
