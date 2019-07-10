package tsurisen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Moneys {

	private final Map<MoneyType, Integer> quantities;

	public Moneys(Map<MoneyType, Integer> quantities) {
		this.quantities = new HashMap<>();
		Arrays.asList(MoneyType.values()).forEach(moneyType -> {
			Integer quantity = quantities.get(moneyType);
			this.quantities.put(moneyType, quantity == null ? 0 : quantity);
		});
	}
	
	public int amount() {
		return quantities.entrySet().stream()
				.collect(Collectors.summingInt(es -> es.getValue() * es.getKey().value));
	}
	
	public int quantityOf(MoneyType moneyType) {
		return quantities.get(moneyType);
	}
	
	public Moneys add(Moneys toAdd) {
		Map<MoneyType, Integer> added = new HashMap<>();
		Arrays.asList(MoneyType.values()).forEach(moneyType -> {
			added.put(moneyType, quantityOf(moneyType) + toAdd.quantityOf(moneyType));
		});
		return new Moneys(added);
	}
	
	public Moneys subtract(Moneys toSubtract) {
		if (!contains(toSubtract)) {
			throw new RuntimeException("can not subtract!");
		}
		
		Map<MoneyType, Integer> subtracted = new HashMap<>();
		Arrays.asList(MoneyType.values()).forEach(moneyType -> {
			subtracted.put(moneyType, quantityOf(moneyType) - toSubtract.quantityOf(moneyType));
		});
		
		return new Moneys(subtracted);
	}
	
	public boolean contains(Moneys moneys) {
		return quantities.entrySet().stream().allMatch(es -> {
			MoneyType moneyType = es.getKey();
			int quantity = es.getValue();
			return quantity >= moneys.quantityOf(moneyType);
		});
	}
}
