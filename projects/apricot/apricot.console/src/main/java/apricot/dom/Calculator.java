package apricot.dom;

import java.util.ArrayList;
import java.util.List;
import apricot.dom.Commons;

public class Calculator {

	public static void calculate(int timeStampStart, int timeStampEnd, WorkShift workShift) {

		// 出退勤時間を登録
		TimePeriod stampTimePeriod = new TimePeriod(timeStampStart, timeStampEnd);

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲


		TimePeriod acutualWorkTime = Commons.getDuplication(stampTimePeriod, workShift.getWork());

		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOvertime().size(); i++) {
			TimePeriod duplication = Commons.getDuplication(stampTimePeriod, workShift.getOvertime().get(i));

			if (duplication == null)
				continue;

			actualOverworkTimes.add(duplication);
		}
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTimes = new ArrayList<>();

		for (int i = 0; i < workShift.getBreak().size(); i++) {
			TimePeriod breakPeriod = workShift.getBreak().get(i);
			TimePeriod duplication = Commons.getDuplication(stampTimePeriod, breakPeriod);

			if (duplication == null)
				continue;

			actualBreakTimes.add(duplication);
		}

		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualBreakTimes.size(); i++) {

			TimePeriod[] subtraction = Commons.getSubtraction(acutualWorkTime, actualBreakTimes.get(i));
//			for (TimePeriod s : subtraction) {
//				actualWorkTimesWithoutBreak.add(s);
//				break;
//			}

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

				// 休憩時間が２つある場合
				// 配列定義して一つ一つ休憩時間をとるようにする
				TimePeriod[] getBreakTimes;
				// 残業から休憩１と休憩２を引く
				
				// 残業-休憩1(12：00－13：00)
				if (j == 0) {
					getBreakTimes = Commons.getSubtraction(actualOverworkTimes.get(i), actualBreakTimes.get(j));
					
					if (getBreakTimes.length == 1) {
						actualOverworkTimesWithoutBreak.add(getBreakTimes[0]);

					} else if (getBreakTimes.length == 2) {
						actualOverworkTimesWithoutBreak.add(getBreakTimes[0]);
						actualOverworkTimesWithoutBreak.add(getBreakTimes[1]);
					}

				} else {
					
					// 休憩と被っていない残業時間リストの最後の要素を出す
					TimePeriod break1 = (actualOverworkTimesWithoutBreak
							.get(actualOverworkTimesWithoutBreak.size() - 1));
					
					// 休憩２を引いたものを変数に加える
					getBreakTimes = Commons.getSubtraction(break1, actualBreakTimes.get(j));
					
					// 一番最後リストの要素を消す
					actualOverworkTimesWithoutBreak.remove(actualOverworkTimesWithoutBreak.size() - 1);

					if (getBreakTimes.length == 1) {
						actualOverworkTimesWithoutBreak.add(getBreakTimes[0]);

					} else if (getBreakTimes.length == 2) {
						actualOverworkTimesWithoutBreak.add(getBreakTimes[0]);
						actualOverworkTimesWithoutBreak.add(getBreakTimes[1]);

					}

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
	
	
	