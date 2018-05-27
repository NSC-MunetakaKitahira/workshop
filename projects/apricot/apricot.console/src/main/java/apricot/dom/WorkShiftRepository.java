package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {

		WorkShift workShift = new WorkShift();
		workShift.setWorkStart(new TimeOfDay(510));
		workShift.setWorkEnd(new TimeOfDay(1050));
		workShift.setOvertimeStarts(Arrays.asList(new TimeOfDay(1080), new TimeOfDay(1320)));
		workShift.setOvertimeEnds(Arrays.asList(new TimeOfDay(1320), new TimeOfDay(1440)));
		workShift.setBreakStarts(Arrays.asList(new TimeOfDay(720), new TimeOfDay(1080)));
		workShift.setBreakEnds(Arrays.asList(new TimeOfDay(780), new TimeOfDay(1110)));

		return workShift;
	}
}
