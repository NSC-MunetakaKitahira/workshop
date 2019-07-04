package apricot.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public static Optional<TimePeriod> getDuplication(TimePeriod a, TimePeriod b) {
		
		TimePeriod result;

		// <------->
		// <------->
		if (a.getStart() == b.getStart() && a.getEnd() == b.getEnd()) {
			result = a;
		}
		
		// <------->
		//    <--..
		else if (a.getStart() <= b.getStart() && b.getStart() < a.getEnd()) {
			
			// <------->
			//    <---->
			if (b.getEnd() <= a.getEnd()) {
				result = b;
			}

			// <------->
			//    <------>
			else {
				result = new TimePeriod(b.getStart(), a.getEnd());
			}
		}
		
		//    <--..
		// <------->
		else if (b.getStart() <= a.getStart() && a.getStart() < b.getEnd()) {

			//    <---->
			// <------->
			if (a.getEnd() <= b.getEnd()) {
				result = a;
			}

			//    <------>
			// <------->
			else {
				result = new TimePeriod(a.getStart(), b.getEnd());
			}
		}
		
		else {
			result = null;
		}

		return Optional.ofNullable(result);
	}
	
	public static List<TimePeriod> getDuplications(TimePeriod base, List<TimePeriod> others) {
		
		List<TimePeriod> results = new ArrayList<>();
		for (TimePeriod other : others) {
			Optional<TimePeriod> duplication = getDuplication(base, other);
			if (duplication.isPresent()) {
				results.add(duplication.get());
			}
		}
		
		return results;
	}
	
	public static int calculateTime(List<TimePeriod> periods) {
		int total = 0;
		for (TimePeriod period : periods) {
			total += period.getEnd() - period.getStart();
		}
		return total;
	}
}
