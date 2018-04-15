package apricot.dom;

import java.util.List;

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
	public static int[] getDuplication(int start1, int end1, int start2, int end2) {

		// <--->
		//       <--->
		if (end1 < start2) {
			return new int[] {};
		}
		
		//       <--->
		// <--->
		if (end2 < start1) {
			return new int[] {};
		}
		
		// <--------->
		//   <---->
		if (start1 <= start2 && end2 <= end1) {
			return new int[] { start2, end2 };
		}

		//   <---->
		// <--------->
		if (start2 <= start1 && end1 <= end2) {
			return new int[] { start1, end1 };
		}
		
		// <------>
		//    <------>
		if (start1 <= start2 && end1 <= end2) {
			return new int[] { start2, end1 };
		}

		//    <------>
		// <------>
		if (start2 <= start1 && end2 <= end1) {
			return new int[] { start1,  end2 };
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
	public static int[] getSubtraction(int start1, int end1, int start2, int end2) {

		// <--->
		//       <--->
		if (end1 < start2) {
			return new int[] { start1, end1 };
		}
		
		//       <--->
		// <--->
		if (end2 < start1) {
			return new int[] { start1, end1 };
		}
		
		// <--------->
		// <---->
		if (start1 == start2 && end2 < end1) {
			return new int[] { end2, end1};
		}

		// <--------->
		//      <---->
		if (start1 < start2 && end1 == end2) {
			return new int[] { start1, start2 };
		}
		
		// <--------->
		//   <---->
		if (start1 < start2 && end2 < end1) {
			return new int[] { start1, start2, end2, end1 };
		}

		//   <---->
		// <--------->
		if (start2 <= start1 && end1 <= end2) {
			return new int[] { };
		}
		
		// <------>
		//    <------>
		if (start1 <= start2 && end1 <= end2) {
			return new int[] { start1, start2 };
		}

		//    <------>
		// <------>
		if (start2 <= start1 && end2 <= end1) {
			return new int[] { end2,  end1 };
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
