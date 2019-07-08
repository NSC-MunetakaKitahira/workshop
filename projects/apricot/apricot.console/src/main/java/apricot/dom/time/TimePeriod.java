package apricot.dom.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		if (this.start <= other.start && other.end <= this.end) {
			return Optional.of(other);
		}

		//   <---->
		// <--------->
		if (other.start <= this.start && this.end <= other.end) {
			return Optional.of(this);
		}
		
		// <------>
		//    <------>
		if (this.start <= other.start && other.start < this.end && this.end <= other.end) {
			return Optional.of(new TimePeriod(other.start, this.end));
		}

		//    <------>
		// <------>
		if (other.start <= this.start && this.start < other.end && other.end <= this.end) {
			return Optional.of(new TimePeriod(this.start, other.end));
		}
		
		return Optional.empty();
	}
	
	public List<TimePeriod> getDuplications(List<TimePeriod> others) {
		return others.stream()
				.map(o -> getDuplication(o))
				.filter(d -> d.isPresent())
				.map(d -> d.get())
				.collect(Collectors.toList());
	}
	
	public int length() {
		return end - start;
	}
	
	public String format() {
		return CommonUtil.format(start) + " - " + CommonUtil.format(end);
	}

}
