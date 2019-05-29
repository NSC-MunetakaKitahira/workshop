package apricot.dom;

/**
 * 時刻（任意の一日内における0時からの経過時間）を表すクラス。 精度は、現在の仕様上、時と分まで。
 */
public class TimeOfDay {

	/** 0時からの経過時間(分) */
	private final int minutesFromZero;

	/**
	 * コンストラクタ
	 * 
	 * @param minutesFromZero 0時からの経過時間(分)
	 */
	public TimeOfDay(int minutesFromZero) {
		this.minutesFromZero = minutesFromZero;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param hourPart   時刻の「時」部分の値
	 * @param minutePart 時刻の「分」部分の値
	 */
	public TimeOfDay(int hourPart, int minutePart) {
		this(hourPart * 60 + minutePart);
	}

	public TimeOfDay(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		this.minutesFromZero = hours * 60 + minutes;
	}

	public boolean equals(TimeOfDay target) {
		return this.minutesFromZero == target.minutesFromZero;
	}

	public boolean isAfter(TimeOfDay target) {
		return this.minutesFromZero > target.minutesFromZero;
	}

	public boolean isAfterOrEqual(TimeOfDay target) {
		return this.minutesFromZero >= target.minutesFromZero;
	}

	public boolean isBefore(TimeOfDay target) {
		return this.minutesFromZero < target.minutesFromZero;
	}

	public boolean isBeforeOrEqual(TimeOfDay target) {
		return this.minutesFromZero <= target.minutesFromZero;
	}
	
	public int minutesFrom(TimeOfDay other) {
		return this.minutesFromZero - other.minutesFromZero;
	}

	/**
	 * 時刻の「時」部分の値を返す
	 * 
	 * @return 時刻の「時」部分の値
	 */
	private int hourPart() {
		return this.minutesFromZero / 60;
	}

	/**
	 * 時刻の「分」部分の値を返す
	 * 
	 * @return 時刻の「分」部分の値
	 */
	private int minutePart() {
		return this.minutesFromZero % 60;
	}

	public String format() {
		return String.format("%d:%02d", this.hourPart(), this.minutePart());
	}
	
	@Override
	public String toString() {
		return format();
	}
}
