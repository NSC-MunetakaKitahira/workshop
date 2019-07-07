package apricot.dom.time;

public class TimeOfDay {

	private Minutes minutes;

	private TimeOfDay(Minutes minutes) {
		this.minutes = minutes;
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
	
}
