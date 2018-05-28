package apricot.dom;

/**
 * @author yuta_sano
 *
 */
public class TimePeriod{
	private Time startTime;
	private Time endTime;


	/**
	 * @param startTimeOfInt 整数形式の開始時刻（e.g. 830）
	 * @param endTimeOfInt 整数形式の修了時刻（e.g. 1730）
	 */
	public TimePeriod(int startTimeOfInt, int endTimeOfInt) {
		this.startTime = new Time(startTimeOfInt);
		this.endTime = new Time(endTimeOfInt);
	}

	/**
	 * @param startTimeOfInt 文字列形式の開始時刻（e.g. "8:30"）
	 * @param endTimeOfInt 文字列形式の修了時刻（e.g. "17:30"）
	 */
	public TimePeriod(String startTimeOfString, String endTimeOfString) {
		this.startTime = new Time(startTimeOfString);
		this.endTime = new Time(endTimeOfString);
	}
	
	public Time start() {
		return this.startTime;
	}
	public Time end() {
		return this.endTime;		
	}

	@Override
	public String toString() {
		return startTime.toString() + "～" + endTime.toString();
	}
}