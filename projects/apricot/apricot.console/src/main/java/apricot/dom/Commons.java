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
	 * period1とperiod2の重複範囲を配列で返す。
	 * 重複していない場合は、空の配列を返す。
	 * @param period1
	 * @param period2
	 * @return 重複範囲の時間帯配列
	 */
	public static PeriodOfTime[] getDuplication(PeriodOfTime period1, PeriodOfTime period2) {

		// <--->
		//       <--->
		if (period1.getEndTime().value() < period2.getStartTime().value()) {
			return new PeriodOfTime[] {};
		}

		//       <--->
		// <--->
		if (period2.getEndTime().value() < period1.getStartTime().value()) {
			return new PeriodOfTime[] {};
		}

		// <--------->
		//   <---->
		if (period1.getStartTime().value() <= period2.getStartTime().value() && period2.getEndTime().value() <= period1.getEndTime().value()) {
			return new PeriodOfTime[] {new PeriodOfTime(period2.getStartTime(), period2.getEndTime())};
		}

		//   <---->
		// <--------->
		if (period2.getStartTime().value() <= period1.getStartTime().value() && period1.getEndTime().value() <= period2.getEndTime().value()) {
			return new PeriodOfTime[] {period1};
		}

		// <------>
		//    <------>
		if (period1.getStartTime().value() <= period2.getStartTime().value() && period1.getEndTime().value() <= period2.getEndTime().value()) {
			return new PeriodOfTime[] {new PeriodOfTime(period2.getStartTime(), period1.getEndTime())};
		}

		//    <------>
		// <------>
		if (period2.getStartTime().value() <= period1.getStartTime().value() && period2.getEndTime().value() <= period1.getEndTime().value()) {
			return new PeriodOfTime[] {new PeriodOfTime(period1.getStartTime(), period2.getEndTime())};
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	/**
	 * period1から、period2の範囲を除外した範囲を配列で返す。
	 * period1の範囲が完全に除外される場合、空の配列を返す。
	 * @param period1
	 * @param period2
	 * @return
	 */
	public static PeriodOfTime[] getSubtraction(PeriodOfTime period1, PeriodOfTime period2) {

		// <--->
		//       <--->
		if (period1.getEndTime().value() < period2.getStartTime().value()) {
			return new PeriodOfTime[] { period1 };
		}

		//       <--->
		// <--->
		if (period2.getEndTime().value() < period1.getStartTime().value()) {
			return new PeriodOfTime[] { period1 };
		}

		// <--------->
		// <---->
		if (period1.getStartTime().value() == period2.getStartTime().value() && period2.getEndTime().value() < period1.getEndTime().value()) {
			return new PeriodOfTime[] { new PeriodOfTime(period2.getEndTime(), period1.getEndTime()) };
		}

		// <--------->
		//      <---->
		if (period1.getStartTime().value() < period2.getStartTime().value() && period1.getEndTime().value() == period2.getEndTime().value()) {
			return new PeriodOfTime[] { new PeriodOfTime(period1.getStartTime(), period2.getStartTime()) };
		}

		// <--------->
		//   <---->
		if (period1.getStartTime().value() < period2.getStartTime().value() && period2.getEndTime().value() < period1.getEndTime().value()) {
			return new PeriodOfTime[] { new PeriodOfTime(period1.getStartTime(), period2.getStartTime()), new PeriodOfTime(period2.getEndTime(), period1.getEndTime()) };
		}

		//   <---->
		// <--------->
		if (period2.getStartTime().value() <= period1.getStartTime().value() && period1.getEndTime().value() <= period2.getEndTime().value()) {
			return new PeriodOfTime[] { };
		}

		// <------>
		//    <------>
		if (period1.getStartTime().value() <= period2.getStartTime().value() && period1.getEndTime().value() <= period2.getEndTime().value()) {
			return new PeriodOfTime[] { new PeriodOfTime(period1.getStartTime(), period2.getStartTime()) };
		}

		//    <------>
		// <------>
		if (period2.getStartTime().value() <= period1.getStartTime().value() && period2.getEndTime().value() <= period1.getEndTime().value()) {
			return new PeriodOfTime[] { new PeriodOfTime(period2.getEndTime(), period1.getEndTime()) };
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
