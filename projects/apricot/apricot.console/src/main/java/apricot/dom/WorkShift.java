package apricot.dom;

import java.util.ArrayList;
import java.util.List;

/**就業時間帯の管理
 * @author yuta_sano
 *
 */
public class WorkShift {
	/**
	 * 勤務時間
	 */
	private TimePeriod worktime;
	/**
	 * 残業時間
	 */
	private List<TimePeriod> overtimeList = new ArrayList<TimePeriod>();
	/**
	 * 休憩時間
	 */
	private List<TimePeriod> breaktimeList = new ArrayList<TimePeriod>();

	public void setWorktime(String startTimeOfString, String endTimeOfString) {
		this.worktime = new TimePeriod(startTimeOfString, endTimeOfString);
	}

	public void addOvertime(String startTimeOfString, String endTimeOfString) {
		this.overtimeList.add(new TimePeriod(startTimeOfString, endTimeOfString));
	}

	public void addBreakime(String startTimeOfString, String endTimeOfString) {
		this.breaktimeList.add(new TimePeriod(startTimeOfString, endTimeOfString));
	}
	
	private TimePeriod getActualWorktimeFrom(TimePeriod target) {
		return this.worktime.getDuplicationWith(target);
	}
	private List<TimePeriod> getActualOvertimeFrom(TimePeriod target){
		return WorkShift.superimposesTargetOnAllListItem(target,overtimeList);
	}
	private List<TimePeriod> getActualBreaktimeFrom(TimePeriod target){
		return WorkShift.superimposesTargetOnAllListItem(target,breaktimeList);
	}
	private static List<TimePeriod> superimposesTargetOnAllListItem (TimePeriod target,List<TimePeriod> thisList){
		List<TimePeriod> duplicationList = new ArrayList<TimePeriod>();
		for (TimePeriod timePeriod : thisList) {
			TimePeriod duplication = target.getDuplicationWith(timePeriod);
			if(duplication.temporalLength().toMinutes() != 0)
				duplicationList.add(duplication);
		}
		return duplicationList;
	}
	private static List<TimePeriod> splitTargetByAllListItem (TimePeriod target,List<TimePeriod> thisList){
		List<TimePeriod> splitList = new ArrayList<TimePeriod>();
		for (TimePeriod timePeriod : thisList) {
			TimePeriod[] subtraction = target.getSubtractionWith(timePeriod);
			// 2つに分けられていたら１つ目をListに入れる.
			if(subtraction.length >= 2) {
				splitList.add(subtraction[0]);
			}
			// 分割対象の変更処理
			target = subtraction[ subtraction.length - 1 ];
		}
		splitList.add(target);
		return splitList;
	}
	
	public void calculate(TimePeriod timeStamp) {
		
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorktime = this.getActualWorktimeFrom(timeStamp);
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworktimeList = this.getActualOvertimeFrom(timeStamp);
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreaktimeList = this.getActualBreaktimeFrom(timeStamp);
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorktimeListWithoutBreaktime = WorkShift.splitTargetByAllListItem(actualWorktime, actualBreaktimeList);
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOvertimeListWithoutBreaktime = new ArrayList<TimePeriod>();
		for (TimePeriod actualOvertime : actualOverworktimeList) {
			actualOvertimeListWithoutBreaktime.addAll(WorkShift.splitTargetByAllListItem(actualOvertime, actualBreaktimeList));
		}
		
		System.out.println("就業時間");
		int sumWorktime = 0;
		for (TimePeriod worktimeWithoutBreaktime : actualWorktimeListWithoutBreaktime) {
			System.out.println(worktimeWithoutBreaktime.toString());
			sumWorktime += worktimeWithoutBreaktime.temporalLength().toMinutes();
		}
		System.out.println("合計: " + Time.parseTimeFrom(sumWorktime));
		
		System.out.println("残業時間");
		int sumOvertime = 0;
		for (TimePeriod overtimeWithoutBreaktime : actualOvertimeListWithoutBreaktime) {
			System.out.println(overtimeWithoutBreaktime.toString());
			sumOvertime += overtimeWithoutBreaktime.temporalLength().toMinutes();
		}
		System.out.println("合計: " + Time.parseTimeFrom(sumOvertime));
				
		System.out.println("休憩時間");
		int sumBreaktime = 0;
		for (TimePeriod actualBreaktime : actualBreaktimeList) {
			System.out.println(actualBreaktime.toString());
			sumBreaktime += actualBreaktime.temporalLength().toMinutes();
		}
		System.out.println("合計: " + Time.parseTimeFrom(sumBreaktime));
	}

}
