package apricot.dom.time;

import java.util.Optional;

public class TimePeriod {

	private final TimeOfDay start;
	
	private final TimeOfDay end;
	
	public TimePeriod(TimeOfDay start, TimeOfDay end) {
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
		
	public Minutes length() {
		return end.minutesFrom(start);
	}
	
	public String format() {
		return start.format() + " - " + end.format();
	}

	private boolean contains(TimeOfDay time) {
		return start.isBeforeOrEqual(time) && time.isBeforeOrEqual(end);
	}
	
	private static Optional<TimePeriod> optionalPeriod(TimeOfDay start, TimeOfDay end) {
		return start.isBefore(end)
				? Optional.of(new TimePeriod(start, end))
				: Optional.empty();
	}
}
