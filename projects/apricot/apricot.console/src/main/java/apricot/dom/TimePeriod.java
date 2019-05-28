package apricot.dom;

public class TimePeriod {
	private int start;
	private int end;
	
	public TimePeriod(int start, int end){
		this.start = start;
		this.end = end;
	}
	
//	 * 重複範囲を配列で返す。
//	 * 結果配列は、index:0が開始、index:1が終了。
//	 * 重複していない場合は、空の配列を返す。
	public int[] Duplicate(TimePeriod timePeriod) {
		
		// <--->
		//       <--->
		if (this.end < timePeriod.start) {
			return new int[] {};
		}
		
		//       <--->
		// <--->
		if (timePeriod.end < this.start) {
			return new int[] {};
		}
		
		// <--------->
		//   <---->
		if (this.start <= timePeriod.start && timePeriod.end <= this.end) {
			return new int[] { timePeriod.start, timePeriod.end };
		}

		//   <---->
		// <--------->
		if (timePeriod.start <= this.start && this.end <= timePeriod.end) {
			return new int[] { this.start, this.end };
		}
		
		// <------>
		//    <------>
		if (this.start <= timePeriod.start && this.end <= timePeriod.end) {
			return new int[] { timePeriod.start, this.end };
		}

		//    <------>
		// <------>
		if (timePeriod.start <= this.start && timePeriod.end <= this.end) {
			return new int[] { this.start,  timePeriod.end };
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	
//	 * start1/end1から、start2/end2の範囲を除外した範囲を配列で返す。
//	 * 結果配列は、index:0が開始、index:1が終了。
//	 * start1/end1の範囲が2つに分断される場合、index:2が2つ目の範囲の開始、index:3がその終了。
//	 * start1/end1の範囲が完全に除外される場合、空の配列を返す。
	public int[] Subtract(TimePeriod timePeriod) {

		// <--->
		//       <--->
		if (this.end < timePeriod.start) {
			return new int[] { this.start, this.end };
		}
		
		//       <--->
		// <--->
		if (timePeriod.end < this.start) {
			return new int[] { this.start, this.end };
		}
		
		// <--------->
		// <---->
		if (this.start == timePeriod.start && timePeriod.end < this.end) {
			return new int[] { timePeriod.end, this.end};
		}

		// <--------->
		//      <---->
		if (this.start < timePeriod.start && this.end == timePeriod.end) {
			return new int[] { this.start, timePeriod.start };
		}
		
		// <--------->
		//   <---->
		if (this.start < timePeriod.start && timePeriod.end < this.end) {
			return new int[] { this.start, timePeriod.start, timePeriod.end, this.end };
		}

		//   <---->
		// <--------->
		if (timePeriod.start <= this.start && this.end <= timePeriod.end) {
			return new int[] { };
		}
		
		// <------>
		//    <------>
		if (this.start <= timePeriod.start && this.end <= timePeriod.end) {
			return new int[] { this.start, timePeriod.start };
		}

		//    <------>
		// <------>
		if (timePeriod.start <= this.start && timePeriod.end <= this.end) {
			return new int[] { timePeriod.end,  this.end };
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
}