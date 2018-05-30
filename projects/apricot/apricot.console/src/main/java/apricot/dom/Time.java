package apricot.dom;

/**
 * @author yuta_sano これは時間をイメージしたクラスです.
 */
public class Time {
	private static final String timeFormat = "%02d:%02d";
	private static final int hourDivide = 100;
	private static final int minutesOfHour = 60;
	private int timeOfMinutes;

	/**
	 * @param timeOfMinutes
	 *          分形式の時間（e.g. 8:30 → 510）
	 */
	public Time(int timeOfMinutes) {
		this.timeOfMinutes = timeOfMinutes;
	}

	/**
	 * @param timeOfString
	 *          文字列形式時間（e.g. 8:30 → "8:30"）
	 */
	public Time(String timeOfString) {
		this(Time.parseTimeOfMinutesFrom(timeOfString));
	}

	/**
	 * timeの時間をすべて分に換算します.
	 */
	public int toMinutes() {
		return timeOfMinutes;
	}

	/**
	 * timeを文字列形式で返します.
	 */
	@Override
	public String toString() {
		return Time.parseTimeOfStringFrom(this.toMinutes());
	}

	/**
	 * 現在の時間:hoursを返します.
	 * 
	 * @return timeの時間部分
	 */
	public int hours() {
		return timeOfMinutes / minutesOfHour;
	}

	/**
	 * 現在の分：minutesを返します.
	 * 
	 * @return timeの分部分
	 */
	public int minutes() {
		return timeOfMinutes % minutesOfHour;
	}

	/**
	 * 時間の引き算（e.g 12:00-8:30 = 3:30）
	 * 
	 * @param target
	 * @return 2つの時間の間に生じる
	 */
	public Time sub(Time target) {
		int timeOfMinutes = this.toMinutes() - target.toMinutes();
		return new Time(Time.parseTimeOfStringFrom(timeOfMinutes));
	}

	/**
	 * @param target
	 * @return this == target
	 */
	public boolean eq(Time target) {
		return this.toMinutes() == target.toMinutes();
	}

	/**
	 * @param target
	 * @return this != target
	 */
	public boolean ne(Time target) {
		return !(this.eq(target));
	}

	/**
	 * @param target
	 * @return this > target
	 */
	public boolean gt(Time target) {
		return this.toMinutes() > target.toMinutes();
	}

	/**
	 * @param target
	 * @return this < target
	 */
	public boolean lt(Time target) {
		return this.toMinutes() < target.toMinutes();
	}

	/**
	 * @param target
	 * @return this >= target
	 */
	public boolean ge(Time target) {
		return this.gt(target) || this.eq(target);
	}

	/**
	 * @param target
	 * @return this <= target
	 */
	public boolean le(Time target) {
		return this.lt(target) || this.eq(target);
	}

	/**
	 * 文字列形式の時間を整数形式の時間に変換します.
	 * 
	 * @param timeOfString
	 *          文字列形式の時間（e.g. "8:30"）
	 * @return 整数形式の時間（e.g. 830）
	 */
	public static int parseTimeOfMinutesFrom(String timeOfString) {
		int timeOfInt = Integer.parseInt(timeOfString.replace(":", ""));
		return timeOfInt / hourDivide * minutesOfHour + timeOfInt % hourDivide;
	}

	/**
	 * 分形式の時間を文字列形式の時間に変換します.
	 * 
	 * @param timeOfMinutes
	 *          分形式の時間（e.g. 510）
	 * @return 文字列形式の時間（e.g. "8:30"）
	 */
	public static String parseTimeOfStringFrom(int timeOfMinutes) {
		return String.format(timeFormat, timeOfMinutes / minutesOfHour, timeOfMinutes % minutesOfHour);
	}
}