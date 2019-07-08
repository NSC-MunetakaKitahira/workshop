package apricot.dom;

public class CommonUtil {

	public static String format(int minutes) {
		int hours = minutes / 60;
		int minutesInHour = minutes % 60;
		return String.format("%d:%02d", hours, minutesInHour);
	}

	public static int parse(String timeString) {
		String timeStringNoColon = timeString.replace(":", "");
		int time = Integer.parseInt(timeStringNoColon);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}
	
}
