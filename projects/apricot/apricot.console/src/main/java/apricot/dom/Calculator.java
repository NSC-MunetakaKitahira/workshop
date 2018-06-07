package apricot.dom;

import java.util.ArrayList;
import java.util.List;
public class Calculator {
	
	private int timeStampStart;
	private int timeStampEnd;
	static WorkShift workShift;
	int[] actualWorkTime;
	List<Integer[]> actualOverworkTimes = new ArrayList<>();
	List<Integer[]> actualBreakTimes = new ArrayList<>();
	List<Integer[]> actualWorkTimesWithoutBreak = new ArrayList<>();
	List<Integer[]> actualOverworkTimesWithoutBreak = new ArrayList<>();
	TimePeriod actualTimes;
	
	public Calculator(TimePeriod actualTimes, WorkShift workShift){
		this.workShift=workShift;
		this.actualTimes=actualTimes;
	}

	public  void calculate() {
		
		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		actualWorkTime = Commons.getDuplication(actualTimes, workShift.getWorkTime());
		
		// 実残業時間帯: 出勤～退勤と、各残業時間帯との重複
		duplicationTime(actualOverworkTimes,workShift.getOverTime());
		
		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		duplicationTime(actualBreakTimes,workShift.getBreakTime());
		
		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		for (int i = 0; i < actualBreakTimes.size(); i++) {
			int workStart = actualWorkTime[0];
			int workEnd = actualWorkTime[1];
			int breakStart = actualBreakTimes.get(i)[0];
			int breakEnd = actualBreakTimes.get(i)[1];
			
			int[] subtraction = Commons.getSubtraction(workStart, workEnd, breakStart, breakEnd);
			
			if (subtraction.length == 2) {
				actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
				break;
			} else if (subtraction.length == 4) {
				actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[0], subtraction[1] });
				actualWorkTimesWithoutBreak.add(new Integer[] { subtraction[2], subtraction[3] });
				break;
			}
		}
		
		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		for (int i = 0; i < actualOverworkTimes.size(); i++) {
			for (int j = 0; j < actualBreakTimes.size(); j++) {
				int overworkStart = actualOverworkTimes.get(i)[0];
				int overworkEnd = actualOverworkTimes.get(i)[1];
				int breakStart = actualBreakTimes.get(j)[0];
				int breakEnd = actualBreakTimes.get(j)[1];
				
				int[] subtraction = Commons.getSubtraction(overworkStart, overworkEnd, breakStart, breakEnd);

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
		sumTime(actualWorkTimesWithoutBreak);
		
		System.out.println("残業時間");
		sumTime(actualOverworkTimesWithoutBreak);
		
		System.out.println("休憩時間");
		sumTime(actualBreakTimes);
	}

	public void duplicationTime(List<Integer[]> Times,List<TimePeriod> overTime) { //
		for (int i = 0; i < overTime.size(); i++) { 
			int[] duplication = Commons.getDuplication(
					actualTimes,
					overTime.get(i));
			
			if (duplication.length != 2) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			Times.add(new Integer[] { duplication[0], duplication[1] });
		}
//		System.out.println(actualTimes.getStartTime());
	}
	
	
	public  void sumTime(List<Integer[]> times) {
		int sumBreakTime = 0;
		for (int i = 0; i < times.size(); i++) {
			int start = times.get(i)[0];
			int end = times.get(i)[1];
			System.out.println(Commons.formatTime(start) + "～" + Commons.formatTime(end));
			sumBreakTime += end - start;
		}
		System.out.println("合計: " + Commons.formatTime(sumBreakTime));
	}
	

}
