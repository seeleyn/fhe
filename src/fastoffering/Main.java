package fastoffering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.ParsingUtils;
import fhe.Apartment;
import fhe.Column;
import fhe.Person;

public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage: <input csv>");
			System.err.println("Example  ./fhe sampleInput/wardList.csv");
			System.exit(1);
		}
		String inputFile = args[0];
		List<Column> columnsInCsv = Arrays.asList(Column.FULL_NAME, Column.ADDRESS);
		List<Person> persons = ParsingUtils.parseCsvFile(columnsInCsv, inputFile);
		System.out.println("read in " + persons.size() + " persons");
		List<Apartment> apts = ParsingUtils.putIntoApts(persons);
		Apartment.printApts(apts);
		/*
		 * Set<String> streets = new TreeSet<String>(); for (Apartment apt :
		 * apts) { streets.add(apt.getAddress().getStreet()); }
		 * System.out.println("\n**********************\nStreets are"); for
		 * (String street : streets) { System.out.println("Street is "+street);
		 * }
		 */
		List<Route> routes = new ArrayList<Route>();
		routes.add(createRoute());
		routes.add(createRoute2());

		List<Apartment> unassignedApartments = new ArrayList<Apartment>();
		for (Apartment apt : apts) {
			for (Route route : routes) {
				if (route.isOnRoute(apt.getAddress())) {
					route.addApartment(apt);
					break;
				}
			}
			unassignedApartments.add(apt);
		}

		for (Route route : routes) {
			System.out.println("**************************");
			System.out.println(route.name);
			System.out.println("**************************");
			for (Apartment aptInRoute : route.apartments) {
				aptInRoute.print();
			}
		}

	}

	public static Route createRoute() {
		Route route = new Route("E SW");
		route.addStreetFilter(new StreetFilter("S 900 E"));
		route.addStreetFilter(new StreetFilter("S 960 E"));
		route.addStreetFilter(new StreetFilter("E 1180 S"));
		route.addStreetFilter(new StreetFilter("E 1230 S"));
		return route;
	}

	public static Route createRoute2() {
		Route route = new Route("W SW");
		route.addStreetFilter(new StreetFilter("S 1000 E"));
		route.addStreetFilter(new StreetFilter("E 1160 S"));
		route.addStreetFilter(new StreetFilter("S 1060 E"));
		route.addStreetFilter(new StreetFilter("E 1270 S"));
		return route;
	}

}
