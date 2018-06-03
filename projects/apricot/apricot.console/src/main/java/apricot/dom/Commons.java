package apricot.dom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Commons {

	public static TimeOfDay parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return new TimeOfDay(hours * 60 + minutes);
	}
	
	/**
	 * start1/end1とstart2/end2の重複範囲を返す。
	 * 重複していない場合は、nullを返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static TimePeriod getDuplication(TimePeriod period1, TimePeriod period2) {

		// <--->
		//       <--->
		if (period1.getEnd().lessThan(period2.getStart())) {
			return null;
		}
		
		//       <--->
		// <--->
		if (period2.getEnd().lessThan(period1.getStart())){
			return null;
		}
		
		// <--------->
		//   <---->
		if (period1.getStart().lessThanEqual(period2.getStart()) && period2.getEnd().lessThanEqual(period1.getEnd())) {
			return new TimePeriod(period2.getStart(), period2.getEnd());
		}

		//   <---->
		// <--------->
		if (period2.getStart().lessThanEqual(period1.getStart()) && period1.getEnd().lessThanEqual(period2.getEnd())) {
			return new TimePeriod(period1.getStart(), period1.getEnd());
		}
		
		// <------>
		//    <------>
		if (period1.getStart().lessThanEqual(period2.getStart()) && period1.getEnd().lessThanEqual(period2.getEnd())) {
			return new TimePeriod(period2.getStart(), period1.getEnd());
		}

		//    <------>
		// <------>
		if (period2.getStart().lessThanEqual(period1.getStart()) && period2.getEnd().lessThanEqual(period1.getEnd())) {
			return new TimePeriod(period1.getStart(),  period2.getEnd());
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	/**
	 * period1から、period2の範囲を除外した範囲をリストで返す。
	 * start1/end1の範囲が完全に除外される場合、空のリストを返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static List<TimePeriod> getSubtraction(TimePeriod period1, TimePeriod period2) {

		// <--->
		//       <--->
		if (period1.getEnd().lessThan(period2.getStart())) {
			return Arrays.asList(new TimePeriod(period1.getStart(), period1.getEnd()));
		}
		
		//       <--->
		// <--->
		if (period2.getEnd().lessThan(period1.getStart())) {
			return Arrays.asList(new TimePeriod(period1.getStart(), period1.getEnd()));
		}
		
		// <--------->
		// <---->
		if (period1.getStart().equals(period2.getStart()) && period2.getEnd().lessThan(period1.getEnd())) {
			return Arrays.asList(new TimePeriod(period2.getEnd(), period1.getEnd()));
		}

		// <--------->
		//      <---->
		if (period1.getStart().lessThan(period2.getStart()) && period1.getEnd().lessThan(period2.getEnd())) {
			return Arrays.asList(new TimePeriod(period1.getStart(), period2.getStart()));
		}
		
		// <--------->
		//   <---->
		if (period1.getStart().lessThan(period2.getStart()) && period2.getEnd().lessThan(period1.getEnd())) {
			return Arrays.asList(
					new TimePeriod(period1.getStart(), period2.getStart()),
					new TimePeriod(period2.getEnd(), period1.getEnd()));
		}

		//   <---->
		// <--------->
		if (period2.getStart().lessThanEqual(period1.getStart()) && period1.getEnd().lessThanEqual(period2.getEnd())) {
			return Collections.emptyList();
		}
		
		// <------>
		//    <------>
		if (period1.getStart().lessThanEqual(period2.getStart()) && period1.getEnd().lessThanEqual(period2.getEnd())) {
			return Arrays.asList(new TimePeriod(period1.getStart(), period2.getStart()));
		}

		//    <------>
		// <------>
		if (period2.getStart().lessThanEqual(period1.getStart()) && period2.getEnd().lessThanEqual(period1.getEnd())) {
			return Arrays.asList(new TimePeriod(period2.getEnd(),  period1.getEnd()));
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
