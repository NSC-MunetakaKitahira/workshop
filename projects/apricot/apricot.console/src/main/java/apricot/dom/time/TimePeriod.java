package apricot.dom.time;

import java.util.ArrayList;
import java.util.List;
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
		
		TimePeriod result;

		// <------->
		// <------->
		if (this.start == other.start && this.end == other.end) {
			result = this;
		}
		
		// <------->
		//    <--..
		else if (this.start <= other.start && other.start < this.end) {
			
			// <------->
			//    <---->
			if (other.end <= this.end) {
				result = other;
			}

			// <------->
			//    <------>
			else {
				result = new TimePeriod(other.start, this.end);
			}
		}
		
		//    <--..
		// <------->
		else if (other.start <= this.start && this.start < other.end) {

			//    <---->
			// <------->
			if (this.end <= other.end) {
				result = this;
			}

			//    <------>
			// <------->
			else {
				result = new TimePeriod(this.start, other.end);
			}
		}
		
		else {
			result = null;
		}

		return Optional.ofNullable(result);
	}
	
	public List<TimePeriod> getDuplications(List<TimePeriod> others) {
		
		List<TimePeriod> results = new ArrayList<>();
		for (TimePeriod other : others) {
			Optional<TimePeriod> duplication = getDuplication(other);
			if (duplication.isPresent()) {
				results.add(duplication.get());
			}
		}
		
		return results;
	}
	
	public int length() {
		return end - start;
	}
	
	public String format() {
		return CommonUtil.format(start) + " - " + CommonUtil.format(end);
	}

}
