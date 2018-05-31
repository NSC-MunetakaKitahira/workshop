package apricot.dom;

public class FormatChange {
	//8:30→510に変更
	public static int parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);//time→830
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}
	//510→8:30に変更
	public static String parseTimeInt(int timeInt) {
		int minutes = timeInt % 60;
		int hours = timeInt / 60;
		return String.format("%d:%02d", hours, minutes);//hours+minutes→830
	}
}
