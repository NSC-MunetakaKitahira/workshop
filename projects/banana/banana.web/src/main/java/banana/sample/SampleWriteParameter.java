package banana.sample;

public class SampleWriteParameter {

//	private String name;
//	private int value;
	
	private int masterCode;
	private String startTime;
	private String endTime;
	
	public int getCode() {
		return masterCode;
	}
	
	public void setMasterCode(int masterCode) {
		this.masterCode = masterCode;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStart(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEnd() {
		return endTime;
	}
	
	public void setEnd(String endTime) {
		this.endTime = endTime;
	}
}
