package banana.sample;

import java.util.ArrayList;
import java.util.List;

public class SampleReadResult {

	private final int masterCode;
	private final int worktimeStart;
//	private final int worktimeEnd;
//	private final int overtime1Start;
//	private final int overtime1End;
//	private final int overtime2Start;
//	private final int overtime2End;
//	private final int resttime1Start;
//	private final int resttime1End;
//	private final int resttime2Start;
//	private final int resttime2End;
	
	private final String errorMessage;
	
//	private SampleReadResult(String name, int value, String errorMessage) {
//		this.name = name;
//		this.value = value;
//		this.errorMessage = errorMessage;
//	}
//	
//	private SampleReadResult(int masterCode, String startTime,String endTime, String errorMessage) {
//		this.masterCode = masterCode;
//		this.startTime = startTime;
//		this.endTime=endTime;
//		this.errorMessage = errorMessage;
//	}
	

	
//	private SampleReadResult(List<Integer> list, String errorMessage) {
//		this.masterCode = list.get(0);
//		this.worktimeStart = list.get(1);
//		this.worktimeEnd=list.get(2);
//		this.overtime1Start=list.get(3);
//		this.overtime1End=list.get(4);
//		this.overtime2Start=list.get(5);
//		this.overtime2End=list.get(6);
//		this.resttime1Start=list.get(7);
//		this.resttime1End=list.get(8);
//		this.resttime2Start=list.get(9);
//		this.resttime2End=list.get(10);
//		this.errorMessage = errorMessage;
//	}
//	
	private SampleReadResult(List<Integer> list, String errorMessage) {
		this.masterCode = list.get(0);
		this.worktimeStart=list.get(1);
		this.errorMessage = errorMessage;
	}
		
//	public static SampleReadResult found(String name, int value) {
//		return new SampleReadResult(name, value, null);
//	}
	
	public static SampleReadResult found(List<Integer> list) {
		return new SampleReadResult(list,null);
	}
	

	
	public static SampleReadResult notFound() {
//		return new SampleReadResult(null, null, null, "データが見つかりません");
		return null;
	}

//	public String getName() {
//		return name;
//	}
//
//	public int getValue() {
//		return value;
//	}
	
//	public int getMasterCode() {
//		return masterCode;
//	}
//	
//	public String getStartTime() {
//		return startTime;
//	}
//	
//	public String getEndTime() {
//		return endTime;
//	}
//

	
	public int getMasterCode() {
		return masterCode;
	}
	
	public int getWorktimeStart() {
		return worktimeStart;
	}
	
//	public int getWorktimeEnd() {
//		return worktimeEnd;
//	}
//	
//	public int getOvertime1Start() {
//		return overtime1Start;
//	}
//	
//	public int getOvertime1End() {
//		return overtime1End;
//	}
//	
//	public int getOvertime2Start() {
//		return overtime2Start;
//	}
//	
//	public int getOvertime2End() {
//		return overtime2End;
//	}
//	
//	public int getResttime1Start() {
//		return resttime1Start;
//	}
//	
//	public int getResttime1End() {
//		return resttime1End;
//	}
//	
//	public int getResttime2Start() {
//		return resttime2Start;
//	}
//	
//	public int getResttime2End() {
//		return resttime2End;
//	}
	
	public String getErrorMessage() {
	return errorMessage;
	}
}
