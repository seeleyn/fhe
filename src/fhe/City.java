package fhe;

public enum City {
	PROVO("Provo", State.UTAH), OREM("Orem", State.UTAH), SPRINGVILLE("Springville", State.UTAH), MAPLETON("Mapleton",State.UTAH);

	String name;

	State state;

	private City(String name_, State state_) {
		this.name = name_;
		this.state = state_;
	}

	public String getName() {
		return name;
	}

	public State getState() {
		return state;
	}

}
