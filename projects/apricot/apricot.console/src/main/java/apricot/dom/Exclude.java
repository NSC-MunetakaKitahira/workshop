package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Exclude {

	public static List<TimePeriod> actualWithoutBreaak(TimePeriod actualWorkTimePeriod, List<TimePeriod> actualBreakTimes){
		List<TimePeriod> actualWorkTimesWithoutBreak = new ArrayList<>();
		
		for(TimePeriod actualBreakTime: actualBreakTimes) {
			List<TimePeriod> subtractions = actualWorkTimePeriod.Subtract(actualBreakTime);

			for(TimePeriod subtraction: subtractions) {
				actualWorkTimesWithoutBreak.add(subtraction);
			}
			
			
//			if (subtraction.size() == 1) {
//				actualWorkTimesWithoutBreak.add(subtraction.get(0));
//				break;
//			} else if (subtraction.size() == 2) {
//				actualWorkTimesWithoutBreak.add(subtraction.get(0));
//				actualWorkTimesWithoutBreak.add(subtraction.get(1));
//				break;
//			}
		}
		
		return actualWorkTimesWithoutBreak;
	}
	
	
	public static List<TimePeriod> actualOverWithoutbreak(List<TimePeriod> actualOverworkTimes, List<TimePeriod> actualBreakTimes){
		List<TimePeriod> actualOverworkTimesWithoutBreak = new ArrayList<>();
		
		for (TimePeriod actualOverworkTime : actualOverworkTimes) {
			for (TimePeriod actualBreakTime : actualBreakTimes) {
				TimePeriod actualOverworkTimesPeriod = actualOverworkTime;
				TimePeriod actualBreakTimesPeriod = actualBreakTime;
				
				List<TimePeriod> subtractions = actualOverworkTimesPeriod.Subtract(actualBreakTimesPeriod);
				
				for(TimePeriod subtraction: subtractions) {
					actualOverworkTimesWithoutBreak.add(subtraction);
				}
				
//				if (subtraction.size() == 1) {
//					actualOverworkTimesWithoutBreak.add(subtraction.get(0));
//					break;
//				} else if (subtraction.size() == 2) {
//					actualOverworkTimesWithoutBreak.add(subtraction.get(0));
//					actualOverworkTimesWithoutBreak.add(subtraction.get(1));
//					break;
//				}
			}
		}
		
		return actualOverworkTimesWithoutBreak;
	}
}
