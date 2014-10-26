package fastoffering;

import fhe.Address;

public class StreetFilter {

	final String street;

	final int minStreetNumber;

	final int maxStreetNumber;
	
	public StreetFilter(String street) {
		this(street,0,Integer.MAX_VALUE);
	}

	public StreetFilter(String street, int minStreetNumber, int maxStreetNumber) {
		this.street = street;
		this.minStreetNumber = minStreetNumber;
		this.maxStreetNumber = maxStreetNumber;
	}

	public boolean matches(Address address) {
		if (address != null) {
			if (address.getStreet().equals(street) && address.getNumber() > minStreetNumber
					&& address.getNumber() < maxStreetNumber) {
				return true;
			}
		}
		return false;
	}

}
