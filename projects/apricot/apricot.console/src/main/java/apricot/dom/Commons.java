package apricot.dom;

public class Commons {
	
	/**
	 * period1から、period2の範囲を除外した範囲をリストで返す。
	 * start1/end1の範囲が完全に除外される場合、空のリストを返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
