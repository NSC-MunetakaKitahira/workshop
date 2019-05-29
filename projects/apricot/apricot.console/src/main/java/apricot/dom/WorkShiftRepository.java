package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		WorkShift workShift = new WorkShift(
				new TimePeriod(new TimeOfDay(8, 30), new TimeOfDay(17, 30)),
				Arrays.asList(
						new TimePeriod(new TimeOfDay(18, 0), new TimeOfDay(22, 0)),
						new TimePeriod(new TimeOfDay(22, 0), new TimeOfDay(24, 0))),
				Arrays.asList(
						new TimePeriod(new TimeOfDay(12, 0), new TimeOfDay(13, 0)),
						new TimePeriod(new TimeOfDay(17, 30), new TimeOfDay(18, 0))));
		
		return workShift;
	}
}
