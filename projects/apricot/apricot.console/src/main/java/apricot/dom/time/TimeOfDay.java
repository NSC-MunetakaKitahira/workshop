package apricot.dom.time;

import java.util.Comparator;

/**
 * 時刻（任意の一日内における0時からの経過時間）を表すクラス。 精度は、現在の仕様上、時と分まで。
 */
public class TimeOfDay implements Comparable<TimeOfDay> {

	private final Minutes minutesFromZero;
	
	public TimeOfDay(Minutes minutesFromZero) {
		this.minutesFromZero = minutesFromZero;
	}
	
	/**
	 * コンストラクタ
	 * 
	 * @param value 0時からの経過時間(分)
	 */
	public TimeOfDay(int minutesFromZero) {
		this(new Minutes(minutesFromZero));
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
		this(new Minutes(timeString));
	}
	
	public static Comparator<TimeOfDay> comparator(boolean isNaturalOrder) {
		return (a, b) -> a.minutesFromZero.compareTo(b.minutesFromZero);
	}
	
	public boolean isAfter(TimeOfDay target) {
		return this.minutesFromZero.compareTo(target.minutesFromZero) > 0;
	}

	public boolean isAfterOrEqual(TimeOfDay target) {
		return this.equals(target) || this.isAfter(target);
	}

	public boolean isBefore(TimeOfDay target) {
		return this.minutesFromZero.compareTo(target.minutesFromZero) < 0;
	}

	public boolean isBeforeOrEqual(TimeOfDay target) {
		return this.equals(target) || this.isBefore(target);
	}
	
	public boolean equals(TimeOfDay target) {
		return target != null && this.minutesFromZero.equals(target.minutesFromZero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return equals((TimeOfDay) obj);
	}
	
	@Override
	public int hashCode() {
		return minutesFromZero.hashCode();
	}

	public Minutes minutesFrom(TimeOfDay base) {
		return minutesFromZero.subtract(base.minutesFromZero);
	}

	public String format() {
		return minutesFromZero.format();
	}
	
	@Override
	public String toString() {
		return format();
	}

	@Override
	public int compareTo(TimeOfDay target) {
		return comparator(true).compare(this, target);
	}
}
