package fastoffering;

import fhe.Address;

public class StreetFilter {

	final String street;

	final int minStreetNumber;

	final int maxStreetNumber;

	public StreetFilter(String street) {
		this(street, 0, Integer.MAX_VALUE);
	}

	public StreetFilter(String street, int minStreetNumber, int maxStreetNumber) {
		this.street = street;
		this.minStreetNumber = minStreetNumber;
		this.maxStreetNumber = maxStreetNumber;
	}

	public boolean matches(Address address) {		
		if (address != null && address.getStreet() != null) {
			if (address.getStreet().equals(street)) {
				if (address.getNumber() > minStreetNumber) {
					if (address.getNumber() < maxStreetNumber) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
