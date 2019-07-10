package tsurisen;

public class ChangeStockRequirement {

	private final Moneys requirements;
	
	public ChangeStockRequirement(Moneys requirements) {
		this.requirements = requirements;
	}

	public boolean isEnough(Moneys quantities) {
		return requirements.contains(quantities);
	}
}
