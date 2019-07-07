package apricot.dom;

import java.util.List;

import apricot.dom.time.TimePeriod;

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
	
	public static int calculateTime(List<TimePeriod> periods) {
		int total = 0;
		for (TimePeriod period : periods) {
			total += period.length();
		}
		return total;
	}
}
