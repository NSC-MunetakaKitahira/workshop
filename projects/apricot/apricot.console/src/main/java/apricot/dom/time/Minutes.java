package apricot.dom.time;

public class Minutes implements Comparable<Minutes> {

	private int value;

	public Minutes(int value) {
		this.value = value;
	}
	
	public static Minutes parse(String timeString) {
		String timeStringNoColon = timeString.replace(":", "");
		int time = Integer.parseInt(timeStringNoColon);
		int minutes = time % 100;
		int hours = time / 100;
		return new Minutes(hours * 60 + minutes);
	}
	
	
	public String format() {
		int hours = value / 60;
		int minutesInHour = value % 60;
		return String.format("%d:%02d", hours, minutesInHour);
	}

	@Override
	public int compareTo(Minutes o) {
		return value - o.value;
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
		return equals((Minutes) obj);
	}
	
	public boolean equals(Minutes other) {
		return other != null && value == other.value;
	}
	
	public Minutes plus(Minutes other) {
		return new Minutes(value + other.value);
	}
	
	public Minutes minus(Minutes other) {
		return new Minutes(value - other.value);
	}

}
