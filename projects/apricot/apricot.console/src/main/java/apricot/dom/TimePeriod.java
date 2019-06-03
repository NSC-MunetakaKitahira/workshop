package apricot.dom;

//import java.util.List;

public class TimePeriod {

		public TimeOfDay start;
        public TimeOfDay end;
        
        public TimePeriod(TimeOfDay start, TimeOfDay end){
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
        
        public  static TimePeriod TP(TimeOfDay a, TimeOfDay b) {
        	return new TimePeriod(a,b); 
        }
        
        public static TimeOfDay TD(int n) {
        	return new TimeOfDay(n);
        }
        
    	public TimePeriod getDuplication(TimePeriod e) {
    		
    		
    		// <--->
    		//       <--->
    		if (this.end.num < e.start.num) {
    			return null;
    		}
    		
    		//       <--->
    		// <--->
    		if (this.end.num< e.start.num) {
    			return null;
    		}
    		
    		// <--------->
    		//   <---->
    		if (this.start.num <= e.start.num && e.end.num <= this.end.num) {
    			return TP(TD(this.start.num),TD(this.end.num));
    			//return  timePeriod.start, timePeriod.end ;
    		}

    		//   <---->
    		// <--------->
    		if (this.start.num <= e.start.num&& e.end.num <= this.end.num) {
    			return  TP(TD(this.start.num),TD(this.end.num));
    			//return timePeriod.start, timePeriod.end;
    		}
    		
    		// <------>
    		//    <------>
    		if (this.start.num <= e.start.num && e.end.num <= this.end.num) {
    			return  TP(TD(this.start.num), TD(this.end.num));
    			//return timePeriod.start, timePeriod.end;
    		}

    		//    <------>
    		// <------>
    		if (this.start.num <= e.start.num && e.end.num <= this.end.num) {
    			return TP(TD(this.start.num), TD(this.end.num));
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
    	
    	public TimePeriod getSubtraction(TimePeriod e) {
    		

    		// <--->
    		//       <--->
    		if (this.end.num < e.start.num) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return timePeriod.start, timePeriod.end;
    		}
    		
    		//       <--->
    		// <--->
    		if (this.end.num < e.start.num) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return timePeriod.start, timePeriod.end;
    		}
    		
    		// <--------->
    		// <---->
    		if (this.start == e.start && e.end.num < this.end.num) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return  timePeriod.end, timePeriod.end;
    		}

    		// <--------->
    		//      <---->
    		if (this.start.num < e.start.num && e.end == this.end) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return timePeriod.start, timePeriod.start ;
    		}
    		
    		// <--------->
    		//   <---->
    		if (this.start.num < e.start.num && e.end.num < this.end.num) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return timePeriod.start, timePeriod.start, timePeriod.end, timePeriod.end;
    		}

    		//   <---->
    		// <--------->
    		if (this.start.num <= e.start.num && e.end.num <= this.end.num) {
    			return null;
    		}
    		
    		// <------>
    		//    <------>
    		if (this.start.num <= e.start.num && e.end.num <= this.end.num) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return timePeriod.start, timePeriod.start;
    		}

    		//    <------>
    		// <------>
    		if (this.start.num <= e.start.num && e.end.num <= this.end.num) {
    			return TP(TD(e.start.num),TD(e.end.num));
    			//return timePeriod.end,  timePeriod.end1;
    		}
    		
    		throw new RuntimeException("たぶん他のケースは無い？");
    	}
    	
    	public  void actualWorkTime() {
    	
    		
			
    	}
    	
		public  int[] getSubtraction(int overworkStart, int overworkEnd, int breakStart, int breakEnd) {
			
			return null;
		}





}

