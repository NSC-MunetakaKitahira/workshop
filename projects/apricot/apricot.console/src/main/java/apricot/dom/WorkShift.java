package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	/**
	 * 始業時刻
	 */
	private int workStart;
	
	/**
	 * 終業時刻（定時）
	 */
	private int workEnd;
	
	/**
	 * 残業の開始時刻のリスト
	 */
	private List<Integer> overtimeStarts;
	
	/**
	 * 残業の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */
	private List<Integer> overtimeEnds;
	
	/**
	 * 休憩の開始時刻のリスト
	 */
	private List<Integer> breakStarts;
	
	/**
	 * 休憩の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */
	private List<Integer> breakEnds;
	
	public int getWorkStart() {
		return workStart;
	}
	
	public void setWorkStart(int workStart) {
		this.workStart = workStart;
	}
	
	public int getWorkEnd() {
		return workEnd;
	}
	
	public void setWorkEnd(int workEnd) {
		this.workEnd = workEnd;
	}
	
	public List<Integer> getOvertimeStarts() {
		return overtimeStarts;
	}
	
	public void setOvertimeStarts(List<Integer> overtimeStarts) {
		this.overtimeStarts = overtimeStarts;
	}
	
	public List<Integer> getOvertimeEnds() {
		return overtimeEnds;
	}
	
	public void setOvertimeEnds(List<Integer> overtimeEnds) {
		this.overtimeEnds = overtimeEnds;
	}
	
	public List<Integer> getBreakStarts() {
		return breakStarts;
	}
	
	public void setBreakStarts(List<Integer> breakStarts) {
		this.breakStarts = breakStarts;
	}
	public List<Integer> getBreakEnds() {
		return breakEnds;
	}
	
	public void setBreakEnds(List<Integer> breakEnds) {
		this.breakEnds = breakEnds;
	}
}
