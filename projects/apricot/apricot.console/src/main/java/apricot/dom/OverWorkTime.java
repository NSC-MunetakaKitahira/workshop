package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class OverWorkTime {
	public static List<Integer[]> overWorkTime(int startWork, int endWork) {
		WorkShift workShift = WorkShiftRepository.get();

		List<Integer[]> overworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOvertimeStarts().size(); i++) {
			int[] duplication = Overlap.getDuplication(startWork  ,endWork  ,workShift.getOvertimeStarts().get(i)  ,workShift.getOvertimeEnds().get(i));
			
			if (duplication.length != 2) continue;
				// int配列をList<Integer[]>に追加できないので、Integer配列に変換
				overworkTimes.add(new Integer[] { duplication[0], duplication[1] });
		}
		return overworkTimes;
	}
}
