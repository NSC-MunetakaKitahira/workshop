package apricot.dom.workshift;

import apricot.dom.time.TimePeriod;
import apricot.dom.time.TimePeriods;

/**
 * 勤務時間帯の定義
 */
public class WorkTimePeriods {
	
	/** 定時の勤務時間帯 */
	private final TimePeriod regular;
	
	/** 残業時間帯のリスト */
	private final TimePeriods overworks;

	public WorkTimePeriods(TimePeriod regular, TimePeriods overworks) {
		this.regular = regular;
		this.overworks = overworks;
	}
	
	/**
	 * 実際の勤務時間帯を返す
	 * @param timeStamp 出退勤時刻
	 * @param notWorks 勤務していない時間帯
	 * @return
	 */
	public TimePeriods actualRegulars(TimePeriod timeStamp, TimePeriods notWorks) {
		
		return timeStamp.getDuplicationWith(regular)
				.map(actual -> (TimePeriods) actual.subtract(notWorks))
				.orElse(TimePeriods.empty());
	}
	
	/**
	 * 実際の残業時間帯を返す
	 * @param timeStamp 出退勤時刻
	 * @param notWorks 勤務していない時間帯
	 * @return
	 */
	public TimePeriods actualOverworks(TimePeriod timeStamp, TimePeriods notWorks) {

		return timeStamp.getDuplicationWith(overworks)
				.subtract(notWorks);
	}
	
}
