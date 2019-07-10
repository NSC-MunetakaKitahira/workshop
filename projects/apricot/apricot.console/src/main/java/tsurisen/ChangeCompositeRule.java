package tsurisen;

import java.util.Map;
import java.util.stream.Collectors;

public class ChangeCompositeRule {

	public static Moneys createChangeMoneysOf(int changeAmount) {
		
		Map<MoneyType, Integer> result = MoneyType.typesAscending().stream()
				.collect(Collectors.toMap(mt -> mt, mt -> 0));
		
		for (MoneyType moneyType : MoneyType.typesDescending()) {
			int count = changeAmount / moneyType.value;
			result.put(moneyType, count);
			changeAmount -= moneyType.value * count;
		}
		
		return new Moneys(result);
	}
}
