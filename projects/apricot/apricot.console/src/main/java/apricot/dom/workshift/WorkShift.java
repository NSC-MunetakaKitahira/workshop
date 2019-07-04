package apricot.dom.workshift;

import apricot.dom.record.WorkRecord;
import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

/**
 * 就業時間帯
 */
public class WorkShift {
	
	/** 勤務する時間帯 */
	private final WorkTimePeriods works;
	
	/** 休憩時間帯 */
	private final TimePeriods breaks;
	
	public WorkShift(WorkTimePeriods works, TimePeriods breaks) {
		this.works = works;
		this.breaks = breaks;
	}

	/**
	 * 出退勤時刻に基づいて、実際の勤務や休憩の時間帯を算出する
	 * @param timeStamp 出退勤
	 * @return 結果
	 */
	public WorkRecord workRecord(TimePeriod timeStamp) {

		TimePeriods actualBreaks = actualBreaks(timeStamp);
		
		return new WorkRecord(
				works.actualRegulars(timeStamp, actualBreaks),
				works.actualOverworks(timeStamp, actualBreaks),
				actualBreaks);
	}
	
	private TimePeriods actualBreaks(TimePeriod timeStamp) {
		return timeStamp.getDuplicationWith(breaks);
	}
}
