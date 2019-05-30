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
	
	private List<TimePeriod> overtimePeriod;
	public void setovertimePeriod(List<TimePeriod>  overtimePeriod) {
		this.overtimePeriod = overtimePeriod;
	}
	
	public List<TimePeriod> getovertimePeriod() {
		return overtimePeriod;
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
	public void setbreaktimePeriod(List<TimePeriod>  breaktimePeriod) {
		this.breakTimePeriod = breaktimePeriod;
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
}
	
	