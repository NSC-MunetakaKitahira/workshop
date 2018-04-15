package apricot.dom;

import java.util.List;

public class Commons {

	public static int parseTimeString(String timeString) {
		timeString = timeString.replace(":", "");
		int time = Integer.parseInt(timeString);
		int minutes = time % 100;
		int hours = time / 100;
		return hours * 60 + minutes;
	}
	
	/**
	 * start1/end1��start2/end2�̏d���͈͂�z��ŕԂ��B
	 * ���ʔz��́Aindex:0���J�n�Aindex:1���I���B
	 * �d�����Ă��Ȃ��ꍇ�́A��̔z���Ԃ��B
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static int[] getDuplication(int start1, int end1, int start2, int end2) {

		// <--->
		//       <--->
		if (end1 < start2) {
			return new int[] {};
		}
		
		//       <--->
		// <--->
		if (end2 < start1) {
			return new int[] {};
		}
		
		// <--------->
		//   <---->
		if (start1 <= start2 && end2 <= end1) {
			return new int[] { start2, end2 };
		}

		//   <---->
		// <--------->
		if (start2 <= start1 && end1 <= end2) {
			return new int[] { start1, end1 };
		}
		
		// <------>
		//    <------>
		if (start1 <= start2 && end1 <= end2) {
			return new int[] { start2, end1 };
		}

		//    <------>
		// <------>
		if (start2 <= start1 && end2 <= end1) {
			return new int[] { start1,  end2 };
		}
		
		throw new RuntimeException("���Ԃ񑼂̃P�[�X�͖����H");
	}
	
	/**
	 * start1/end1����Astart2/end2�͈̔͂����O�����͈͂�z��ŕԂ��B
	 * ���ʔz��́Aindex:0���J�n�Aindex:1���I���B
	 * start1/end1�͈̔͂�2�ɕ��f�����ꍇ�Aindex:2��2�ڂ͈̔͂̊J�n�Aindex:3�����̏I���B
	 * start1/end1�͈̔͂����S�ɏ��O�����ꍇ�A��̔z���Ԃ��B
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public static int[] getSubtraction(int start1, int end1, int start2, int end2) {

		// <--->
		//       <--->
		if (end1 < start2) {
			return new int[] { start1, end1 };
		}
		
		//       <--->
		// <--->
		if (end2 < start1) {
			return new int[] { start1, end1 };
		}
		
		// <--------->
		// <---->
		if (start1 == start2 && end2 < end1) {
			return new int[] { end2, end1};
		}

		// <--------->
		//      <---->
		if (start1 < start2 && end1 == end2) {
			return new int[] { start1, start2 };
		}
		
		// <--------->
		//   <---->
		if (start1 < start2 && end2 < end1) {
			return new int[] { start1, start2, end2, end1 };
		}

		//   <---->
		// <--------->
		if (start2 <= start1 && end1 <= end2) {
			return new int[] { };
		}
		
		// <------>
		//    <------>
		if (start1 <= start2 && end1 <= end2) {
			return new int[] { start1, start2 };
		}

		//    <------>
		// <------>
		if (start2 <= start1 && end2 <= end1) {
			return new int[] { end2,  end1 };
		}
		
		throw new RuntimeException("���Ԃ񑼂̃P�[�X�͖����H");
	}
	
	public static String formatTime(int time) {
		int minutes = time % 60;
		int hours = time / 60;
		return String.format("%d:%02d", hours, minutes);
	}
}
