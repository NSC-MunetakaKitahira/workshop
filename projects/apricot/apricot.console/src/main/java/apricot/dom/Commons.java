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
	 * 結果配列は、index:0が開始、index:1が終了。
	 * 重複していない場合は、空の配列を返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static TimeOfDay[] getDuplication(TimeOfDay start1, TimeOfDay end1, TimeOfDay start2, TimeOfDay end2) {

		// <--->
		//       <--->
		if (end1.value() < start2.value()) {
			return new TimeOfDay[] {};
		}

		//       <--->
		// <--->
		if (end2.value() < start1.value()) {
			return new TimeOfDay[] {};
		}

		// <--------->
		//   <---->
		if (start1.value() <= start2.value() && end2.value() <= end1.value()) {
			return new TimeOfDay[] { start2, end2 };
		}

		//   <---->
		// <--------->
		if (start2.value() <= start1.value() && end1.value() <= end2.value()) {
			return new TimeOfDay[] { start1, end1 };
		}

		// <------>
		//    <------>
		if (start1.value() <= start2.value() && end1.value() <= end2.value()) {
			return new TimeOfDay[] { start2, end1 };
		}

		//    <------>
		// <------>
		if (start2.value() <= start1.value() && end2.value() <= end1.value()) {
			return new TimeOfDay[] { start1,  end2 };
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
	public static TimeOfDay[] getSubtraction(TimeOfDay start1, TimeOfDay end1, TimeOfDay start2, TimeOfDay end2) {

		// <--->
		//       <--->
		if (end1.value() < start2.value()) {
			return new TimeOfDay[] { start1, end1 };
		}

		//       <--->
		// <--->
		if (end2.value() < start1.value()) {
			return new TimeOfDay[] { start1, end1 };
		}

		// <--------->
		// <---->
		if (start1.value() == start2.value() && end2.value() < end1.value()) {
			return new TimeOfDay[] { end2, end1};
		}

		// <--------->
		//      <---->
		if (start1.value() < start2.value() && end1.value() == end2.value()) {
			return new TimeOfDay[] { start1, start2 };
		}

		// <--------->
		//   <---->
		if (start1.value() < start2.value() && end2.value() < end1.value()) {
			return new TimeOfDay[] { start1, start2, end2, end1 };
		}

		//   <---->
		// <--------->
		if (start2.value() <= start1.value() && end1.value() <= end2.value()) {
			return new TimeOfDay[] { };
		}

		// <------>
		//    <------>
		if (start1.value() <= start2.value() && end1.value() <= end2.value()) {
			return new TimeOfDay[] { start1, start2 };
		}

		//    <------>
		// <------>
		if (start2.value() <= start1.value() && end2.value() <= end1.value()) {
			return new TimeOfDay[] { end2,  end1 };
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}

	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
