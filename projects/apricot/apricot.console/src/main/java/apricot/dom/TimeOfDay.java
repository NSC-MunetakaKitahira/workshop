package apricot.dom;

//import java.util.List;

public class TimeOfDay {
	
	public int minutes;
	public int hours;
	public int num;
		
	public TimeOfDay(int minutes,int hours){
		this.minutes = minutes;
		this.hours = hours;
		this.num = hours*60 + minutes;
	}
	
	public TimeOfDay(int num){
		this.minutes = num%60;
		this.hours = num/60  ;
		this.num = num;
}

	public static String formatTime(int time) {
		int minutes = time % 100;
		int hours = time / 100;
		return String.format("%d:%02d", hours, minutes);
	}
}