package apricot.dom;

import java.util.Arrays;
//import java.util.List;

/**
 * テスト用のデータを作るだけのリポジトリ
 */
public class WorkShiftRepository {

	public static WorkShift get() {
	////////就業時間///////////////////////////////////////	
		String workStartSet = "8:30";
		String workEndSet = "17:30";
		
	////////残業時間//////////////////////////////////////	
		String overtimeStartsSet = "17:30";
		String overtimeEndsSet = "22:00";	
		
		String overtimeStartsSet2 = "22:00";
		String overtimeEndsSet2 = "24:00";
		
	////////休憩時間//////////////////////////////////////	
		String breakStartSet = "12:00";
		String breakEndSet = "13:00";	
		
		String breakStartSet2 = "17:30";
		String breakEndSet2 = "18:00";	
		
//		String breakStartSet3 = "14:30";
//		String breakEndSet3 = "15:00";
		
//		String breakStartSet4 = "15:30";
//		String breakEndSet4 = "16:00";
		
//		String breakStartSet5 = "16:30";
//		String breakEndSet5 = "16:30";
		
		WorkShift workShift = new WorkShift();
		
		workShift.setWorkStart(FormatChange.parseTimeString(workStartSet));
		workShift.setWorkEnd(FormatChange.parseTimeString(workEndSet));
		
		workShift.setOvertimeStarts(Arrays.asList(
					FormatChange.parseTimeString(overtimeStartsSet), FormatChange.parseTimeString(overtimeStartsSet2)));
		
		workShift.setOvertimeEnds(Arrays.asList(
					FormatChange.parseTimeString(overtimeEndsSet), FormatChange.parseTimeString(overtimeEndsSet2)));
		
		workShift.setBreakStarts(Arrays.asList(
					FormatChange.parseTimeString(breakStartSet)
					, FormatChange.parseTimeString(breakStartSet2)
//				 , FormatChange.parseTimeString(breakStartSet3)
//					   , FormatChange.parseTimeString(breakStartSet4)
//						, FormatChange.parseTimeString(breakStartSet5)
					));
		
		workShift.setBreakEnds(Arrays.asList(
					FormatChange.parseTimeString(breakEndSet)
					, FormatChange.parseTimeString(breakEndSet2) 
//					 , FormatChange.parseTimeString(breakEndSet3)
//					  , FormatChange.parseTimeString(breakEndSet4)					
//					   , FormatChange.parseTimeString(breakEndSet5)					
					));
		
		return workShift;
	}
}
