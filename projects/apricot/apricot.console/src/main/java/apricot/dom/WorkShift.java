package apricot.dom;

import java.util.List;


/**
 * 就業時間帯
 */
public class WorkShift {
	
	/*
	/**
	 * 始業時刻
	*/	
	private int workStart;
	
	/**
	 * 終業時刻（定時）
	*/	
	private int workEnd;

	private TimePeriod workTimePeriod;
	
	
	/**
	 * 残業の開始時刻のリスト
	*/
	private List<Integer> overtimeStarts;
	
	/**
	 * 残業の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */
	private List<Integer> overtimeEnds;
	 
	private List<TimePeriod> overTimePeriod;

	/**
	 * 休憩の開始時刻のリスト
	*/
	private List<Integer> breakStarts;
	
	/**
	 * 休憩の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	*/
	private List<Integer> breakEnds;
	
	private List<TimePeriod> breakTimePeriod;
	

	
	
	public int getWorkStart() {
		return workStart;
	}

	public int getWorkEnd() {
		return workEnd;
	}

	public TimePeriod getWorkTimePeriod() {
		return workTimePeriod;
	}
		
	public void setWorkStart(int workStart) {
		this.workStart = workStart;
	}
		
	public void setWorkEnd(int workEnd) {
		this.workEnd = workEnd;
	}

	public void setWorkTimePeriod(TimePeriod workTimePeriod) {
		this.workTimePeriod = workTimePeriod;
	}

	public List<Integer> getOvertimeStarts() {
		return overtimeStarts;
	}

	public List<Integer> getOvertimeEnds() {
		return overtimeEnds;
	}
	
	public List<TimePeriod> getOverTimePeriod(){
		return overTimePeriod;
	}
		
	public void setOvertimeStarts(List<Integer> overtimeStarts) {
		this.overtimeStarts = overtimeStarts;
	}	
		
	public void setOvertimeEnds(List<Integer> overtimeEnds) {
		this.overtimeEnds = overtimeEnds;
	}

	public void setOverTimePeriod(List<TimePeriod> overTimePeriod) {
		this.overTimePeriod = overTimePeriod;
	}
	
	public List<Integer> getBreakStarts() {
		return breakStarts;
	}
	
	public List<Integer> getBreakEnds() {
		return breakEnds;
	}

	public List<TimePeriod> getBreakTimePeriod(){
		return breakTimePeriod;
	}
	
	public void setBreakStarts(List<Integer> breakStarts) {
		this.breakStarts = breakStarts;
	}

	public void setBreakEnds(List<Integer> breakEnds) {
		this.breakEnds = breakEnds;
	}	

	public void setBreakTimePeriod(List<TimePeriod> breakTimePeriod) {
		this.breakTimePeriod = breakTimePeriod;
	}

}
	


	

