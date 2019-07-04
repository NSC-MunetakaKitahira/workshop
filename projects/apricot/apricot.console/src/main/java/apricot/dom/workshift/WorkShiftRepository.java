package apricot.dom.workshift;

import java.util.Arrays;
import java.util.List;

import apricot.dom.time.TimePeriod;

public class WorkShiftRepository {

	public static WorkShift get() {
		
		List<TimePeriod> normalWorks = Arrays.asList(
				new TimePeriod(8 * 60 + 30, 12 * 60),
				new TimePeriod(13 * 60, 17 * 60 + 30));
		
		List<TimePeriod> overtimeWorks = Arrays.asList(
				new TimePeriod(18 * 60, 22 * 60),
				new TimePeriod(22 * 60, 24 * 60));
		
		WorkShift workShift = new WorkShift(normalWorks, overtimeWorks);
		
		return workShift;
	}
}
