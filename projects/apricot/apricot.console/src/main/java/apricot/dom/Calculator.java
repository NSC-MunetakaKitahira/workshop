package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	public static void calculate(TimeOfDay timeStampStart, TimeOfDay timeStampEnd, WorkShift workShift) {

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimeOfDay[] actualWorkTime = Commons.getDuplication(timeStampStart, timeStampEnd, workShift.getWorkStart(), workShift.getWorkEnd());

		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<TimeOfDay[]> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOvertimeStarts().size(); i++) {
			TimeOfDay[] duplication = Commons.getDuplication(
					timeStampStart,
					timeStampEnd,
					workShift.getOvertimeStarts().get(i),
					workShift.getOvertimeEnds().get(i));

			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualOverworkTimes.add(new TimeOfDay[] { duplication[0], duplication[1] });
		}

		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimeOfDay[]> actualBreakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreakStarts().size(); i++) {
			TimeOfDay[] duplication = Commons.getDuplication(
					timeStampStart,
					timeStampEnd,
					workShift.getBreakStarts().get(i),
					workShift.getBreakEnds().get(i));

			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualBreakTimes.add(new TimeOfDay[] { duplication[0], duplication[1] });
		}

		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimeOfDay[]> actualWorkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			TimeOfDay workStart = actualWorkTime[0];
			TimeOfDay workEnd = actualWorkTime[1];
			TimeOfDay breakStart = actualBreakTimes.get(i)[0];
			TimeOfDay breakEnd = actualBreakTimes.get(i)[1];

			TimeOfDay[] subtraction = Commons.getSubtraction(workStart, workEnd, breakStart, breakEnd);

			if (subtraction.length == 2) {
				actualWorkTimesWithoutBreak.add(new TimeOfDay[] { subtraction[0], subtraction[1] });
				break;
			} else if (subtraction.length == 4) {
				actualWorkTimesWithoutBreak.add(new TimeOfDay[] { subtraction[0], subtraction[1] });
				actualWorkTimesWithoutBreak.add(new TimeOfDay[] { subtraction[2], subtraction[3] });
				break;
			}
		}

		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimeOfDay[]> actualOverworkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				TimeOfDay overworkStart = actualOverworkTimes.get(i)[0];
				TimeOfDay overworkEnd = actualOverworkTimes.get(i)[1];
				TimeOfDay breakStart = actualBreakTimes.get(j)[0];
				TimeOfDay breakEnd = actualBreakTimes.get(j)[1];

				TimeOfDay[] subtraction = Commons.getSubtraction(overworkStart, overworkEnd, breakStart, breakEnd);

				if (subtraction.length == 2) {
					actualOverworkTimesWithoutBreak.add(new TimeOfDay[] { subtraction[0], subtraction[1] });
					break;
				} else if (subtraction.length == 4) {
					actualOverworkTimesWithoutBreak.add(new TimeOfDay[] { subtraction[0], subtraction[1] });
					actualOverworkTimesWithoutBreak.add(new TimeOfDay[] { subtraction[2], subtraction[3] });
					break;
				}
			}
		}

		System.out.println("就業時間");
		int sumWorkTime = 0;
		for (int i = 0; i < actualWorkTimesWithoutBreak.size(); i++) {
			TimeOfDay start = actualWorkTimesWithoutBreak.get(i)[0];
			TimeOfDay end = actualWorkTimesWithoutBreak.get(i)[1];
			System.out.println(Commons.formatTime(start.value()) + "～" + Commons.formatTime(end.value()));
			sumWorkTime += end.value() - start.value();
		}
		System.out.println("合計: " + Commons.formatTime(sumWorkTime));

		System.out.println("残業時間");
		int sumOverworkTime = 0;
		for (int i = 0; i < actualOverworkTimesWithoutBreak.size(); i++) {
			TimeOfDay start = actualOverworkTimesWithoutBreak.get(i)[0];
			TimeOfDay end = actualOverworkTimesWithoutBreak.get(i)[1];
			System.out.println(Commons.formatTime(start.value()) + "～" + Commons.formatTime(end.value()));
			sumOverworkTime += end.value() - start.value();
		}
		System.out.println("合計: " + Commons.formatTime(sumOverworkTime));

		System.out.println("休憩時間");
		int sumBreakTime = 0;
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			TimeOfDay start = actualBreakTimes.get(i)[0];
			TimeOfDay end = actualBreakTimes.get(i)[1];
			System.out.println(Commons.formatTime(start.value()) + "～" + Commons.formatTime(end.value()));
			sumBreakTime += end.value() - start.value();
		}
		System.out.println("合計: " + Commons.formatTime(sumBreakTime));
	}
}
