package apricot.dom.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TimePeriodsのコレクション
 */
public class TimePeriods {

	/** 開始時刻の早い順にソートされている */
	private final List<TimePeriod> periods;

	public TimePeriods(List<TimePeriod> periods) {
		this.periods = periods.stream()
				.sorted((a, b) -> a.start().compareTo(b.start()))
				.collect(Collectors.toList());
	}
	
	/**
	 * 1つ以上のTimePeriodからコレクションを作る
	 * @param periods
	 * @return
	 */
	public static TimePeriods of(TimePeriod... periods) {
		return new TimePeriods(Arrays.asList(periods));
	}
	
	/**
	 * 空のコレクションを作る
	 * @return
	 */
	public static TimePeriods empty() {
		return new TimePeriods(Collections.emptyList());
	}
	
	/**
	 * 合計時間を求める
	 * @return
	 */
	public Minutes sumMinutesOfLength() {
		return list().stream()
				.map(tp -> tp.minutesOfLength())
				.reduce((a, b) -> a.add(b))
				.orElse(Minutes.zero());
	}
	
	/**
	 * TimePeriodをコレクションに追加する
	 * @param period
	 * @return
	 */
	public TimePeriods add(TimePeriod period) {
		List<TimePeriod> list = list();
		list.add(period);
		return new TimePeriods(list);
	}
	
	/**
	 * TimePeriodsをコレクションに追加する
	 * @param periods
	 * @return
	 */
	public TimePeriods add(TimePeriods periods) {
		List<TimePeriod> list = list();
		list.addAll(periods.list());
		return new TimePeriods(list);
	}
	
	/**
	 * 指定した数のTimePeriodを先頭（開始時刻が早いもの）から除外した新しいコレクションを作る
	 * @param countToSkip
	 * @return
	 */
	public TimePeriods skip(int countToSkip) {
		return new TimePeriods(periods.subList(1, periods.size()));
	}
	
	/**
	 * 指定したsubtractorを除外した結果を返す
	 * @param subtractor
	 * @return
	 */
	public TimePeriods subtract(TimePeriod subtractor) {
		return subtract(TimePeriods.of(subtractor));
	}
	
	/**
	 * 指定したsubtractorsを除外した結果を返す
	 * @param subtractors
	 * @return
	 */
	public TimePeriods subtract(TimePeriods subtractors) {
		if (subtractors.isEmpty()) {
			return this;
		}
		
		return this.stream()
				.flatMap(t -> t.subtract(subtractors).stream())
				.collect(TimePeriods.collector());
	}
	
	/**
	 * コレクションに含まれる要素の数を返す
	 * @return
	 */
	public int size() {
		return periods.size();
	}
	
	/**
	 * 指定したインデックスの要素を返す
	 * @param index
	 * @return
	 */
	public TimePeriod get(int index) {
		return periods.get(index);
	}
	
	/**
	 * 要素が空ならtrueを返す
	 * @return
	 */
	public boolean isEmpty() {
		return periods.isEmpty();
	}
	
	public Stream<TimePeriod> stream() {
		return periods.stream();
	}
	
	public static Collector<TimePeriod, ?, TimePeriods> collector() {
		return new TimePeriodCollector();
	}
	
	private List<TimePeriod> list() {
		return new ArrayList<>(periods);
	}
	
	private static class TimePeriodCollector implements Collector<TimePeriod, List<TimePeriod>, TimePeriods> {

		@Override
		public BiConsumer<List<TimePeriod>, TimePeriod> accumulator() {
			return (list, period) -> list.add(period);
		}

		@Override
		public Set<Characteristics> characteristics() {
			return Collections.emptySet();
		}

		@Override
		public BinaryOperator<List<TimePeriod>> combiner() {
			return (list1, list2) -> { throw new RuntimeException("not supported"); };
		}

		@Override
		public Function<List<TimePeriod>, TimePeriods> finisher() {
			return list -> new TimePeriods(list);
		}

		@Override
		public Supplier<List<TimePeriod>> supplier() {
			return () -> new ArrayList<>();
		}
		
	}
}
