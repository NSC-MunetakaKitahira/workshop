package apricot.dom;

public class Commons {

	/**
	 * 本来はこのメソッドもCommonsから無くしたいが、「時刻」ではなく「時間」の処理であるため、TimeOfDayとは別のクラスが必要。
	 * しかし、そこまでやるには「時間」に関する仕様情報が現状足りていないので、ひとまずただの共通関数としておく。
	 */
	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
