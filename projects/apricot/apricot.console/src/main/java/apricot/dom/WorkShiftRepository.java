package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		WorkShift workShift = new WorkShift();
		workShift.setWorkTime(new TimePeriod(new TimeOfDay(510), new TimeOfDay(1050)));
		workShift.setOverworkTimes(Arrays.asList(
				new TimePeriod(new TimeOfDay(1080), new TimeOfDay(1320)),
				new TimePeriod(new TimeOfDay(1320), new TimeOfDay(1440))));
		workShift.setBreakTimes(Arrays.asList(
				new TimePeriod(new TimeOfDay(720), new TimeOfDay(780)),
				new TimePeriod(new TimeOfDay(1080), new TimeOfDay(1110))));
		
		return workShift;
	}
}
