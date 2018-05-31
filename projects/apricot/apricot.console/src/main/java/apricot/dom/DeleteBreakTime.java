package apricot.dom;
import java.util.ArrayList;
import java.util.List;

import apricot.dom.Overlap;

public class DeleteBreakTime {
	public static List<Integer[]> deleteBreakTime(List<Integer[]> breakTimes ,List<Integer[]> workTime) {
		List<Integer[]> resultTimes = new ArrayList<>();
		
		for (int i = 0; i < workTime.size(); i++) {		
			for (int j = 0; j < breakTimes.size(); j++) {
				
				int workStart = workTime.get(i)[0];
				int workEnd = workTime.get(i)[1];
				int breakStart = breakTimes.get(j)[0];
				int breakEnd = breakTimes.get(j)[1];
				
				if(workStart<=breakStart && workEnd>=breakEnd) {	
					int[] subTime = Overlap.getSubtraction(workStart, workEnd, breakStart, breakEnd);
					if (subTime.length == 2) {
						resultTimes.add(new Integer[] { subTime[0], subTime[1] });
					} else if (subTime.length == 4) {
						resultTimes.add(new Integer[] { subTime[0], subTime[1] });
						resultTimes.add(new Integer[] { subTime[2], subTime[3] });
					}
				}
			}
		}
		//勤務時間の重なりを排除
		removeExtraTime(resultTimes);

		return resultTimes;
	}


	public static void removeExtraTime(List<Integer[]> resultTimesRe) {
		for(int i=1;i+1<resultTimesRe.size();i++) {
			
			int workStartRe = resultTimesRe.get(i+1)[0];
			int workEndRe = resultTimesRe.get(i+1)[1];
			int breakStartRe = resultTimesRe.get(i)[0];
			int breakEndRe = resultTimesRe.get(i)[1];
			int[] subTimeRe = Overlap.getDuplication(workStartRe, workEndRe, breakStartRe, breakEndRe);
			
			resultTimesRe.set(i,new Integer[] { subTimeRe[0], subTimeRe[1] });
			resultTimesRe.remove(i+1);
		}
	}
}
