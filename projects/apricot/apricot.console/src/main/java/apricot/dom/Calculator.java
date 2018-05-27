package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

	public static void calculate(TimeOfDay timeStampStart, TimeOfDay timeStampEnd, WorkShift workShift) {

		// 引数の開始・終了時刻を時間帯クラスに変換
		PeriodOfTime workPeriodTime = new PeriodOfTime(timeStampStart, timeStampEnd);

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		List<PeriodOfTime> actualWorkTime = Arrays.asList(Commons.getDuplication(workPeriodTime, new PeriodOfTime(workShift.getWorkStart(), workShift.getWorkEnd())));

		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<PeriodOfTime> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOvertimeStarts().size(); i++) {
			PeriodOfTime[] duplication = Commons.getDuplication(
					workPeriodTime,
					new PeriodOfTime(workShift.getOvertimeStarts().get(i), workShift.getOvertimeEnds().get(i)));
			for (int j = 0; j < duplication.length; j++)
			{
				actualOverworkTimes.add(duplication[j]);
			}
		}

		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<PeriodOfTime> actualBreakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreakStarts().size(); i++) {
			PeriodOfTime[] duplication = Commons.getDuplication(
					workPeriodTime,
					new PeriodOfTime(workShift.getBreakStarts().get(i),workShift.getBreakEnds().get(i)));
			for (int j = 0; j < duplication.length; j++)
			{
				actualBreakTimes.add(duplication[j]);
			}
		}

		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<PeriodOfTime> actualWorkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualWorkTime.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				PeriodOfTime[] subtraction = Commons.getSubtraction(actualWorkTime.get(i), actualBreakTimes.get(j));

				for(int k = 0; k < subtraction.length; k++)
				{
					actualWorkTimesWithoutBreak.add(subtraction[k]);
				}
				if (subtraction.length > 0)
				{
					break;
				}
			}
		}

		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<PeriodOfTime> actualOverworkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				PeriodOfTime[] subtraction = Commons.getSubtraction(actualOverworkTimes.get(i), actualBreakTimes.get(j));

				for(int k = 0; k < subtraction.length; k++)
				{
					actualOverworkTimesWithoutBreak.add(subtraction[k]);
				}
				if (subtraction.length > 0)
				{
					break;
				}
			}
		}

		System.out.println("就業時間");
		int sumWorkTime = 0;
		for (int i = 0; i < actualWorkTimesWithoutBreak.size(); i++) {
			TimeOfDay start = actualWorkTimesWithoutBreak.get(i).getStartTime();
			TimeOfDay end = actualWorkTimesWithoutBreak.get(i).getEndTime();
			System.out.println(Commons.formatTime(start.value()) + "～" + Commons.formatTime(end.value()));
			sumWorkTime += end.value() - start.value();
		}
		System.out.println("合計: " + Commons.formatTime(sumWorkTime));

		System.out.println("残業時間");
		int sumOverworkTime = 0;
		for (int i = 0; i < actualOverworkTimesWithoutBreak.size(); i++) {
			TimeOfDay start = actualOverworkTimesWithoutBreak.get(i).getStartTime();
			TimeOfDay end = actualOverworkTimesWithoutBreak.get(i).getEndTime();
			System.out.println(Commons.formatTime(start.value()) + "～" + Commons.formatTime(end.value()));
			sumOverworkTime += end.value() - start.value();
		}
		System.out.println("合計: " + Commons.formatTime(sumOverworkTime));

		System.out.println("休憩時間");
		int sumBreakTime = 0;
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			TimeOfDay start = actualBreakTimes.get(i).getStartTime();
			TimeOfDay end = actualBreakTimes.get(i).getEndTime();
			System.out.println(Commons.formatTime(start.value()) + "～" + Commons.formatTime(end.value()));
			sumBreakTime += end.value() - start.value();
		}
		System.out.println("合計: " + Commons.formatTime(sumBreakTime));
	}
}
