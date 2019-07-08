package apricot.dom.time;

public class TimeOfDay {

	private Minutes minutes;

	public TimeOfDay(Minutes minutes) {
		this.minutes = minutes;
	}
	
	public TimeOfDay(int hours, int minutesInHour) {
		this(new Minutes(hours * 60 + minutesInHour));
	}
	
	public TimeOfDay(String timeString) {
		this(Minutes.parse(timeString));
	}

	@Override
	public int hashCode() {
		return minutes.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return equals((TimeOfDay) obj);
	}
	
	public boolean equals(TimeOfDay other) {
		return other != null && minutes.equals(other.minutes);
	}
	
	public boolean isBefore(TimeOfDay other) {
		return minutes.compareTo(other.minutes) < 0;
	}
	
	public boolean isBeforeOrEqual(TimeOfDay other) {
		return minutes.compareTo(other.minutes) <= 0;
	}
	
	public Minutes minutesFrom(TimeOfDay base) {
		return minutes.minus(base.minutes);
	}
	
	public String format() {
		return minutes.format();
	}
}
