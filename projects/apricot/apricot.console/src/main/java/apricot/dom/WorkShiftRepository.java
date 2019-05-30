package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		WorkShift workShift = new WorkShift();
		workShift.setWorkStart(510);
		workShift.setWorkEnd(1050);
		workShift.setWorkTimePeriod(null);;
		workShift.setOvertimeStarts(Arrays.asList(1080, 1320));
		workShift.setOvertimeEnds(Arrays.asList(1320, 1440));
		workShift.setOverTimePeriod(null);
		workShift.setBreakStarts(Arrays.asList(720, 1080));
		workShift.setBreakEnds(Arrays.asList(780, 1110));
		workShift.setBreakTimePeriod(null);
		
		return workShift;
	}
}
