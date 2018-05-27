package apricot.dom;

public class Commons {

	public static TimeOfDay parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return new TimeOfDay(hours, minutes);
	}

	/**
	 * start1/end1とstart2/end2の重複範囲を配列で返す。
	 * 重複していない場合は、空の配列を返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return 重複範囲の時間帯配列
	 */
	public static PeriodOfTime[] getDuplication(TimeOfDay start1, TimeOfDay end1, TimeOfDay start2, TimeOfDay end2) {

		// <--->
		//       <--->
		if (end1.value() < start2.value()) {
			return new PeriodOfTime[] {};
		}

		//       <--->
		// <--->
		if (end2.value() < start1.value()) {
			return new PeriodOfTime[] {};
		}

		// <--------->
		//   <---->
		if (start1.value() <= start2.value() && end2.value() <= end1.value()) {
			return new PeriodOfTime[] {new PeriodOfTime(start2, end2)};
		}

		//   <---->
		// <--------->
		if (start2.value() <= start1.value() && end1.value() <= end2.value()) {
			return new PeriodOfTime[] {new PeriodOfTime(start1, end1)};
		}

		// <------>
		//    <------>
		if (start1.value() <= start2.value() && end1.value() <= end2.value()) {
			return new PeriodOfTime[] {new PeriodOfTime(start2, end1)};
		}

		//    <------>
		// <------>
		if (start2.value() <= start1.value() && end2.value() <= end1.value()) {
			return new PeriodOfTime[] {new PeriodOfTime(start1,  end2)};
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	/**
	 * start1/end1から、start2/end2の範囲を除外した範囲を配列で返す。
	 * start1/end1の範囲が完全に除外される場合、空の配列を返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static PeriodOfTime[] getSubtraction(TimeOfDay start1, TimeOfDay end1, TimeOfDay start2, TimeOfDay end2) {

		// <--->
		//       <--->
		if (end1.value() < start2.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(start1, end1) };
		}

		//       <--->
		// <--->
		if (end2.value() < start1.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(start1, end1) };
		}

		// <--------->
		// <---->
		if (start1.value() == start2.value() && end2.value() < end1.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(end2, end1) };
		}

		// <--------->
		//      <---->
		if (start1.value() < start2.value() && end1.value() == end2.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(start1, start2) };
		}

		// <--------->
		//   <---->
		if (start1.value() < start2.value() && end2.value() < end1.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(start1, start2), new PeriodOfTime(end2, end1) };
		}

		//   <---->
		// <--------->
		if (start2.value() <= start1.value() && end1.value() <= end2.value()) {
			return new PeriodOfTime[] { };
		}

		// <------>
		//    <------>
		if (start1.value() <= start2.value() && end1.value() <= end2.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(start1, start2) };
		}

		//    <------>
		// <------>
		if (start2.value() <= start1.value() && end2.value() <= end1.value()) {
			return new PeriodOfTime[] { new PeriodOfTime(end2, end1) };
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
