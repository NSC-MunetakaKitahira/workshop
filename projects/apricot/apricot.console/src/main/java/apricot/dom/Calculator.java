package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	public static void calculate(TimePeriod stampTimePeriod, WorkShift workShift) {
		TimePeriod OTP;
		TimePeriod BTP;
		Object timeStampEnd;
		Object timeStampStart;
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		int[] actualWorkTime = WorkShift.getDuplication(OTP ,BTP, workShift.getWorkStart(), workShift.getWorkEnd());
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		List<Integer[]> actualOverworkTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getoverTimePeriod().size(); i++) {
			int[] duplication = Commons.getDuplication(
					timeStampStart,
					timeStampEnd,
					workShift.getoverTimePeriod().get(i),
					workShift.getoverTimePeriod().get(i));
			
			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualOverworkTimes.add(new Integer[] { duplication[0], duplication[1] });
		}
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<Integer[]> actualBreakTimes = new ArrayList<>();
		for (int i = 0; i < workShift.getBreakEnds().size(); i++) {
			int[] duplication = Commons.getDuplication(
					timeStampStart,
					timeStampEnd,
					workShift.getBreakEnds().get(i),
					workShift.getBreakEnds().get(i));
			
			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualBreakTimes.add(new Integer[] { duplication[0], duplication[1] });
		}
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		//List<Integer[]> actualWorkTimesWithoutBreak = new ArrayList<>();
		//for (int i = 0; i < actualBreakTimes.size(); i++) {
			//int workStart = actualWorkTime[0];
			//int workEnd = actualWorkTime[1];
			//int breakStart = actualBreakTimes.get(i)[0];
			//int breakEnd = actualBreakTimes.get(i)[1];
			
			//int[] subtraction = Commons.getSubtraction(workStart, workEnd, breakStart, breakEnd);
			
			//if (subtraction.length == 2) {
				//actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
				//break;
			//} else if (subtraction.length == 4) {
				//actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
				//actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[2], subtraction[3] });
				//break;
			//}
		//}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<Integer[]> actualOverworkTimesWithoutBreak = new ArrayList<>();
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				int overworkStart = actualOverworkTimes.get(i)[0];
				int overworkEnd = actualOverworkTimes.get(i)[1];
				int breakStart = actualBreakTimes.get(j)[0];
				int breakEnd = actualBreakTimes.get(j)[1];
				
				TimePeriod overworkPeriod = new TimePeriod(new TimeOfDay(overworkStart),new TimeOfDay(overworkEnd));
				TimePeriod breaktimePeriod = new TimePeriod(new TimeOfDay(breakStart),new TimeOfDay(breakEnd));
				
				TimePeriod subtraction = overworkPeriod.getSubtraction(breaktimePeriod);
				
				

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
		int sumWorkTime = 0;
		List<Integer[]> actualWorkTimesWithoutBreak;
		for (int i = 0; i < actualWorkTimesWithoutBreak.size(); i++) {
			int start = actualWorkTimesWithoutBreak.get(i)[0];
			int end = actualWorkTimesWithoutBreak.get(i)[1];
			System.out.println(TimeOfDay.formatTime(start) + "～" + TimeOfDay.formatTime(end));
			sumWorkTime += end - start;
		}
		System.out.println("合計: " + TimeOfDay.formatTime(sumWorkTime));
		
		System.out.println("残業時間");
		int sumOverworkTime = 0;
		for (int i = 0; i < actualOverworkTimesWithoutBreak.size(); i++) {
			int start = actualOverworkTimesWithoutBreak.get(i)[0];
			int end = actualOverworkTimesWithoutBreak.get(i)[1];
			System.out.println(TimeOfDay.formatTime(start) + "～" + TimeOfDay.formatTime(end));
			sumOverworkTime += end - start;
		}
		System.out.println("合計: " + TimeOfDay.formatTime(sumOverworkTime));
		
		System.out.println("休憩時間");
		int sumBreakTime = 0;
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			int start = actualBreakTimes.get(i)[0];
			int end = actualBreakTimes.get(i)[1];
			System.out.println(TimeOfDay.formatTime(start) + "～" + TimeOfDay.formatTime(end));
			sumBreakTime += end - start;
		}
		System.out.println("合計: " + TimeOfDay.formatTime(sumBreakTime));
	}
    
	public static void caluculate(int parseTimeString,int parseTimeString2,WorkShift workShift) {
		
	}
	
}
