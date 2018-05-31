package apricot.dom;

public class Time {
	private final TimePair actualTime;	// 出退勤時間
	protected final WorkShift workShift;// 就業時間帯
	
	public Time(int startTime, int endTime, WorkShift workShift) {
		this.actualTime = new TimePair(startTime, endTime);
		this.workShift = workShift;
	}
	
	// 与えられた時間リストに対して重複範囲を返す
	public TimePair getDuplicationTime(TimePair times) {
		TimePair duplicationTimes = new TimePair();

		for (int i = 0; i < times.size(); i++) {
			
			// 重複範囲の調査
			int[] duplication = Commons.getDuplication(
					this.actualTime.getPair(0)[0], 
					this.actualTime.getPair(0)[1], 
					times.getPair(i)[0], 
					times.getPair(i)[1]);
			
			// 重複する範囲が存在するとき
			if (duplication.length != 0) {
				duplicationTimes.add(duplication[0], duplication[1]);
			}
		}
		return duplicationTimes;
	}
	
	// 与えられた時間リストに対して重複範囲を除外する
	public TimePair getSubtractionTime(TimePair target, TimePair times) {
		TimePair subtractionTime = new TimePair();
		
		for (int i = 0; i < target.size(); i++) {
			int[] targetTime = target.getPair(i);
			int[] time = times.getPair(i);
			
			// 除外できる範囲の調査
			int[] subtraction = Commons.getSubtraction(targetTime[0], targetTime[1], time[0], time[1]);
			
			// 除外する範囲が存在するとき
			if (subtraction.length != 0) {
				subtractionTime.add(subtraction[0], subtraction[1]);
				
				// 長さが4のときは2つに分断されている
				if (subtraction.length == 4) {
					subtractionTime.add(subtraction[2], subtraction[3]);
				}
				break;
			} 
		}
		return subtractionTime;
	}
	
	// 与えられた時刻を表示する
	public void printTime(TimePair times) {
		int totalTime = 0;	// 合計時間
		for (int i = 0; i < times.size(); i++) {
			int[] time = times.getPair(i);
			System.out.println(Commons.formatTime(time[0]) + "～" + Commons.formatTime(time[1]));
			totalTime += time[1] - time[0];
		}
		System.out.println("合計: " + Commons.formatTime(totalTime));
	}
}
