package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class BreakWorkTime {
	public static List<Integer[]> breakWorkTime(int startWork, int endWork) {
		WorkShift workShift = WorkShiftRepository.get();

		List<Integer[]> breakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreakStarts().size(); i++) {
			int[] duplication = Overlap.getDuplication(startWork  ,endWork  ,workShift.getBreakStarts().get(i)  ,workShift.getBreakEnds().get(i));
			
			if (duplication.length != 2) continue;
				// int配列をList<Integer[]>に追加できないので、Integer配列に変換
				breakTimes.add(new Integer[] { duplication[0], duplication[1] });
		}
		return breakTimes;
	}
}