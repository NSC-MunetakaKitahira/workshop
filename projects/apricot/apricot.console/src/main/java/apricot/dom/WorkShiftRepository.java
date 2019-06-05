package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		WorkShift workShift = new WorkShift();
		workShift.setWorkStart(510); // 8:30
		workShift.setWorkEnd(1050);  // 17:30
		workShift.setOvertimeStarts(Arrays.asList(1080, 1320)); // 18:00, 22:00
		workShift.setOvertimeEnds(Arrays.asList(1320, 1440));   // 22:00, 24:00
		workShift.setBreakStarts(Arrays.asList(720, 1050)); // 12:00, 17:30
		workShift.setBreakEnds(Arrays.asList(780, 1080));   // 13:00, 18:00
		
		return workShift;
	}
}
