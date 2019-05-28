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
	
	public static void actualTimesPrint(List<Integer[]> actualTimes) {
		
		int sumTime = 0;
		for (int i = 0; i < actualTimes.size(); i++) {
			int start = actualTimes.get(i)[0];
			int end = actualTimes.get(i)[1];
			System.out.println(formatTime(start) + "～" + Commons.formatTime(end));
			sumTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumTime));
	}

}
