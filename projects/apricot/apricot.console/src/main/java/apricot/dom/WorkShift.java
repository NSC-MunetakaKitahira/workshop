package apricot.dom;

import java.util.List;
import apricot.dom.TimePeriod;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	//始業・就業(定時)時刻
	private TimePeriod workTimes;
	//残業の始業・就業時刻リスト
	private List<TimePeriod> overTimes;
	//休憩の始業・就業時刻リスト
	private List<TimePeriod> breakTimes;
	

	public TimePeriod getWorkTimes(){
		return workTimes;
	}
	
	public void setWorkTimes(TimePeriod workTimes){
		this.workTimes = workTimes;
	}
	
	public List<TimePeriod> getOverTimes(){
		return overTimes;
	}
	public void setOverTimes(List<TimePeriod> overTimes){
		this.overTimes = overTimes;
	}
	
	public List<TimePeriod> getBreakTimes(){
		return breakTimes;
	}
	public void setBreakTimes(List<TimePeriod> breakTimes){
		this.breakTimes = breakTimes;
	}
}
