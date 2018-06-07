package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	TimePeriod workTime;
	
	
	public void setWork(int startTime,int endTime) {
		 workTime = new TimePeriod(startTime,endTime);
	}
	
	public void setWork(String startTimeString,String endTimeString) {
		int startTime=Commons.parseTimeString(startTimeString);
		int endTime=Commons.parseTimeString(endTimeString);
		workTime = new TimePeriod(startTime,endTime);
	}
	
	
	List<TimePeriod> overTime;
	
	public void setOvertime(List<List<Integer>> overtimes) {
		TimePeriod overTime1 = new TimePeriod(overtimes.get(0).get(0),overtimes.get(0).get(1));
		TimePeriod overTime2 = new TimePeriod(overtimes.get(1).get(0),overtimes.get(1).get(1));
		overTime = new  ArrayList<TimePeriod>(Arrays.asList(overTime1, overTime2));
	}
	
	List<TimePeriod> breakTime;
	public void setBreak(List<List<Integer>> breaktimes) {
		TimePeriod breakTime1 = new TimePeriod(breaktimes.get(0).get(0),breaktimes.get(0).get(1));
		TimePeriod breakTime2 = new TimePeriod(breaktimes.get(1).get(0),breaktimes.get(1).get(1));
		breakTime = new  ArrayList<TimePeriod>(Arrays.asList(breakTime1, breakTime2));
	}
	
	
	public TimePeriod getWorkTime(){
		
		return workTime;
	}
	public List<TimePeriod> getOverTime(){
		
		return overTime;
	}

	public TimePeriod getOverTime(int num){
		
		return overTime.get(num);
	}
	
	public List<TimePeriod> getBreakTime(){
		
		return breakTime;
	}
	
	public TimePeriod getBreakTime(int num){
		
		return breakTime.get(num);
	}
	
	
}
