package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

public static WorkShift get() {
		
		WorkShift workShift = new WorkShift();
		workShift.setWork(510,1050);
		workShift.setOvertime(Arrays.asList(Arrays.asList(1080,1320),Arrays.asList(1320,1440)));
		workShift.setBreak(Arrays.asList(Arrays.asList(720,780), Arrays.asList(1050,1080)));
		
		return workShift;
	}
}
