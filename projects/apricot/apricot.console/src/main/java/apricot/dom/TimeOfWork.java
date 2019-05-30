package apricot.dom;

import java.util.List;

public class TimeOfWork {

	public final List<TimePeriod> actualWorkTimes;
	public final List<TimePeriod> actualOverworkTimes;
	public final List<TimePeriod> actualBreakTimes;
	
	public TimeOfWork(List<TimePeriod> actualWorkTimes, List<TimePeriod> actualOverworkTimes, List<TimePeriod> actualBreakTimes) {
		this.actualWorkTimes = actualWorkTimes;
		this.actualOverworkTimes = actualOverworkTimes;
		this.actualBreakTimes = actualBreakTimes;
	}
}
