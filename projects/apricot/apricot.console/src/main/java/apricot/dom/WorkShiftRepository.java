package apricot.dom;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		WorkShift workShift = new WorkShift();
		workShift.setWorktime("8:30", "17:30");
		workShift.addBreakime("12:00", "13:00");
		workShift.addBreakime("17:30", "18:00");
		workShift.addOvertime("18:00", "22:00");
		workShift.addOvertime("22:00", "24:00");
		
		return workShift;
	}
}
