package apricot.dom;

import java.util.List;

/**
 * �A�Ǝ��ԑ�
 */
public class WorkShift {
	
	/**
	 * �n�Ǝ���
	 */
	private int workStart;
	
	/**
	 * �I�Ǝ����i�莞�j
	 */
	private int workEnd;
	
	/**
	 * �c�Ƃ̊J�n�����̃��X�g
	 */
	private List<Integer> overtimeStarts;
	
	/**
	 * �c�Ƃ̏I�������̃��X�g
	 * �J�n�����Ɠ����C���f�b�N�X�̗v�f���y�A
	 */
	private List<Integer> overtimeEnds;
	
	/**
	 * �x�e�̊J�n�����̃��X�g
	 */
	private List<Integer> breakStarts;
	
	/**
	 * �x�e�̏I�������̃��X�g
	 * �J�n�����Ɠ����C���f�b�N�X�̗v�f���y�A
	 */
	private List<Integer> breakEnds;
	
	public int getWorkStart() {
		return workStart;
	}
	
	public void setWorkStart(int workStart) {
		this.workStart = workStart;
	}
	
	public int getWorkEnd() {
		return workEnd;
	}
	
	public void setWorkEnd(int workEnd) {
		this.workEnd = workEnd;
	}
	
	public List<Integer> getOvertimeStarts() {
		return overtimeStarts;
	}
	
	public void setOvertimeStarts(List<Integer> overtimeStarts) {
		this.overtimeStarts = overtimeStarts;
	}
	
	public List<Integer> getOvertimeEnds() {
		return overtimeEnds;
	}
	
	public void setOvertimeEnds(List<Integer> overtimeEnds) {
		this.overtimeEnds = overtimeEnds;
	}
	
	public List<Integer> getBreakStarts() {
		return breakStarts;
	}
	
	public void setBreakStarts(List<Integer> breakStarts) {
		this.breakStarts = breakStarts;
	}
	public List<Integer> getBreakEnds() {
		return breakEnds;
	}
	
	public void setBreakEnds(List<Integer> breakEnds) {
		this.breakEnds = breakEnds;
	}
}
