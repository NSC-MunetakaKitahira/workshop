package apricot.dom;

public class TimePeriod {
	
	public final int start;
	public final int end;
	
	public TimePeriod(int start,  int end) {
		if(end < start) {
			this.start = start;
			this.end = end;
		}
		throw new RuntimeException();
	}
	public int length() {
		return end - start;
	}
}
