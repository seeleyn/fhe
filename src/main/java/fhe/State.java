package fhe;

public enum State {
	UTAH("Utah", "UT");

	String name;

	String abbreviation;

	private State(String name_, String abbreviation_) {
		this.name = name_;
		this.abbreviation = abbreviation_;
	}

	public String getName() {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
}
