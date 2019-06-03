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
    public TimePeriod  Period;
	
	private List<TimePeriod> overTimePeriod;
	public void setoverTimePeriod(List<Integer>  list) {
		//this.overtimePeriod = overtimePeriod;
	}
	
	public List<TimePeriod> getoverTimePeriod() {
		return overTimePeriod;
	}
	
	public static TimePeriod CalCorrection(TimePeriod Period) {
		return Period;
	}
	/**
	 * 残業の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */
	
	/**
	 * 休憩の開始時刻のリスト
	 */
	public  TimePeriod Break;
	
	private List<TimePeriod>  breakTimePeriod;
	public void setbreakTimePeriod(List<TimePeriod>  breakTimePeriod) {
		this.breakTimePeriod = breakTimePeriod;
	}
	
	public List<TimePeriod> getbreaktimePeriod() {
		return breakTimePeriod;
	
	}
	
	/**
	 * 休憩の終了時刻のリスト
	 * 開始時刻と同じインデックスの要素がペア
	 */

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
    
	public List<TimePeriod>getBreakEnds(){
		return null;
	}
	
	public void setOvertimeStarts(List<Integer> list) {
		
	}
	public void setOvertimeEnds(List<Integer> list) {
		
	}
    
	public List<TimePeriod> getOvertimestarts(){
		return null;
	}
	
	public static int[] getDuplication(Object timeStampStart,Object timeStampEnd,int workstart2,int workEnd2) {
		return null;
	}


	public void setBreakEnds(List<Integer> list) {
		
	}

}
	
	