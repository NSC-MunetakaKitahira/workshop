package apricot.dom.workshift;

import apricot.dom.time.TimeOfDay;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		// 就業
		TimePeriod regular = new TimePeriod(new TimeOfDay(8, 30), new TimeOfDay(17, 30));
		
		// 残業
		TimePeriods overworks = TimePeriods.of(
				new TimePeriod(new TimeOfDay(18, 0), new TimeOfDay(22, 0)),
				new TimePeriod(new TimeOfDay(22, 0), new TimeOfDay(24, 0)));
		
		// 休憩
		TimePeriods breaks = TimePeriods.of(
				new TimePeriod(new TimeOfDay(10, 0), new TimeOfDay(10, 30)),
				new TimePeriod(new TimeOfDay(12, 0), new TimeOfDay(13, 0)),
				new TimePeriod(new TimeOfDay(17, 30), new TimeOfDay(18, 0)),
				new TimePeriod(new TimeOfDay(20, 00), new TimeOfDay(20, 10)),
				new TimePeriod(new TimeOfDay(21, 00), new TimeOfDay(21, 10)));
		
		WorkShift workShift = new WorkShift(new WorkTimePeriods(regular, overworks), breaks);
		
		return workShift;
	}
}
