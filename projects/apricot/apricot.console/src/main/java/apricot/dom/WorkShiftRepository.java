package apricot.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
		
		WorkShift workShift = new WorkShift();
		
		TimePeriod WorktimePeriod = new TimePeriod(510,1050);
		workShift.setWorkTimes(WorktimePeriod);

		List<TimePeriod> OvertimePeriods = new ArrayList<>();
		OvertimePeriods.add(new TimePeriod(1080,1320));
		OvertimePeriods.add(new TimePeriod(1320,1440));
		workShift.setOverTimes(OvertimePeriods);
		
		List<TimePeriod> BreaktimePeriods = new ArrayList<>();
		BreaktimePeriods.add(new TimePeriod(720,780));
		BreaktimePeriods.add(new TimePeriod(1050,1080));
		workShift.setBreakTimes(BreaktimePeriods);
		
		return workShift;
	}
}
