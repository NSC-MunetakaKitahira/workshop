package apricot.dom;

import java.util.List;

public class ShowResult {
	
	//結果を出力
	public static void show(TimeOfWork actualTime) {
		System.out.println("就業時間");
		System.out.println("合計: " + sumEachTime(actualTime.actualWorkTimes).formatTime());		
		System.out.println("残業時間");
		System.out.println("合計: " + sumEachTime(actualTime.actualOverworkTimes).formatTime());		
		System.out.println("休憩時間");
		System.out.println("合計: " + sumEachTime(actualTime.actualBreakTimes).formatTime());		
	}
	
	//合計時間を求め,表示する
	private static TimeOfDay sumEachTime(List<TimePeriod> times) {
		int sumTime = 0;
		for (TimePeriod time: times) {
			if(time.length.minute > 0) {
			System.out.println(time.start.formatTime() + "-" + time.end.formatTime());
			}
			sumTime += time.length.minute;
		}
		return new TimeOfDay(sumTime);
	}
}
