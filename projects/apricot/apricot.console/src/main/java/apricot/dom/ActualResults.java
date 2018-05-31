package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class ActualResults {
	/**
	 * 会社内にいた時間
	 */
	private TimePeriod staytime;
	/**
	 * 実勤務時間
	 */
	private TimePeriod worktime;
	/**
	 * 実残業時間
	 */
	private List<TimePeriod> overtimeList;
	/**
	 * 実休憩時間
	 */
	private List<TimePeriod> breaktimeList;

	/**
	 * 休憩を覗いた実勤務時間
	 */
	private List<TimePeriod> actualWorktimeListWithoutBreaktime;
	/**
	 * 休憩を除いた実残業時間
	 */
	private List<TimePeriod> actualOvertimeListWithoutBreaktime = new ArrayList<TimePeriod>();

	public ActualResults(TimePeriod timeStamp, WorkShift workShift) {
		this.staytime = timeStamp;
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		this.worktime = workShift.getActualWorktimeFrom(this.staytime);
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		this.overtimeList = workShift.getActualOvertimeFrom(this.staytime);
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		this.breaktimeList = workShift.getActualBreaktimeFrom(this.staytime);
	  // 実就業時間帯から実休憩時間帯に重複している部分を除外
		this.actualWorktimeListWithoutBreaktime = worktime.getSubtractionWith(this.breaktimeList);	
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		for (TimePeriod overtime : overtimeList) {
			this.actualOvertimeListWithoutBreaktime.addAll(overtime.getSubtractionWith(this.breaktimeList));
		}
	}
	public void show() {
		ActualResults.showTimePeriodList("就業時刻",this.actualWorktimeListWithoutBreaktime);
		ActualResults.showTimePeriodList("残業時刻",this.actualOvertimeListWithoutBreaktime);
		ActualResults.showTimePeriodList("休憩時刻",this.breaktimeList);
	}
	
	
	private static void showTimePeriodList(String label, List<TimePeriod> timePeriodList) {
		System.out.println(label);
		int sumTime = 0;
		for (TimePeriod timePeriod : timePeriodList) {
			System.out.println(timePeriod.toString());
			sumTime += timePeriod.temporalLength().toMinutes();
		}
		System.out.println("合計: " + Time.parseTimeOfStringFrom(sumTime));
	}
}
