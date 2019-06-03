package apricot.dom;

import java.util.List;

public class Commons {

	public static int parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}

	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
	
	public static void actualTimesPrint(List<TimePeriod> actualTimes) {
		int sumTime = 0;
		for (TimePeriod actualTime : actualTimes) {
			int start = actualTime.getStart();
			int end = actualTime.getEnd();
			System.out.println(formatTime(start) + "～" + Commons.formatTime(end));
			sumTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumTime));
	}
	
	public static void actualAllTimePrint(List<TimePeriod> actualWorkTimesWithoutBreak, List<TimePeriod> actualOverworkTimesWithoutBreak,List<TimePeriod> actualBreakTimes) {
		System.out.println("就業時間");
		actualTimesPrint(actualWorkTimesWithoutBreak);
		System.out.println("残業時間");
		actualTimesPrint(actualOverworkTimesWithoutBreak);
		System.out.println("休憩時間");
		actualTimesPrint(actualBreakTimes);
	}
}