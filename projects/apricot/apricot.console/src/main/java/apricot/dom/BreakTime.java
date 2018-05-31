package apricot.dom;

public class BreakTime extends Time {

	public BreakTime(int timeStart, int timeEnd, WorkShift workShift) {
		super(timeStart, timeEnd, workShift);
	}

	// 休憩時間の算出
	public TimePair getActualBreakTime() {
		TimePair breakTime = new TimePair(this.workShift.getBreakStarts(), this.workShift.getBreakEnds());
		return super.getDuplicationTime(breakTime);
	}

	// 休憩時間の表示
	public void printActualBreakTime() {
		System.out.println("休憩時間");
		super.printTime(getActualBreakTime());
	}
}
