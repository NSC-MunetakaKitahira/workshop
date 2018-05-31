package apricot.dom;

public class Time {
	
	private int time;
	
	public Time(String stringTime) { //コンストラクタ
		this.time=Commons.parseTimeString(stringTime);
		
	}
	
	public Time(int time) { //コンストラクタ。int型の時刻をそのまま代入
		this.time= time;
		
	}
	
	public int getTime(){
		return this.time;
	}
	
	
	public void setStartTime(String stringTime) {//時間の登録(引数string型)
		this.time=Commons.parseTimeString(stringTime);
	}
	
	public void setEndTime(int time) { //時間の登録(引数int型)
		this.time=time;
	}
	
//	public String formatTime(int time) { //int型にした時刻をString型の〇〇:〇〇の形に直す
//		int minutes = time % 60;
//		int hours = time / 60;
//		return String.format("%d:%02d", hours, minutes);
//	}
	
//	public int parseIntTime(String stringTime) { //String型の時刻〇〇:〇〇をint型で分単位に直す
//		stringTime = stringTime.replace(":", "");
//		int time = Integer.parseInt(stringTime);
//		int minutes = time % 100;
//		int hours = time / 100;
//		return  hours * 60 + minutes;
//	}
}
