package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class TimePair {
	private List<Integer> startTime;
	private List<Integer> endTime;
	
	public TimePair() {
		this.startTime = new ArrayList<Integer>();
		this.endTime = new ArrayList<Integer>();
	}
	
	public TimePair(int startTime, int endTime) {
		this.startTime = new ArrayList<Integer>();
		this.endTime = new ArrayList<Integer>();
		this.add(startTime, endTime);
	}
	
	public TimePair(List<Integer> startTime, List<Integer> endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int size() {
		return startTime.size();
	}
	
	public void add(int start, int end) {
		this.startTime.add(start);
		this.endTime.add(end);
	}
	
	public int[] getPair(int i) {
		if (this.size() == 0) {
			return new int[] { 0, 0 };
		}
		return new int[] { startTime.get(i), endTime.get(i) };
	}
}
