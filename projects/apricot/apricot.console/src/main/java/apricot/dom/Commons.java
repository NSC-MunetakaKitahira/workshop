package apricot.dom;

//import java.util.List;

public class Commons {

	public static int parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}

	/**
	 * start1/end1とstart2/end2の重複範囲を配列で返す。 結果配列は、index:0が開始、index:1が終了。
	 * 重複していない場合は、空の配列を返す。
	 * 
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static TimePeriod getDuplication(TimePeriod period1, TimePeriod period2) {
		// return null;

		// <--->
		// <--->
		if (period1.end < period2.start) {
			return null;
		}

		// <--->
		// <--->
		if (period2.end < period1.start) {
			return null;
		}

		// <--------->
		// <---->
		if (period1.start <= period2.start && period2.end <= period1.end) {
			return period2;
		}

		// <---->
		// <--------->
		if (period2.start <= period1.start && period1.end <= period2.end) {
			return period1;
		}

		// <------>
		// <------>
		if (period1.start <= period2.start && period1.end <= period2.end) {
			return new TimePeriod(period2.start, period1.end);
		}

		// <------>
		// <------>
		if (period2.start <= period1.start && period2.end <= period1.end) {
			return new TimePeriod(period1.start, period2.end);
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	/**
	 * start1/end1から、start2/end2の範囲を除外した範囲を配列で返す。 結果配列は、index:0が開始、index:1が終了。
	 * start1/end1の範囲が2つに分断される場合、index:2が2つ目の範囲の開始、index:3がその終了。
	 * start1/end1の範囲が完全に除外される場合、空の配列を返す。
	 * 
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static TimePeriod[] getSubtraction(TimePeriod period1, TimePeriod period2) {

		// <--->
		// <--->
		if (period1.end < period2.start) {
			return new TimePeriod[] { new TimePeriod(period1.start, period1.end) };
		}

		// <--->
		// <--->
		if (period2.end < period1.start) {
			return new TimePeriod[] { new TimePeriod(period1.start, period1.end) };
		}

		// <--------->
		// <---->
		if (period1.start == period2.start && period2.end < period1.end) {
			return new TimePeriod[] { new TimePeriod(period2.end, period1.end) };
		}

		// <--------->
		// <---->
		if (period1.start < period2.start && period1.end == period2.end) {
			return new TimePeriod[] { new TimePeriod(period1.start, period2.start) };
		}

		// <--------->
		// <---->
		if (period1.start < period2.start && period2.end < period1.end) {
			return new TimePeriod[] { new TimePeriod(period1.start, period2.start),
					new TimePeriod(period2.end, period1.end) };
		}

		// <---->
		// <--------->
		if (period2.start <= period1.start && period1.end <= period2.end) {
			return null;
		}

		// <------>
		// <------>
		if (period1.start <= period2.start && period1.end <= period2.end) {
			return new TimePeriod[] { new TimePeriod(period1.start, period2.start) };
		}

		// <------>
		// <------>
		if (period2.start <= period1.start && period2.end <= period1.end) {
			return new TimePeriod[] { new TimePeriod(period2.end, period1.end) };
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
