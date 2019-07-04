package apricot.console.dom.time;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import apricot.dom.time.TimeOfDay;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

class TimePeriodTest {
	
	@Test
	void sample() {
		
		TimePeriod base = pr(1.00, 2.00);
		Optional<TimePeriod> actual = base.getDuplicationWith(pr(1.30, 2.30));
		Optional<TimePeriod> expected = Optional.of(pr(1.30, 2.00));
		
		// assertEqualsに渡すクラスは、equalsメソッドをきちんと実装しておく必要がある
		assertEquals(expected, actual, "sample!!");
	}
	
	@Test
	void containsTimeOfDay() {
		TimePeriod target = pr(1.00, 2.00);

		assertEquals(false, target.contains(hm(0.59)), "0:59");
		assertEquals(true, target.contains(hm(1.00)), "1:00");
		assertEquals(true, target.contains(hm(2.00)), "2:00");
		assertEquals(false, target.contains(hm(2.01)), "2:01");
	}
	
	@Test
	void containsTimePeriod() {
		TimePeriod target = pr(1.00, 2.00);

		assertEquals(false, target.contains(pr(0.58, 0.59)), "0:58-0:59");
		assertEquals(false, target.contains(pr(0.58, 1.00)), "0:58-1:00");
		assertEquals(false, target.contains(pr(0.58, 1.01)), "0:58-1:01");
		assertEquals(true, target.contains(pr(1.00, 1.01)), "1:00-1:01");
		assertEquals(true, target.contains(pr(1.01, 1.02)), "1:01-1:02");
		assertEquals(true, target.contains(pr(1.58, 1.59)), "1:58-1:59");
		assertEquals(true, target.contains(pr(1.58, 2.00)), "1:58-2:00");
		assertEquals(false, target.contains(pr(1.58, 2.01)), "1:58-2:01");
		assertEquals(false, target.contains(pr(2.00, 2.01)), "2:00-2:01");
		assertEquals(false, target.contains(pr(2.01, 2.02)), "2:01-2:02");
		assertEquals(true, target.contains(pr(1.00, 2.00)), "1:00-2:00");
		assertEquals(false, target.contains(pr(0.59, 2.01)), "0:59-2:01");
	}

	@Test
	void getDuplicationWith() {
		
		TimePeriod base = pr(1.00, 2.00);
		
		List<TimePeriod[]> testCases = Arrays.asList(
				// { パラメータ, 期待結果 }
				new TimePeriod [] { pr(0.00, 1.00), null },
				new TimePeriod [] { pr(2.00, 3.00), null },
				new TimePeriod [] { pr(0.59, 2.01), pr(1.00, 2.00) },
				new TimePeriod [] { pr(1.01, 1.59), pr(1.01, 1.59) },
				new TimePeriod [] { pr(0.30, 1.30), pr(1.00, 1.30) },
				new TimePeriod [] { pr(1.30, 2.30), pr(1.30, 2.00) }
				);
		
		batchTestGetDuplicationWith(base, testCases);
	}
	
	private static void batchTestGetDuplicationWith(TimePeriod base, List<TimePeriod[]> testCases) {
		
		for (TimePeriod[] testCase : testCases) {
			Optional<TimePeriod> actual = base.getDuplicationWith(testCase[0]);
			assertEquals(Optional.ofNullable(testCase[1]), actual, "case: " + testCase[0].toString());
		}
		
	}
	
	@Test
	void getDuplicationWithList() {

		TimePeriod base = pr(1.00, 10.00);
		TimePeriods others = TimePeriods.of(
				pr(0.00, 3.00),
				pr(5.00, 5.30),
				pr(7.00, 11.00),
				pr(14.00, 16.00));
		

		TimePeriods actual = base.getDuplicationWith(others);

		assertEquals(3, actual.size());
		assertEquals(pr(1.00, 3.00), actual.get(0));
		assertEquals(pr(5.00, 5.30), actual.get(1));
		assertEquals(pr(7.00, 10.00), actual.get(2));
	}
	
	@Test
	void subtractList() {
		
		TimePeriod base = pr(1.00, 10.00);
		TimePeriods subtractors = TimePeriods.of(
				pr(2.00, 3.00),
				pr(5.00, 5.30),
				pr(7.00, 9.00));
		
		TimePeriods actual = base.subtract(subtractors);

		assertEquals(4, actual.size());
		assertEquals(pr(1.00, 2.00), actual.get(0));
		assertEquals(pr(3.00, 5.00), actual.get(1));
		assertEquals(pr(5.30, 7.00), actual.get(2));
		assertEquals(pr(9.00, 10.00), actual.get(3));
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
