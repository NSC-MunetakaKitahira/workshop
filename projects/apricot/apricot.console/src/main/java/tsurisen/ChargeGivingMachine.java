package tsurisen;

import java.util.Map;

public class ChargeGivingMachine {

	private final CashRegister cashRegister;

	public ChargeGivingMachine(CashRegister cashRegister) {
		this.cashRegister = cashRegister;
	}
	
	public Map<MoneyType, Integer> getChange(int deposit) {
		int change = deposit - cashRegister.amount();
		return ChangeCompositeRule.compositeChange(change);
	}
}
