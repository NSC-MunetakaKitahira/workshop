package apricot.dom.workshift;

import java.util.Arrays;

import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

public class WorkShiftRepository {

	public static WorkShift get() {
		
		TimePeriods normalWorks = new TimePeriods(Arrays.asList(
				new TimePeriod(8 * 60 + 30, 12 * 60),
				new TimePeriod(13 * 60, 17 * 60 + 30)));
		
		TimePeriods overtimeWorks = new TimePeriods(Arrays.asList(
				new TimePeriod(18 * 60, 22 * 60),
				new TimePeriod(22 * 60, 24 * 60)));
		
		WorkShift workShift = new WorkShift(normalWorks, overtimeWorks);
		
		return workShift;
	}
}
