package apricot.dom.workshift;

import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

public class WorkShift {

	private final TimePeriods normalWorks;
	
	private final TimePeriods overtimeWorks;

	public WorkShift(TimePeriods normalWorks, TimePeriods overtimeWorks) {
		this.normalWorks = normalWorks;
		this.overtimeWorks = overtimeWorks;
	}
	
	public TimePeriods actualNormalWorks(TimePeriod timeStamp) {
		return normalWorks.getDuplications(timeStamp);
	}
	
	public TimePeriods actualOvertimeWorks(TimePeriod timeStamp) {
		return overtimeWorks.getDuplications(timeStamp);
	}
}
