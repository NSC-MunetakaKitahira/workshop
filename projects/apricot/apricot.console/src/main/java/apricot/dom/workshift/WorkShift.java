package apricot.dom.workshift;

import java.util.List;

import apricot.dom.time.TimePeriod;

public class WorkShift {

	private final List<TimePeriod> normalWorks;
	
	private final List<TimePeriod> overtimeWorks;

	public WorkShift(List<TimePeriod> normalWorks, List<TimePeriod> overtimeWorks) {
		this.normalWorks = normalWorks;
		this.overtimeWorks = overtimeWorks;
	}
	
	public List<TimePeriod> actualNormalWorks(TimePeriod timeStamp) {
		return timeStamp.getDuplications(normalWorks);
	}
	
	public List<TimePeriod> actualOvertimeWorks(TimePeriod timeStamp) {
		return timeStamp.getDuplications(overtimeWorks);
	}
}
