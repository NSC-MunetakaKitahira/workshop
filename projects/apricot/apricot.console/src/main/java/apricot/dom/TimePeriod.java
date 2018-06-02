package apricot.dom;

/**
 * 開始時刻と終了時刻からなる時間帯を表すクラス
 */
public class TimePeriod {

	/** 開始時刻 */
	private final TimeOfDay start;
	
	/** 終了時刻 */
	private final TimeOfDay end;
	
	public TimePeriod(TimeOfDay start, TimeOfDay end) {
		this.start = start;
		this.end = end;
	}

	public TimeOfDay getStart() {
		return this.start;
	}

	public TimeOfDay getEnd() {
		return this.end;
	}

	@Override
	public String toString() {
		return this.start.toString() + " - " + this.end.toString(); 
	}
}
