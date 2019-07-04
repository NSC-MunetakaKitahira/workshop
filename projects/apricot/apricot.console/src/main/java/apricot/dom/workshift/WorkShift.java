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

	public List<TimePeriod> getNormalWorks() {
		return normalWorks;
	}

	public List<TimePeriod> getOverworks() {
		return overtimeWorks;
	}
}
