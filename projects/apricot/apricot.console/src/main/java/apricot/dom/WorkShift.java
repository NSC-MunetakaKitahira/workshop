package apricot.dom;

import java.util.ArrayList;
import java.util.List;

/**就業時間帯の管理
 * @author yuta_sano
 *
 */
public class WorkShift {
	/**
	 * 勤務時間
	 */
	private TimePeriod worktime;
	/**
	 * 残業時間
	 */
	private List<TimePeriod> overtimeList = new ArrayList<TimePeriod>();
	/**
	 * 休憩時間
	 */
	private List<TimePeriod> breaktimeList = new ArrayList<TimePeriod>();

	public void setWorktime(String startTimeOfString, String endTimeOfString) {
		this.worktime = new TimePeriod(startTimeOfString, endTimeOfString);
	}

	public void addOvertime(String startTimeOfString, String endTimeOfString) {
		this.overtimeList.add(new TimePeriod(startTimeOfString, endTimeOfString));
	}

	public void addBreakime(String startTimeOfString, String endTimeOfString) {
		this.breaktimeList.add(new TimePeriod(startTimeOfString, endTimeOfString));
	}
	
	public TimePeriod getActualWorktimeFrom(TimePeriod target) {
		return target.getDuplicationWith(this.worktime);
	}
	public List<TimePeriod> getActualOvertimeFrom(TimePeriod target){
		return target.getDuplicationWith(this.overtimeList);
	}
	public List<TimePeriod> getActualBreaktimeFrom(TimePeriod target){
		return target.getDuplicationWith(this.breaktimeList);
	}
	
}
