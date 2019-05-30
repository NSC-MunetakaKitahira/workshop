package apricot.dom;

public class TimePeriod {
	public final int start;
	public final int end;

	// スタートとエンドを一緒にする
	public TimePeriod(int start, int end) {

		if (start > end) {
			throw new RuntimeException();
		}
		this.start = start;
		this.end = end;
	}

//	public void getDuplication(TimePeriod p2) {
//		// TODO Auto-generated method stub
//
//	}

}
