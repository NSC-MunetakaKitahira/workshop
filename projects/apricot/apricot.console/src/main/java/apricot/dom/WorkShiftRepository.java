package apricot.dom;

import java.util.Arrays;
import java.util.List;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		// 就業
		TimePeriod workTime = new TimePeriod(new TimeOfDay(8, 30), new TimeOfDay(17, 30));
		
		// 残業
		List<TimePeriod> overworkTimes = Arrays.asList(
				new TimePeriod(new TimeOfDay(18, 0), new TimeOfDay(22, 0)),
				new TimePeriod(new TimeOfDay(22, 0), new TimeOfDay(24, 0)));
		
		// 休憩
		List<TimePeriod> breakTimes = Arrays.asList(
				new TimePeriod(new TimeOfDay(10, 0), new TimeOfDay(10, 30)),
				new TimePeriod(new TimeOfDay(12, 0), new TimeOfDay(13, 0)),
				new TimePeriod(new TimeOfDay(17, 30), new TimeOfDay(18, 0)),
				new TimePeriod(new TimeOfDay(20, 00), new TimeOfDay(20, 10)),
				new TimePeriod(new TimeOfDay(21, 00), new TimeOfDay(21, 10)));
		
		WorkShift workShift = new WorkShift(workTime, overworkTimes, breakTimes);
		
		return workShift;
	}
}
