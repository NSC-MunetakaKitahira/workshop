package apricot.dom.workshift;

import java.util.Arrays;

import apricot.dom.time.TimeOfDay;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

public class WorkShiftRepository {

	public static WorkShift get() {
		
		TimePeriods normalWorks = new TimePeriods(Arrays.asList(
				new TimePeriod(new TimeOfDay(8, 30), new TimeOfDay(12, 0)),
				new TimePeriod(new TimeOfDay(13, 0), new TimeOfDay(17, 30))));
		
		TimePeriods overtimeWorks = new TimePeriods(Arrays.asList(
				new TimePeriod(new TimeOfDay(18, 0), new TimeOfDay(22, 0)),
				new TimePeriod(new TimeOfDay(22, 0), new TimeOfDay(24, 0))));
		
		WorkShift workShift = new WorkShift(normalWorks, overtimeWorks);
		
		return workShift;
	}
}
