package apricot.dom;

//0:00からの分数を記録するクラス
public class TimeOfDay {
	
	public int minute;
	
	public TimeOfDay(int time){
		this.minute = time;
	}
	
	public TimeOfDay(String timeString){
		this.minute = parseTimeString(timeString);
	}
		
	private int parseTimeString(String timeString) {
	timeString = timeString.replace(":", "");
	int time = Integer.parseInt(timeString);
	int minutes = time % 100;
	int hours = time / 100;
	return hours * 60 + minutes;
	}

	public String formatTime() {
		int minutes = this.minute % 60;
		int hours = this.minute / 60;
		return String.format("%d:%02d", hours, minutes);
	}
	
	@Override
	public String toString() {
		return String.format("%s", formatTime());
	}
}



