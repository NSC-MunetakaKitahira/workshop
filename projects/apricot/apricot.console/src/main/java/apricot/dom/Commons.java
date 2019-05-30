package apricot.dom;

public class Commons {

	public static int parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}
	
	/**
	 * start1/end1とstart2/end2の重複範囲を配列で返す。
	 * 結果配列は、index:0が開始、index:1が終了。
	 * 重複していない場合は、空の配列を返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static TimePeriod getDuplication(TimePeriod Period1, TimePeriod Period2) {

		// <--->
		//       <--->
		if (Period1.end < Period2.start) {
			return null;
		}
		
		//       <--->
		// <--->
		if (Period2.end < Period1.start) {
			return null;
		}
		
		// <--------->
		//   <---->
		if (Period1.start <= Period2.start && Period2.end <= Period1.end) {
			return Period2;
		}

		//   <---->
		// <--------->
		if (Period2.start <= Period1.start && Period1.end <= Period2.end) {
			return Period1;
		}
		
		// <------>
		//    <------>
		if (Period1.start <= Period2.start && Period1.end <= Period2.end) {
			return new TimePeriod(Period2.start,Period1.end);
		}

		//    <------>
		// <------>
		if (Period2.start <= Period1.start && Period2.end <= Period1.end) {
			return new TimePeriod(Period1.start,Period2.end);
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	/**
	 * start1/end1から、start2/end2の範囲を除外した範囲を配列で返す。
	 * 結果配列は、index:0が開始、index:1が終了。
	 * start1/end1の範囲が2つに分断される場合、index:2が2つ目の範囲の開始、index:3がその終了。
	 * start1/end1の範囲が完全に除外される場合、空の配列を返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static TimePeriod[] getSubtraction(TimePeriod Period1, TimePeriod Period2) {

		// <--->
		//       <--->
		if (Period1.end < Period2.start) {
			return null;
		}
		
		//       <--->
		// <--->
		if (Period2.end < Period1.start) {
			return null;
		}
		
		// <--------->
		// <---->
		if (Period1.start == Period2.start && Period2.end < Period1.end) {
			return new TimePeriod[] {new TimePeriod(Period2.end,Period1.end)};
		}

		// <--------->
		//      <---->
		if (Period1.start < Period2.start && Period1.end == Period2.end) {
			return new TimePeriod[] {new TimePeriod(Period1.start,Period2.start)};
		}
		
		// <--------->
		//   <---->
		if (Period1.start < Period2.start && Period2.end < Period1.end) {
			return new TimePeriod[] {new TimePeriod(Period1.start, Period2.start),new TimePeriod(Period2.end,Period1.end)};
		}

		//   <---->
		// <--------->
		if (Period2.start <= Period1.start && Period1.end <= Period2.end) {
			return null;
		}
		
		// <------>
		//    <------>
		if (Period1.start <= Period2.start && Period1.end <= Period2.end) {
			return new TimePeriod[] {new TimePeriod(Period1.start,Period2.start)};
		}

		//    <------>
		// <------>
		if (Period2.start <= Period1.start && Period2.end <= Period1.end) {
			return new TimePeriod[] {new TimePeriod(Period2.end,Period1.end)};
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
