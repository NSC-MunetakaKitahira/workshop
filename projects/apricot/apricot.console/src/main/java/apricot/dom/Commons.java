package apricot.dom;
import java.text.Normalizer;

public class Commons {//名前だけどtimeInterfaceとかtimeFunctionとかのほうが良くない

	public static int parseTimeString(String inputString) {

		String timeString = Normalizer.normalize(inputString, Normalizer.Form.NFKC);

		if(!(timeString.matches("[0-9]?[0-9]:[0-9][0-9]"))) {throw new IllegalArgumentException("is not matches regix.");}

		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;

		if(minutes>=60) {throw new IllegalArgumentException("minute is over 60.");}

		return hours * 60 + minutes;
	}


	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}