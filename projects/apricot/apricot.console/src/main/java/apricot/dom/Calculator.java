package apricot.dom;

import java.util.List;
import apricot.dom.Exclude;

public class Calculator {

	public static void calculate(TimePeriod timeStamp, WorkShift workShift) {
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = timeStamp.Duplicate(workShift.getWorkTimes());
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = timeStamp.DuplicateActualTimes(workShift.getOverTimes());
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = timeStamp.DuplicateActualTimes(workShift.getBreakTimes());
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = Exclude.actualWithoutBreaak(actualWorkTime,actualBreakTimes);

		//休憩なしで業務が終了
		if(actualBreakTimes.size() <= 0 && actualWorkTimesWithoutBreak.size() <=0) {
			actualWorkTimesWithoutBreak.add(actualWorkTime);
		}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak = Exclude.actualOverWithoutbreak(actualOverworkTimes,actualBreakTimes);

		System.out.println("就業時間");
		Commons.actualTimesPrint(actualWorkTimesWithoutBreak);
		System.out.println("残業時間");
		Commons.actualTimesPrint(actualOverworkTimesWithoutBreak);
		System.out.println("休憩時間");
		Commons.actualTimesPrint(actualBreakTimes);
	}
}