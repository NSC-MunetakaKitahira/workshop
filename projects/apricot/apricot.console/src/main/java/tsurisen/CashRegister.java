package tsurisen;

public class CashRegister {

	private int amount;
	
	public void add(int price) {
		amount += price;
	}
	
	public int calculateChange(int receivedAmount) {
		return amount - receivedAmount;
	}
}
