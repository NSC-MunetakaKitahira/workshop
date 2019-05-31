package apricot.dom;

public class TimePeriod {
	
	public int start;
	public int end;
	
	public TimePeriod(int start,  int end) {
		if(end < start) {
			this.start = start;
			this.end = end;
			throw new RuntimeException();
		}

	}
	public int length() {
		return end - start;
	}
}
