package fastoffering;

import java.util.ArrayList;
import java.util.List;

import fhe.Address;
import fhe.Apartment;

public class Route {

	String name;
	
	List<StreetFilter> streetFilters = new ArrayList<StreetFilter>();

	List<Apartment> apartments = new ArrayList<Apartment>();

	public Route(String name) {
		this.name = name;
	}

	public void addStreetFilter(StreetFilter streetFilter) {
		streetFilters.add(streetFilter);
	}

	public boolean isOnRoute(Address address) {
		for (StreetFilter streetFilter : streetFilters) {
			if (streetFilter.matches(address)) {
				return true;
			}
		}
		return false;
	}
	
	public void addApartment(Apartment apartment) {
		if (apartment == null) {
			throw new IllegalArgumentException("Cannot add null apartment to route "+name);
		}
		if (!isOnRoute(apartment.getAddress())) {
			throw new IllegalArgumentException("Apartment "+apartment.getAddress()+" is not on route "+name);
		}
		
		apartments.add(apartment);
	}

}
