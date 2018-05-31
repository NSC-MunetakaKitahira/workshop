package apricot.dom;

public class WorkTime extends BreakTime {

	public WorkTime(int startTime, int endTime, WorkShift workShift) {
		super(startTime, endTime, workShift);
	}
	
	// 休憩時間を除いた就業時間
	public TimePair getActualWorkTimesWithoutBreak() {
		return super.getSubtractionTime(this.getActualWorkTime(), super.getActualBreakTime());
	}
	
	// 休憩時間を含めた就業時間
	public TimePair getActualWorkTime() {
		TimePair workTime = new TimePair(this.workShift.getWorkStart(), this.workShift.getWorkEnd());
		return super.getDuplicationTime(workTime);		
	}
	
	// 就業時間の表示
	public void printActualWorkTimeWithoutBreak() {
		System.out.println("就業時間");
		super.printTime(getActualWorkTimesWithoutBreak());
	}
}
