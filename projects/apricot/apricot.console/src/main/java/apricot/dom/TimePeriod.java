package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
	 * 自身とotherの重複する時間帯を返す
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
	
	public List<TimePeriod> getDuplicationWith(List<TimePeriod> others) {
		return others.stream()
				.map(p -> p.getDuplicationWith(this))
				.filter(dup -> dup != null)
				.collect(Collectors.toList());
	}
	
	/**
	 * 自身からotherを取り除いた時間帯を返す（2つできることもある）
	 */
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
	
	/**
	 * 自身からothersすべてを取り除いた時間帯を返す
	 */
	public List<TimePeriod> subtract(List<TimePeriod> others) {
		
		// 全く重複しない時間帯は関係無いので除外し、時刻の早い順にソート
		List<TimePeriod> subtractors = others.stream()
				.filter(o -> o.start.isBefore(this.end))
				.sorted((p1, p2) -> p1.start.compareTo(p2.start))
				.collect(Collectors.toList());;
		
		return subtractRecursive(this, subtractors);
	}
	
	// 複数subtractを再帰で処理
	private static List<TimePeriod> subtractRecursive(TimePeriod base, List<TimePeriod> subtractors) {

		// subtractorsが空なら、baseがそのまま残る
		if (subtractors.isEmpty()) {
			return Arrays.asList(base);
		}
		
		TimePeriod headSubtractor = subtractors.get(0);
		List<TimePeriod> subtracted = base.subtract(headSubtractor);
		
		// baseが完全に消されてしまった
		if (subtracted.isEmpty()) {
			return Collections.emptyList();
		}
		
		if (subtracted.size() == 1) {
			// headSubtractorの前にしか残らないなら、これで終わり
			if (subtracted.get(0).end.isBeforeOrEqual(headSubtractor.start)) {
				return subtracted;
			}
			
			// 後ろに残っているなら、再びそこから残りのsubtractorsを取り除く
			return subtractRecursive(subtracted.get(0), subtractors.subList(1, subtractors.size()));
		}
		
		if (subtracted.size() == 2) {
			// 2つに分断されたなら、手前の1つと、後ろに残った分のsubtractの結果を結合して返す
			List<TimePeriod> results = new ArrayList<>();
			results.add(subtracted.get(0));
			results.addAll(subtractRecursive(subtracted.get(1), subtractors.subList(1, subtractors.size())));
			return results;
		}
		
		// ここには来ないはず
		throw new RuntimeException("なんかバグってる!");
	}
	
	public String format() {
		return start.format() + "～" + end.format();
	}
	
	@Override
	public String toString() {
		return format();
	}

	// ユニットテストでassertEqualsに放り込むために実装
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		return equals((TimePeriod) obj);
	}
	
	public boolean equals(TimePeriod other) {
		return start.equals(other.start) && end.equals(other.end);
	}

	// equals(Object obj)を独自に実装した場合、hashCodeもそれに合わせた実装が必要・・・というJavaのルールがあるが、今回の課題では本質ではないので気にしなくて良い。
	// 実際には、ashSetやHashMapなど、ハッシュアルゴリズムに放り込む場合に必要になる。
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}
	
}
