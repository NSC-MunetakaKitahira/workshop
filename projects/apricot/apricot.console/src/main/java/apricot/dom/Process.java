package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Process {

	public static void process(int startWork, int endWork) {
		//各時間帯の処理
		List<Integer[]> workTimes = new ArrayList<>();
		 				workTimes = WorkTime.workTime(startWork, endWork);
		 				
		List<Integer[]> overworkTimes = new ArrayList<>();
		 				overworkTimes = OverWorkTime.overWorkTime(startWork, endWork);
		 				
		List<Integer[]> breakTimes = new ArrayList<>();
		 				breakTimes =BreakWorkTime.breakWorkTime(startWork, endWork);
				
		// 就業時間帯と残業時間帯から休憩時間帯部分を除外
		List<Integer[]> actualWorkTime = new ArrayList<>(); 
						actualWorkTime = DeleteBreakTime.deleteBreakTime(breakTimes,workTimes);

		List<Integer[]> actualOverworkTimes = new ArrayList<>();
						actualOverworkTimes = DeleteBreakTime.deleteBreakTime(breakTimes,overworkTimes);

		//各時間帯を出力
		System.out.println("就業時間");
		outputTimes(actualWorkTime);

		System.out.println("残業時間");
		outputTimes(actualOverworkTimes);

		System.out.println("休憩時間");
		outputTimes(breakTimes);
		
	}
	public static void outputTimes(List<Integer[]> actualTimes) {
		
		int sumTime = 0;
		
		for (int i = 0; i < actualTimes.size(); i++) {
			
			int startTime = actualTimes.get(i)[0];
			int endTime = actualTimes.get(i)[1];
			
			System.out.println(FormatChange.parseTimeInt(startTime) + "～" + FormatChange.parseTimeInt(endTime));
			sumTime += endTime - startTime;
		}
		System.out.println("合計: " + FormatChange.parseTimeInt(sumTime));
	}
}
	
