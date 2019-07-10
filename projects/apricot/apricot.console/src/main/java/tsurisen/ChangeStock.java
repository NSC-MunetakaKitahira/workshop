package tsurisen;

public class ChangeStock {
	
	private Moneys stock;
	private final ChangeStockRequirement requirement;

	public ChangeStock(Moneys stock, ChangeStockRequirement requirement) {
		this.stock = stock;
		this.requirement = requirement;
	}
	
	public void accept(Moneys moneysToAccept) {
		stock = stock.add(moneysToAccept);
	}

	public void discharge(Moneys moneysToDischarge) {
		stock = stock.subtract(moneysToDischarge);
	}
	
	public boolean isEnough() {
		return requirement.isEnough(stock);
	}
}
