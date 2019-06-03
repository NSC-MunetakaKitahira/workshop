package apricot.dom;



public class Commons {

	
	
	public static String formatTime(int time) {
		int minutes = time % 100;
		int hours = time / 100;
		return String.format("%d:%02d", hours, minutes);
	}
    
	public static int parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	} 
    
	public static int[] getDuplication(Object timeStampStart,Object timeStampEnd,TimePeriod timePeriod,TimePeriod timeperiod) {
		return null;
	}	
	
	public WorkShift getOvertimeStarts() {
		return getOvertimeStarts(); 
	}
	
	public WorkShift getSubtraction() {
		return getSubtraction(); 
	}
}
