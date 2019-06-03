package apricot.dom;

public class PrintTime {
	
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
