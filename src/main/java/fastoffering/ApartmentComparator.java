package fastoffering;

import java.util.Comparator;

import fhe.Address;
import fhe.Apartment;

/*
 * The point of this class is to sort the addresses in walking order, not numerical order.
 * Since all the odd addresses are one side of the street and the evens on another, this helps
 * the report look like how the addresses might be traversed in walking order.
 */
public class ApartmentComparator implements Comparator<Apartment> {

	@Override
	public int compare(Apartment apt1, Apartment apt2) {
		//TODO handle case where apartments or addresses are null
		Address addr1 = toModifiedAddress(apt1.getAddress());
		Address addr2 = toModifiedAddress(apt2.getAddress());
		return addr1.compareTo(addr2);
	}
	
	private Address toModifiedAddress(Address addr1) {
		int addrNumber1 = addr1.getNumber();
		if (addrNumber1 % 2 == 0)
			addrNumber1 *= -1;  //Causes even addresses to be separated from odds
		return new Address(addrNumber1, addr1.getStreet(), addr1.getUnit(), addr1.getCity());		
	}

}
