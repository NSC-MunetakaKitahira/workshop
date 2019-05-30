package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift getTestdata() {
		
//		WorkShift workShift = new WorkShift();
//		workShift.setWorktime(TP(TOD("8:30"), TOD("17:30")));
//		workShift.setOvertimes(Arrays.asList(TP(TOD("18:00"), TOD("20:00")), 
//											TP(TOD("20:00"), TOD("24:00"))));
//		workShift.setBreaktimes(Arrays.asList(TP(TOD("12:00"), TOD("13:00")), 
//											 TP(TOD("17:30"), TOD("18:00"))));
//		

		WorkShift workShift = new WorkShift(
				TP(TOD("8:30"), TOD("17:30")),
				Arrays.asList(TP(TOD("18:00"), TOD("20:00")), TP(TOD("20:00"), TOD("24:00"))),
				Arrays.asList(TP(TOD("12:00"), TOD("13:00")), TP(TOD("18:00"), TOD("18:30"))));
		return workShift;
	}

	private static TimePeriod TP(TimeOfDay tod1, TimeOfDay tod2) {
		return new TimePeriod(tod1, tod2);
	}
	
	private static TimeOfDay TOD(String time) {
		return new TimeOfDay(time);
	}
}

