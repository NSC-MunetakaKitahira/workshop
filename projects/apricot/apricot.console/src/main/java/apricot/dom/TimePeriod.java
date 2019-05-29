package apricot.dom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	
	public int minutesOfLength() {
		return end.minutesFrom(start);
	}

	/**
	 * start1/end1とstart2/end2の重複範囲を返す。
	 * 重複していない場合は、nullを返す。
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public TimePeriod getDuplicationWith(TimePeriod other) {

		// <--->
		//       <--->
		if (this.end.isBeforeOrEqual(other.start)) {
			return null;
		}
		
		//       <--->
		// <--->
		if (other.end.isBeforeOrEqual(this.start)){
			return null;
		}
		
		// <--------->
		//   <---->
		if (this.start.isBeforeOrEqual(other.start) && other.end.isBeforeOrEqual(this.end)) {
			return other;
		}

		//   <---->
		// <--------->
		if (other.start.isBeforeOrEqual(this.start) && this.end.isBeforeOrEqual(other.end)) {
			return this;
		}
		
		// <------>
		//    <------>
		if (this.start.isBeforeOrEqual(other.start) && this.end.isBeforeOrEqual(other.end)) {
			return new TimePeriod(other.start, this.end);
		}

		//    <------>
		// <------>
		if (other.start.isBeforeOrEqual(this.start) && other.end.isBeforeOrEqual(this.end)) {
			return new TimePeriod(this.start, other.end);
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	public List<TimePeriod> subtract(TimePeriod other) {

		// 重複なし
		if (this.getDuplicationWith(other) == null) {
			return Arrays.asList(this);
		}
		
		// <--------->
		// <---->
		if (this.start.equals(other.start) && other.end.isBefore(this.end)) {
			return Arrays.asList(new TimePeriod(other.end, this.end));
		}

		// <--------->
		//      <---->
		if (this.start.isBefore(other.start) && this.end.isBefore(other.end)) {
			return Arrays.asList(new TimePeriod(this.start, other.start));
		}
		
		// <--------->
		//   <---->
		if (this.start.isBefore(other.start) && other.end.isBefore(this.end)) {
			return Arrays.asList(
					new TimePeriod(this.start, other.start),
					new TimePeriod(other.end, this.end));
		}

		//   <---->
		// <--------->
		if (other.getStart().isBeforeOrEqual(this.start) && this.end.isBeforeOrEqual(other.end)) {
			return Collections.emptyList();
		}
		
		// <------>
		//    <------>
		if (this.start.isBeforeOrEqual(other.start) && this.end.isBeforeOrEqual(other.end)) {
			return Arrays.asList(new TimePeriod(this.start, other.start));
		}

		//    <------>
		// <------>
		if (other.start.isBeforeOrEqual(this.start) && other.end.isBeforeOrEqual(this.end)) {
			return Arrays.asList(new TimePeriod(other.end, this.end));
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	public String format() {
		return start.format() + "～" + end.format();
	}
	
	@Override
	public String toString() {
		return format();
	}
}
