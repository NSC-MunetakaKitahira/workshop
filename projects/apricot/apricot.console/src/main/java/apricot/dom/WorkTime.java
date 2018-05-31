package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class WorkTime {
	public static List<Integer[]> workTime(int startWork, int endWork) {
		WorkShift workShift = WorkShiftRepository.get();
	
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		List<Integer[]> workTimes = new ArrayList<>();
		int[] duplication = Overlap.getDuplication(startWork, endWork, workShift.getWorkStart(), workShift.getWorkEnd());

		workTimes.add(new Integer[] { duplication[0], duplication[1] });
		return workTimes;
	}
}
