package apricot.dom.time;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TimePeriods {

	private List<TimePeriod> periods;

	public TimePeriods(List<TimePeriod> periods) {
		this.periods = periods;
	}
	
	public TimePeriods getDuplications(TimePeriod other) {
		return new TimePeriods(periods.stream()
				.map(p -> p.getDuplication(other).orElse(null))
				.filter(d -> d != null)
				.collect(Collectors.toList()));
	}
	
	public void forEach(Consumer<TimePeriod> action) {
		periods.forEach(action);
	}

	public int calculateTime() {
		int total = 0;
		for (TimePeriod period : periods) {
			total += period.length();
		}
		return total;
	}
}
