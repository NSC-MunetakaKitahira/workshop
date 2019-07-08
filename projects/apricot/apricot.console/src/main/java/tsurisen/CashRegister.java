package tsurisen;

public class CashRegister {

	private int amount;
	
	public void add(int price) {
		amount += price;
	}
	
	public int amount() {
		return amount;
	}
}
