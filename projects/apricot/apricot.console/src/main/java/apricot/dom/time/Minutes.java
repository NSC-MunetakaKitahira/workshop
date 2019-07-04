package apricot.dom.time;

/**
 * 分(時間)の量を表す
 */
public class Minutes implements Comparable<Minutes> {

	protected final int value;
	
	public Minutes(int value) {
		this.value = value;
	}
	
	public Minutes(String timeString) {
		this(parse(timeString));
	}
	
	public static Minutes zero() {
		return new Minutes(0);
	}
	
	public int hours() {
		return value / 60;
	}
	
	public int minutesInHour() {
		return value % 60;
	}
	
	public Minutes add(Minutes adder) {
		return new Minutes(this.value + adder.value);
	}
	
	public Minutes subtract(Minutes subtractor) {
		return new Minutes(this.value - subtractor.value);
	}
	
	public String format() {
		return String.format("%d:%02d", this.hours(), this.minutesInHour());
	}
	
	@Override
	public String toString() {
		return format();
	}

	@Override
	public int compareTo(Minutes target) {
		return value - target.value;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return this.equals((Minutes) obj);
	}
	
	public boolean equals(Minutes target) {
		return target != null && target.value == this.value;
	}
	
	private static int parse(String timeString) {
		String timeStringNoColon = timeString.replace(":", "");
		int time = Integer.parseInt(timeStringNoColon);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}
}
