package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class TimePeriod {
	private int start;
	private int end;
	
	public TimePeriod(int start, int end){
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return this.start;
	}
	
	public int getEnd() {
		return this.end;
	}
//	 * 重複範囲を配列で返す。
//	 * 結果配列は、index:0が開始、index:1が終了。
//	 * 重複していない場合は、空の配列を返す。
	public TimePeriod Duplicate(TimePeriod timePeriod) {
		
		// <--->
		//       <--->
		if (this.end < timePeriod.start) {
			return null;
		}
		
		//       <--->
		// <--->
		if (timePeriod.end < this.start) {
			return null;
		}
		
		// <--------->
		//   <---->
		if (this.start <= timePeriod.start && timePeriod.end <= this.end) {
			return new TimePeriod(timePeriod.start, timePeriod.end);
		}

		//   <---->
		// <--------->
		if (timePeriod.start <= this.start && this.end <= timePeriod.end) {
			return new TimePeriod(this.start, this.end);
		}
		
		// <------>
		//    <------>
		if (this.start <= timePeriod.start && this.end <= timePeriod.end) {
			return new TimePeriod(timePeriod.start, this.end);
		}

		//    <------>
		// <------>
		if (timePeriod.start <= this.start && timePeriod.end <= this.end) {
			return new TimePeriod(this.start,  timePeriod.end);
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	//
	public List<TimePeriod> DuplicateActualTimes(List<TimePeriod> getActualTimes){
		List<TimePeriod> actualTimes = new ArrayList<>();
		for (TimePeriod getActualTime :getActualTimes) {
			TimePeriod duplication = this.Duplicate(getActualTime);
			
			if (duplication == null) continue;

			// int配列をList<Integer[]>に追加できないので、Integer配列に変換
			actualTimes.add(duplication);
		}
		
		return actualTimes;
	}

//	 * start1/end1から、start2/end2の範囲を除外した範囲を配列で返す。
//	 * 結果配列は、index:0が開始、index:1が終了。
//	 * start1/end1の範囲が2つに分断される場合、index:2が2つ目の範囲の開始、index:3がその終了。
//	 * start1/end1の範囲が完全に除外される場合、空の配列を返す。
	public List<TimePeriod> Subtract(TimePeriod timePeriod) {
		List<TimePeriod> sub = new ArrayList<>();
		
		// <--->
		//       <--->
		if (this.end < timePeriod.start) {
			sub.add( new TimePeriod(this.start, this.end));
			return sub;
		}
		
		//       <--->
		// <--->
		if (timePeriod.end < this.start) {
			sub.add( new TimePeriod(this.start, this.end));
			return sub;
		}
		
		// <--------->
		// <---->
		if (this.start == timePeriod.start && timePeriod.end < this.end) {
			sub.add( new TimePeriod(timePeriod.end, this.end));
			return sub;
		}

		// <--------->
		//      <---->
		if (this.start < timePeriod.start && this.end == timePeriod.end) {
			sub.add( new TimePeriod(this.start, timePeriod.start));
			return sub;
		}
		
		// <--------->
		//   <---->
		if (this.start < timePeriod.start && timePeriod.end < this.end) {
			sub.add( new TimePeriod(this.start, timePeriod.start));
			sub.add( new TimePeriod(timePeriod.end, this.end));
			return sub;
		}

		//   <---->
		// <--------->
		if (timePeriod.start <= this.start && this.end <= timePeriod.end) {
			return null;
		}
		
		// <------>
		//    <------>
		if (this.start <= timePeriod.start && this.end <= timePeriod.end) {
			sub.add( new TimePeriod(this.start, timePeriod.start));
			return sub;
		}

		//    <------>
		// <------>
		if (timePeriod.start <= this.start && timePeriod.end <= this.end) {
			sub.add( new TimePeriod(timePeriod.end,  this.end));
			return sub;
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}

}