package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimePeriod {
	private Time startTime;
	private Time endTime;
	private List<Time> period = new  ArrayList<Time>(Arrays.asList(startTime, endTime));
	
	public TimePeriod(String startTimeString,String endTimeString){ //コンストラクタ(startとendの登録:string型)
		startTime = new Time(startTimeString);
		
		endTime = new Time(endTimeString);
	}
	
	public TimePeriod(int startTime,int endTime){ //コンストラクタ(startとendの登録:int型)
		this.startTime = new Time(startTime);
		
		this.endTime = new Time(endTime);
	}
	
	public List<Time> getTimePeriod(){
				
		return period;
	}
	
	public int getStartTime() {
		return startTime.getTime();
	}
	
	public int getEndTime() {
		return endTime.getTime();
	}
}
