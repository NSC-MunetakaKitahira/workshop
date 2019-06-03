package apricot.console.dom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import apricot.dom.TimeOfDay;
import apricot.dom.TimePeriod;

class TimePeriodTest {
	
	@Test
	void sample() {
		
		TimePeriod base = pr(1.00, 2.00);
		TimePeriod actual = base.getDuplicationWith(pr(1.30, 2.30));
		TimePeriod expected = pr(1.30, 2.00);
		
		// assertEqualsに渡すクラスは、equalsメソッドをきちんと実装しておく必要がある
		assertEquals(expected, actual, "sample!!");
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
			TimePeriod actual = base.getDuplicationWith(testCase[0]);
			assertEquals(actual, testCase[1], "case: " + testCase[0].toString());
		}
		
	}
	
	@Test
	void getDuplicationWithList() {

		TimePeriod base = pr(1.00, 10.00);
		List<TimePeriod> others = Arrays.asList(
				pr(0.00, 3.00),
				pr(5.00, 5.30),
				pr(7.00, 11.00),
				pr(14.00, 16.00));
		

		List<TimePeriod> actual = base.getDuplicationWith(others);

		assertEquals(3, actual.size());
		assertEquals(pr(1.00, 3.00), actual.get(0));
		assertEquals(pr(5.00, 5.30), actual.get(1));
		assertEquals(pr(7.00, 10.00), actual.get(2));
	}
	
	@Test
	void subtractList() {
		
		TimePeriod base = pr(1.00, 10.00);
		List<TimePeriod> subtractors = Arrays.asList(
				pr(2.00, 3.00),
				pr(5.00, 5.30),
				pr(7.00, 9.00));
		
		List<TimePeriod> actual = base.subtract(subtractors);

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
