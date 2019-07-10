package tsurisen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MoneyType {

	Coin_1(1),
	Coin_5(5),
	Coin_10(10),
	Coin_50(50),
	Coin_100(100),
	Coin_500(500),
	Bill_1000(1000),
	Bill_5000(5000),
	Bill_10000(10000),
	
	;
	
	public final int value;

	private MoneyType(int value) {
		this.value = value;
	}
	
	public static List<MoneyType> typesAscending() {
		return Arrays.asList(values()).stream()
				.sorted((a, b) -> Integer.compare(a.value, b.value))
				.collect(Collectors.toList());
	}
	
	public static List<MoneyType> typesDescending() {
		return Arrays.asList(values()).stream()
				.sorted((a, b) -> Integer.compare(b.value, a.value))
				.collect(Collectors.toList());
	}
}
