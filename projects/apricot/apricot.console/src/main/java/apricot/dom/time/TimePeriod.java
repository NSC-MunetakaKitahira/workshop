package apricot.dom.time;

import java.util.Optional;

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
	
	public TimeOfDay start() {
		return start;
	}
	
	public TimeOfDay end() {
		return end;
	}
	
	public Minutes minutesOfLength() {
		return end.minutesFrom(start);
	}
	
	public boolean isAfter(TimeOfDay target) {
		return start.isAfter(target);
	}
	
	public boolean isAfterOrEqual(TimeOfDay target) {
		return start.isAfterOrEqual(target);
	}
	
	public boolean isBefore(TimeOfDay target) {
		return start.isBefore(target);
	}
	
	public boolean isBeforeOrEqual(TimeOfDay target) {
		return start.isBeforeOrEqual(target);
	}
	
	public boolean contains(TimeOfDay target) {
		return start.isBeforeOrEqual(target) && target.isBeforeOrEqual(end);
	}
	
	public boolean contains(TimePeriod target) {
		return contains(target.start) && contains(target.end);
	}
	
	/**
	 * 自身とotherの重複する時間帯を返す
	 */
	public Optional<TimePeriod> getDuplicationWith(TimePeriod target) {

		// <--------->
		//   <---->
		if (this.contains(target)) {
			return Optional.of(target);
		}

		//   <---->
		// <--------->
		if (target.contains(this)) {
			return Optional.of(this);
		}
		
		// <------>
		//    <------>
		if (this.canSplitBy(target.start) && target.canSplitBy(this.end)) {
			return Optional.of(period(target.start, this.end));
		}

		//    <------>
		// <------>
		if (target.canSplitBy(this.start) && this.canSplitBy(target.end)) {
			return Optional.of(period(this.start, target.end));
		}
		
		return Optional.empty();
	}
	
	public TimePeriods getDuplicationWith(TimePeriods others) {
		
		return others.stream()
				.map(o -> o.getDuplicationWith(this))
				.filter(o -> o.isPresent())
				.map(o -> o.get())
				.collect(TimePeriods.collector());
	}
	
	/**
	 * 自身からsubtractorを取り除いた時間帯を返す
	 */
	public Subtracted subtract(TimePeriod subtractor) {

		//   <---->
		// <--------->
		if (subtractor.contains(this)) {
			return Subtracted.empty();
		}
		
		// <--------->
		//   <---->
		if (this.contains(subtractor)) {
			return Subtracted.splitted(
					period(this.start, subtractor.start),
					period(subtractor.end, this.end));
		}
		
		// <------>
		//    <------>
		if (this.contains(subtractor.start) && !this.contains(subtractor.end)) {
			return Subtracted.headOnly(period(this.start, subtractor.start));
		}

		//    <------>
		// <------>
		if (!this.contains(subtractor.start) && this.contains(subtractor.end)) {
			return Subtracted.tailOnly(period(subtractor.end, this.end));
		}
		
		// 重複なし
		return Subtracted.headOnly(this);
	}
	
	/**
	 * 自身からsubtractorsすべてを取り除いた時間帯を返す
	 */
	public TimePeriods subtract(TimePeriods subtractors) {
		
		// subtractorsが空なら、baseがそのまま残る
		if (subtractors.isEmpty()) {
			return TimePeriods.of(this);
		}
		
		// とりあえず最初のsubtractorの分を取り除く
		Subtracted subtracted = this.subtract(subtractors.get(0));

		// subtractorsの前にしか残らないなら、これで終わり
		if (subtracted.isHeadOnly()) {
			return subtracted.toTimePeriods();
		}
		
		// 後ろにのみ残っているなら、再びそこから残りのsubtractorsを取り除く
		if (subtracted.isTailOnly()) {
			return subtracted.tail.subtract(subtractors.skip(1));
		}
		
		// 2つに分断されたなら、手前の1つと、後ろに残った分のsubtractの結果を結合して返す
		if (subtracted.isSplitted()) {
			TimePeriods tailSubtracted = subtracted.tail.subtract(subtractors.skip(1));
			return TimePeriods.of(subtracted.head).add(tailSubtracted);
		}
		
		// baseが完全に消されてしまった
		return TimePeriods.empty();
	}

	/**
	 * subtractの結果を表すクラス
	 */
	public static class Subtracted {
		
		/** subtractorより前に残った部分 */
		public final TimePeriod head;
		
		/** subtractorより後ろに残った部分 */
		public final TimePeriod tail;
		
		private Subtracted(TimePeriod head, TimePeriod tail) {
			this.head = head;
			this.tail = tail;
		}
		
		public static Subtracted empty() {
			return new Subtracted(null, null);
		}
		
		public static Subtracted headOnly(TimePeriod head) {
			return new Subtracted(head.isEmpty() ? null : head, null);
		}
		
		public static Subtracted tailOnly(TimePeriod tail) {
			return new Subtracted(null, tail.isEmpty() ? null : tail);
		}
		
		public static Subtracted splitted(TimePeriod head, TimePeriod tail) {
			return new Subtracted(
					head.isEmpty() ? null : head,
					tail.isEmpty() ? null : tail);
		}
		
		public boolean isEmpty() {
			return head == null && tail == null;
		}
		
		public boolean isSplitted() {
			return head != null && tail != null;
		}
		
		public boolean isHeadOnly() {
			return head != null && tail == null;
		}
		
		public boolean isTailOnly() {
			return head == null && tail != null;
		}
		
		public TimePeriods toTimePeriods() {
			if (isEmpty()) {
				return TimePeriods.empty();
			}
			if (isSplitted()) {
				return TimePeriods.of(head, tail);
			}
			return TimePeriods.of(head);
		}
	}
	
	public String format() {
		return start.format() + "～" + end.format();
	}
	
	@Override
	public String toString() {
		return format();
	}

	private boolean isEmpty() {
		return start.equals(end);
	}
	
	private boolean canSplitBy(TimeOfDay splitter) {
		return this.start.isBefore(splitter) && splitter.isBefore(this.end);
	}

	private static TimePeriod period(TimeOfDay start, TimeOfDay end) {
		return new TimePeriod(start, end);
	}
	
	// ユニットテストでassertEqualsに放り込むために実装
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return equals((TimePeriod) obj);
	}
	
	public boolean equals(TimePeriod other) {
		return start.equals(other.start) && end.equals(other.end);
	}

	// equals(Object obj)を独自に実装した場合、hashCodeもそれに合わせた実装が必要・・・というJavaのルールがあるが、今回の課題では本質ではないので気にしなくて良い。
	// 実際には、ashSetやHashMapなど、ハッシュアルゴリズムに放り込む場合に必要になる。
	@Override
	public int hashCode() {
		// ↓自動生成
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}
	
}
