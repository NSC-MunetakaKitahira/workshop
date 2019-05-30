package apricot.dom;

//import java.util.List;

public class TimePeriod {

		public int start;
        public int end;
        
        TimePeriod(int start, int end){
        	this.start = start;
        	this.end = end;
        }
        /**
    	 * start1/end1とstart2/end2の重複範囲を配列で返す。
    	 * 結果配列は、index:0が開始、index:1が終了。
    	 * 重複していない場合は、空の配列を返す。
    	 * @param start1
    	 * @param end1
    	 * @param start2
    	 * @param end2
    	 * @return
    	 */
    	public TimePeriod getDuplication(TimePeriod timePeriod) {
    		
    		// <--->
    		//       <--->
    		if (this.end < timePeriod.start) {
    			return null;
    		}
    		
    		//       <--->
    		// <--->
    		if (this.end < timePeriod.start) {
    			return null;
    		}
    		
    		// <--------->
    		//   <---->
    		if (this.start <= timePeriod.start && timePeriod.end <= this.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return  timePeriod.start, timePeriod.end ;
    		}

    		//   <---->
    		// <--------->
    		if (this.start <= timePeriod.start && timePeriod.end <= this.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.end;
    		}
    		
    		// <------>
    		//    <------>
    		if (this.start <= timePeriod.start && timePeriod.end <= this.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.end;
    		}

    		//    <------>
    		// <------>
    		if (this.start <= timePeriod.start && timePeriod.end <= this.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start,  timePeriod.end;
    		}
    		
    		throw new RuntimeException("たぶん他のケースは無い？");
    	}
  
    	/**
    	 * start1/end1から、start2/end2の範囲を除外した範囲を配列で返す。
    	 * 結果配列は、index:0が開始、index:1が終了。
    	 * start1/end1の範囲が2つに分断される場合、index:2が2つ目の範囲の開始、index:3がその終了。
    	 * start1/end1の範囲が完全に除外される場合、空の配列を返す。
    	 * @param start1
    	 * @param end1
    	 * @param start2
    	 * @param end2
    	 * @return
    	 */
    	
    	public static TimePeriod getSubtraction(TimePeriod timePeriod) {

    		// <--->
    		//       <--->
    		if (timePeriod.end < timePeriod.start) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.end;
    		}
    		
    		//       <--->
    		// <--->
    		if (timePeriod.end < timePeriod.start) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.end;
    		}
    		
    		// <--------->
    		// <---->
    		if (timePeriod.start == timePeriod.start && timePeriod.end < timePeriod.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return  timePeriod.end, timePeriod.end;
    		}

    		// <--------->
    		//      <---->
    		if (timePeriod.start < timePeriod.start && timePeriod.end == timePeriod.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.start ;
    		}
    		
    		// <--------->
    		//   <---->
    		if (timePeriod.start < timePeriod.start && timePeriod.end < timePeriod.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.start, timePeriod.end, timePeriod.end;
    		}

    		//   <---->
    		// <--------->
    		if (timePeriod.start <= timePeriod.start && timePeriod.end <= timePeriod.end) {
    			return null;
    		}
    		
    		// <------>
    		//    <------>
    		if (timePeriod.start <= timePeriod.start && timePeriod.end <= timePeriod.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.start, timePeriod.start;
    		}

    		//    <------>
    		// <------>
    		if (timePeriod.start <= timePeriod.start && timePeriod.end <= timePeriod.end) {
    			return new TimePeriod(timePeriod.start, timePeriod.end);
    			//return timePeriod.end,  timePeriod.end1;
    		}
    		
    		throw new RuntimeException("たぶん他のケースは無い？");
    	}
    	
    	public static String formatTime(int time) {
    		int minutes = time % 60;
    		int hours = time / 60;
    		return String.format("%d:%02d", hours, minutes);
    	}





}

