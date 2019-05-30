package apricot.dom;

import java.util.Arrays;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {

		WorkShift workShift = new WorkShift();

		workShift.setWork (new TimePeriod(510,1050));
		workShift.setOver (Arrays.asList(new TimePeriod(1080,1320),new TimePeriod(1320,1440)));
		workShift.setBreak(Arrays.asList(new TimePeriod(720,780)  ,new TimePeriod(1080,1110)));

		return workShift;

		//後でデータベースとか外部ファイルから読み込めるようにするとか言ってた、このままだと危ない
	}
}
