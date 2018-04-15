package apricot.console;

import java.util.Scanner;

import apricot.dom.Calculator;
import apricot.dom.Commons;
import apricot.dom.WorkShift;
import apricot.dom.WorkShiftRepository;

public class ApricotConsole {

	/**
	 * �G���g���|�C���g
	 * 
	 * @param args �R�}���h���C�������͎g��Ȃ�
	 */
	public static void main(String[] args) {
		
		WorkShift workShift = WorkShiftRepository.get();
		String startTime;
		String endTime;

		System.out.println("������ \"8:30\" �̌`���œ��͂��Ă��������B");
		
		try(Scanner scan = new Scanner(System.in)) {
			System.out.print("�J�n���� = ");
			startTime = scan.nextLine();
	
			System.out.print("�I������ = ");
			endTime = scan.nextLine();
		}
		
		Calculator.calculate(Commons.parseTimeString(startTime), Commons.parseTimeString(endTime), workShift);
	}
	
}
