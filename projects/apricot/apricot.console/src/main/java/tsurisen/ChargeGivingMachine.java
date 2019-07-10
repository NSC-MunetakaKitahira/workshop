package tsurisen;

public class ChargeGivingMachine {

	private final CashRegister cashRegister;
	private final ChangeStock changeStock;

	public ChargeGivingMachine(CashRegister cashRegister, ChangeStock changeStock) {
		this.cashRegister = cashRegister;
		this.changeStock = changeStock;
	}
	
	public Moneys getChange(Moneys receivedMoney) {
		int change = cashRegister.calculateChange(receivedMoney.amount());
		Moneys changeMoneys = ChangeCompositeRule.createChangeMoneysOf(change);
		
		changeStock.accept(receivedMoney);
		changeStock.discharge(changeMoneys);
		
		return changeMoneys;
	}
	
	public boolean isEnoughStock() {
		return changeStock.isEnough();
	}
}
