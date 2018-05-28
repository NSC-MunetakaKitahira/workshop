package apricot.dom;

/**
 * @author yuta_sano
 *	これは時間をイメージしたクラスです.
 */
public class Time{
	private static final String timeFormat = "%tH:%tM";
	private static final int hourDivide = 100;
	int timeOfInt;
	
	
	/**
	 * @param timeOfInt　整数形式の時間（e.g. 8:30 →　830）
	 */
	public Time(int timeOfInt) {
		this.timeOfInt = timeOfInt;
	}
	
	/**
	 * @param timeOfString 文字列形式時間（e.g. 8:30 → "8:30"）
	 */
	public Time(String timeOfString) {
		this(Integer.parseInt(timeOfString.replace(":", "")));
	}
	
	/**
	 * timeを整数形式で返します.
	 */
	public int toInt() {
		return timeOfInt;
	}
	
	/**
	 * timeを文字列形式で返します.
	 */
	@Override
	public String toString() {
		return Time.parseTimeFrom(timeOfInt);
	}
	
	/**
	 * 現在の時間:hoursを返します.
	 * @return timeの時間部分
	 */
	public int hours() {
		return timeOfInt / hourDivide;
	}
	/**
	 * 現在の分：minutesを返します.
	 * @return timeの分部分
	 */
	public int minutes() {
		return timeOfInt % hourDivide;
	}
	
	
	/**
	 * 時間の引き算（e.g 12:00-8:30 = 3:30）
	 * @param target
	 * @return 2つの時間の間に生じる
	 */
	public Time sub(Time target) {
		if(this.lt(target))
			return new Time(0);
		int hours = this.hours() - target.hours();
		int minutes = this.minutes() - target.minutes();
		if(minutes < 0) {
			hours -= 1;
			minutes = 60 - minutes;
		}
		return new Time(hours*hourDivide + minutes); 
	}
	/**
	 * @param target
	 * @return this == target
	 */
	public boolean eq(Time target) {
		return this.toInt() == target.toInt(); 
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
		return this.toInt() > target.toInt(); 
	}
	/**
	 * @param target
	 * @return this < target
	 */
	public boolean lt(Time target) {
		return this.toInt() < target.toInt(); 		
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
	 * @param timeOfString 文字列形式の時間（e.g. "8:30"）
	 * @return 整数形式の時間（e.g. 830）
	 */
	public static int parseTimeFrom(String timeOfString) {
		Time time = new Time(timeOfString);
		return time.hours() * hourDivide + time.minutes();
	}

	/**
	 * 整数形式の時間を文字列形式の時間に変換します.
	 * @param timeOfInt 整数形式の時間（e.g. 830）
	 * @return 文字列形式の時間（e.g. "8:30"）
	 */
	public static String parseTimeFrom(int timeOfInt) {
		Time time = new Time(timeOfInt);
		return String.format(timeFormat, time.hours(), time.minutes());
	}
}