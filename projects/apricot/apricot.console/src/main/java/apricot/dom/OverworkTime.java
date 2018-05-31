package apricot.dom;

public class OverworkTime extends BreakTime {

	public OverworkTime(int startTime, int endTime, WorkShift workShift) {
		super(startTime, endTime, workShift);
	}
	
	// 休憩時間を除いた残業時間
	public TimePair getActualOverWorkTimesWithoutBreak() {
		return super.getSubtractionTime(this.getActualOverWorkTime(), super.getActualBreakTime());
	}
	
	// 休憩時間を含めた残業時間
	public TimePair getActualOverWorkTime() {
		TimePair overworkTime = new TimePair(this.workShift.getOvertimeStarts(), this.workShift.getOvertimeEnds());
		return super.getDuplicationTime(overworkTime);
	}

	// 残業時間の表示
	public void printActualOverWorkTimesWithoutBreak() {
		System.out.println("残業時間");
		super.printTime(getActualOverWorkTimesWithoutBreak());
	}
}
