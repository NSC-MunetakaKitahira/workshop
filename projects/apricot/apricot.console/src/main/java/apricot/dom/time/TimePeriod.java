package apricot.dom.time;

import java.util.Optional;

import apricot.dom.CommonUtil;

public class TimePeriod {

	private final int start;
	
	private final int end;
	
	public TimePeriod(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public Optional<TimePeriod> getDuplication(TimePeriod other) {
		
		// <--------->
		//   <---->
		if (contains(other.start) && contains(other.end)) {
			return Optional.of(other);
		}

		//   <---->
		// <--------->
		if (other.contains(start) && other.contains(end)) {
			return Optional.of(this);
		}
		
		// <------>
		//    <------>
		if (contains(other.start) && other.contains(end)) {
			return optionalPeriod(other.start, this.end);
		}

		//    <------>
		// <------>
		if (other.contains(start) && contains(other.end)) {
			return optionalPeriod(this.start, other.end);
		}
		
		return Optional.empty();
	}
		
	public int length() {
		return end - start;
	}
	
	public String format() {
		return CommonUtil.format(start) + " - " + CommonUtil.format(end);
	}

	private boolean contains(int time) {
		return start <= time && time <= end;
	}
	
	private static Optional<TimePeriod> optionalPeriod(int start, int end) {
		return start < end
				? Optional.of(new TimePeriod(start, end))
				: Optional.empty();
	}
}
