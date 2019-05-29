package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	public static void calculate(TimePeriod timeStamp, WorkShift workShift) {
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		int[] actualWorkTime = timeStamp.Duplicate(workShift.getWorkTimes());
		
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<Integer[]> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getOverTimes().size(); i++) {
			int[] duplication = timeStamp.Duplicate(workShift.getOverTimes().get(i));
			
			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualOverworkTimes.add(new Integer[] { duplication[0], duplication[1] });
		}
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<Integer[]> actualBreakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreakTimes().size(); i++) {
			int[] duplication = timeStamp.Duplicate(workShift.getBreakTimes().get(i));
			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualBreakTimes.add(new Integer[] { duplication[0], duplication[1] });
		}
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<Integer[]> actualWorkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualBreakTimes.size(); i++) {
		
			TimePeriod actualWorkTimePeriod = new TimePeriod(actualWorkTime[0], actualWorkTime[1]);
			TimePeriod actualBreakTimesPeriod = new TimePeriod(actualBreakTimes.get(i)[0], actualBreakTimes.get(i)[1]);
			
			int[] subtraction = actualWorkTimePeriod.Subtract(actualBreakTimesPeriod);
			
			if (subtraction.length == 2) {
				actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
				break;
			} else if (subtraction.length == 4) {
				actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
				actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[2], subtraction[3] });
				break;
			}
		}
		
		//休憩なしで業務が終了
		if(actualBreakTimes.size() <= 0 && actualWorkTimesWithoutBreak.size() <=0) {
			actualWorkTimesWithoutBreak.add(new Integer[] { actualWorkTime[0], actualWorkTime[1] });
		}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<Integer[]> actualOverworkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				
				TimePeriod actualOverworkTimesPeriod = new TimePeriod(actualOverworkTimes.get(i)[0], actualOverworkTimes.get(i)[1]);
				TimePeriod actualBreakTimesPeriod = new TimePeriod(actualBreakTimes.get(j)[0], actualBreakTimes.get(j)[1]);
				
				int[] subtraction = actualOverworkTimesPeriod.Subtract(actualBreakTimesPeriod);
				
				if (subtraction.length == 2) {
					actualOverworkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
					break;
				} else if (subtraction.length == 4) {
					actualOverworkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
					actualOverworkTimesWithoutBreak.add(new Integer[] { subtraction[2], subtraction[3] });
					break;
				}
			}
		}
		
		System.out.println("就業時間");
		Commons.actualTimesPrint(actualWorkTimesWithoutBreak);
		System.out.println("残業時間");
		Commons.actualTimesPrint(actualOverworkTimesWithoutBreak);
		System.out.println("休憩時間");
		Commons.actualTimesPrint(actualBreakTimes);
		
	}
}
