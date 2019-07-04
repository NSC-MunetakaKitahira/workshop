package apricot.console.dom.time;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import apricot.dom.time.TimeOfDay;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

class TimePeriodsTest {

	@Test
	void test() {
		List<TimePeriod> list = Arrays.asList(pr(1,2), pr(3,4), pr(5,6), pr(7,8), pr(9,10));
		TimePeriods tp = list.stream().collect(TimePeriods.collector());
		tp.toString();
	}

	
	private static TimePeriod pr(double start, double end) {
		return new TimePeriod(hm(start), hm(end));
	}
	
	private static TimeOfDay hm(double time) {
		int hour = (int)time;
		int minute = (int) Math.round((time - hour)*100);
		return new TimeOfDay(hour, minute);
	}
}
