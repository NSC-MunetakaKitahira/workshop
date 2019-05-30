package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {

	/**
	 * 始業、終業時刻
	 */
	public final TimePeriod worktime;
	
	/**
	 * 残業の開始、終了時刻のリスト
	 */
	public final List<TimePeriod> overtimes;
	
	/**
	 * 休憩の開始、終了時刻のリスト
	 */
	public final List<TimePeriod> breaktimes;
	
	public WorkShift(TimePeriod worktime, List<TimePeriod> overtimes, List<TimePeriod> breaktimes) {
		this.worktime = worktime;
		this.overtimes = overtimes;
		this.breaktimes = breaktimes;
	}
	
}