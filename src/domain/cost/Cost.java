package domain.cost;

public class Cost {

	int minerals;
	int gas;
	
	public Cost(int minerals, int gas) {
		super();
		this.minerals = minerals;
		this.gas = gas;
	}

	public int getMinerals() {
		return minerals;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}

	public int getGas() {
		return gas;
	}

	public void setGas(int gas) {
		this.gas = gas;
	}
	
}
