package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	public static void calculate(int timeStampStart, int timeStampEnd, WorkShift workShift) {

		// 実在時間を登録
		TimePeriod stampTime = new TimePeriod(timeStampStart, timeStampEnd);
		
		
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTimes = TimePeriod.getDuplication(stampTime, workShift.getWorktimePeriod());
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOvertimePeriod().size(); i++) {
			TimePeriod duplication = TimePeriod.getDuplication(stampTime,workShift.getOvertimePeriod().get(i));
			if (duplication == null) {continue;}
			actualOverworkTimes.add(duplication);
			
		}
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreaktimePeriod().size(); i++) {
			TimePeriod duplication = TimePeriod.getDuplication(stampTime,workShift.getBreaktimePeriod().get(i));
			if (duplication == null) {continue;}
			actualBreakTimes.add(duplication);
		}	     	
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			TimePeriod[] subtraction = TimePeriod.getSubtraction(actualWorkTimes, actualBreakTimes.get(i));
			if (subtraction.length == 1) {
					actualWorkTimesWithoutBreak.add(subtraction[0]);
					break;
				} else if (subtraction.length == 2) {
					actualWorkTimesWithoutBreak.add(subtraction[0]);
					actualWorkTimesWithoutBreak.add(subtraction[1]);
					break;
				}
		}
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverworkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				TimePeriod[] subtraction = TimePeriod.getSubtraction(actualOverworkTimes.get(i), actualBreakTimes.get(j));
					if (subtraction.length == 1) {
						actualOverworkTimesWithoutBreak.add(subtraction[0]);
						break;
					} else if (subtraction.length == 2) {
						actualOverworkTimesWithoutBreak.add(subtraction[0]);
						actualOverworkTimesWithoutBreak.add(subtraction[1]);
						break;
					}
			}
		}
		
		System.out.println("就業時間");
		int sumWorkTime = 0;
		for (int i = 0; i < actualWorkTimesWithoutBreak.size(); i++) {
			int start = actualWorkTimesWithoutBreak.get(i).start;
			int end = actualWorkTimesWithoutBreak.get(i).end;
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumWorkTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumWorkTime));
		
		System.out.println("残業時間");
		int sumOverworkTime = 0;
		for (int i = 0; i < actualOverworkTimesWithoutBreak.size(); i++) {
			int start = actualOverworkTimesWithoutBreak.get(i).start;
			int end = actualOverworkTimesWithoutBreak.get(i).end;
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumOverworkTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumOverworkTime));
		
		System.out.println("休憩時間");
		int sumBreakTime = 0;
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			int start = actualBreakTimes.get(i).start;
			int end = actualBreakTimes.get(i).end;
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumBreakTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumBreakTime));
	}
}

// int配列をList<Integer[]>に追加できないので、Integer配列に変換
// int配列をList<Integer[]>に追加できないので、Integer配列に変換
