package apricot.dom;

import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {

	private TimePeriod        workTime;
	private List<TimePeriod>  overTime;
	private List<TimePeriod> breakTime;


	public TimePeriod       getWork()  {return workTime; }
	public List<TimePeriod> getOver()  {return overTime; }
	public List<TimePeriod> getBreak() {return breakTime;}


	public void setWork (TimePeriod       in_workTime ) {this.workTime  = in_workTime; }
	public void setOver (List<TimePeriod> in_overTime ) {this.overTime  = in_overTime; }
	public void setBreak(List<TimePeriod> in_breakTime) {this.breakTime = in_breakTime;}

	//リフレクション使えばもっと行数圧縮できる気がするけど詳しくない、誰か頼んだ
}
