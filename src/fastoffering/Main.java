package fastoffering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		List<Column> columnsInCsv = Arrays.asList(Column.ADDRESS, Column.FULL_NAME);
		List<Person> persons = ParsingUtils.parseCsvFile(columnsInCsv, inputFile);
		System.out.println("read in " + persons.size() + " persons");
		List<Apartment> apts = ParsingUtils.putIntoApts(persons);

		List<Route> routes = new ArrayList<Route>();
		routes.add(createRoute());
		routes.add(createRoute2());
		routes.add(createRoute3());
		routes.add(createRoute4());
		routes.add(createRoute5());
		routes.add(createRoute6());
		routes.add(createRoute7());
		routes.add(createRoute8());

		List<Apartment> unassignedApartments = new ArrayList<Apartment>();
		for (Apartment apt : apts) {
			boolean assignedToRoute = false;
			for (Route route : routes) {
				if (route.isOnRoute(apt.getAddress())) {
					route.addApartment(apt);
					assignedToRoute = true;
					break;
				}
			}
			if (!assignedToRoute) {
				unassignedApartments.add(apt);
			}
		}

		for (Route route : routes) {
			System.out.println("**************************");
			System.out.println(route.name);
			System.out.println("**************************");
			List<Apartment> aptsInRoute = route.apartments;

			Collections.sort(aptsInRoute);
			for (Apartment aptInRoute : route.apartments) {
				aptInRoute.printAsLine();
			}
		}

		System.out.println("**************************");
		System.out.println("Unassigned");
		System.out.println("**************************");
		for (Apartment apt : unassignedApartments) {
			apt.printAsLine();
		}

	}

	public static Route createRoute() {
		Route route = new Route("W South Willow");
		route.addStreetFilter(new StreetFilter("S 900 E"));
		route.addStreetFilter(new StreetFilter("S 960 E"));
		route.addStreetFilter(new StreetFilter("E 1180 S"));
		route.addStreetFilter(new StreetFilter("E 1230 S"));
		return route;
	}

	public static Route createRoute2() {
		Route route = new Route("E South Willow");
		route.addStreetFilter(new StreetFilter("S 1000 E"));
		route.addStreetFilter(new StreetFilter("E 1160 S"));
		route.addStreetFilter(new StreetFilter("S 1060 E"));
		route.addStreetFilter(new StreetFilter("E 1270 S"));
		route.addStreetFilter(new StreetFilter("STATE"));
		return route;
	}

	public static Route createRoute3() {
		Route route = new Route("W Deerhaven");
		route.addStreetFilter(new StreetFilter("S 1370 E"));
		route.addStreetFilter(new StreetFilter("S 1400 E"));
		route.addStreetFilter(new StreetFilter("S 1440 E"));
		route.addStreetFilter(new StreetFilter("E 1370 S", 1370, 1470));
		route.addStreetFilter(new StreetFilter("E 1320 S", 1370, 1470));
		return route;
	}

	public static Route createRoute4() {
		Route route = new Route("Old Deerhaven");
		// route.addStreetFilter(new StreetFilter("STATE"));
		route.addStreetFilter(new StreetFilter("E 1440 S"));
		route.addStreetFilter(new StreetFilter("S 1420 E"));
		route.addStreetFilter(new StreetFilter("S 1470 E", 1320, 2000));
		route.addStreetFilter(new StreetFilter("E 1370 S", 1440, 1500));
		route.addStreetFilter(new StreetFilter("E 1320 S", 1440, 1500));
		return route;
	}

	public static Route createRoute5() {
		Route route = new Route("Pioneer 2");
		route.addStreetFilter(new StreetFilter("S 1500 E"));
		route.addStreetFilter(new StreetFilter("S 1550 E"));
		route.addStreetFilter(new StreetFilter("E 1350 S"));
		route.addStreetFilter(new StreetFilter("S 1590 E"));
		return route;
	}

	public static Route createRoute6() {
		Route route = new Route("Cul de sacs");
		route.addStreetFilter(new StreetFilter("S 1650 E"));
		route.addStreetFilter(new StreetFilter("S 1710 E"));
		route.addStreetFilter(new StreetFilter("E 1350 S"));
		route.addStreetFilter(new StreetFilter("S 1590 E"));
		route.addStreetFilter(new StreetFilter("E 1320 S", 1550, 2000));
		return route;
	}

	public static Route createRoute7() {
		Route route = new Route("Slate Canyon & Nevada");
		route.addStreetFilter(new StreetFilter("NEVADA"));
		route.addStreetFilter(new StreetFilter("SLATE CANYON"));
		return route;
	}

	public static Route createRoute8() {
		Route route = new Route("Pioneer");
		route.addStreetFilter(new StreetFilter("S 1470 E", 1000, 1320));
		route.addStreetFilter(new StreetFilter("S 1460 E"));
		route.addStreetFilter(new StreetFilter("S 1480 E"));
		route.addStreetFilter(new StreetFilter("E 1190 S"));
		route.addStreetFilter(new StreetFilter("E 1230 S"));
		route.addStreetFilter(new StreetFilter("E 1280 S"));
		route.addStreetFilter(new StreetFilter("E 1300 S"));
		route.addStreetFilter(new StreetFilter("E 1320 S", 1470, 1550));
		route.addStreetFilter(new StreetFilter("S 1510 E"));
		route.addStreetFilter(new StreetFilter("S 1540 E"));
		return route;
	}

}
