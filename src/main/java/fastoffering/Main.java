package fastoffering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import utils.MarkdownRenderer;
import utils.ParsingUtils;
import fhe.Apartment;
import fhe.Column;
import fhe.Person;
import utils.PdfRenderer;

public class Main {

	//To get the csv go to the web site and sign in. Navigate to My Ward, Directory, Export Households
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage: <input csv>");
			System.err.println("Example  ./fastoffering.sh sampleInput/wardList.csv");
			System.exit(1);
		}
		String inputFile = args[0];
		Map<Column,Integer> columnToIndex = new HashMap<Column, Integer>();
		columnToIndex.put(Column.FULL_NAME,1);
		columnToIndex.put(Column.ADDRESS,4);
		List<Person> persons = ParsingUtils.parseCsvFile(columnToIndex, inputFile);
		// System.out.println("read in " + persons.size() + " persons");
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

		Map<String, List<Apartment>> apartmentMappings = new LinkedHashMap<String, List<Apartment>>();
		for (Route route : routes) {
			apartmentMappings.put(route.name, route.apartments);
		}
		apartmentMappings.put("Unassigned", unassignedApartments);

		outputReports(apartmentMappings);
	}

	private static void outputReports(Map<String, List<Apartment>> apartmentMappings) throws Exception {
		File outputDir = new File("fastOfferingOutput");
		if (!outputDir.exists()) {
			if (!outputDir.mkdir()) {
				throw new IllegalStateException("Cannot create output directory");
			}
		}
		System.out.println("Printing output to " + outputDir.getAbsolutePath());

		// Print a report that contains information for all routes
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String allRoutesReportMarkdown = MarkdownRenderer.createReportForAllRoutes("Fast Offering Routes - "
				+ dateFormat.format(new Date()), apartmentMappings);
		printStringToFile(outputDir + "/allRoutes.txt", allRoutesReportMarkdown);

		// Print a separate report for each route (to be included in the fast
		// offering route bags)
		for (String routeName : apartmentMappings.keySet()) {
			String routeMarkdown = MarkdownRenderer.createRouteReport(routeName, apartmentMappings.get(routeName));
			printStringToFile(outputDir + "/" + routeName.replaceAll("\\s", "") + ".txt", routeMarkdown);
			String pdfFileName = outputDir + "/" + routeName.replaceAll("\\s", "") + ".pdf";
			PdfRenderer.createRouteReport(routeName, apartmentMappings.get(routeName), pdfFileName);
		}
	}

	private static void printStringToFile(String fileName, String stringToOutput) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new FileOutputStream(fileName));
		out.print(stringToOutput);
		out.close();
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
		route.addStreetFilter(new StreetFilter("E 1320 S", 1370, 1440));
		return route;
	}

	public static Route createRoute4() {
		Route route = new Route("Old Deerhaven");
		route.addStreetFilter(new StreetFilter("E 1440 S"));
		route.addStreetFilter(new StreetFilter("S 1420 E"));
		route.addStreetFilter(new StreetFilter("S 1470 E", 1320, 2000));
		route.addStreetFilter(new StreetFilter("E 1370 S", 1440, 1500));
		return route;
	}

	public static Route createRoute5() {
		Route route = new Route("Pioneer");
		route.addStreetFilter(new StreetFilter("S 1470 E", 1000, 1320));
		route.addStreetFilter(new StreetFilter("S 1460 E"));
		route.addStreetFilter(new StreetFilter("S 1480 E"));
		route.addStreetFilter(new StreetFilter("E 1190 S"));
		route.addStreetFilter(new StreetFilter("E 1230 S"));
		route.addStreetFilter(new StreetFilter("E 1280 S"));
		route.addStreetFilter(new StreetFilter("E 1300 S"));
		route.addStreetFilter(new StreetFilter("S 1510 E"));
		route.addStreetFilter(new StreetFilter("S 1540 E"));
		return route;
	}

	public static Route createRoute6() {
		Route route = new Route("Pioneer 2");
		route.addStreetFilter(new StreetFilter("S 1500 E"));
		route.addStreetFilter(new StreetFilter("S 1550 E"));
		route.addStreetFilter(new StreetFilter("E 1350 S"));
		route.addStreetFilter(new StreetFilter("S 1590 E"));
		route.addStreetFilter(new StreetFilter("E 1320 S", 1440, 1550));
		return route;
	}

	public static Route createRoute7() {
		Route route = new Route("Cul de sacs");
		route.addStreetFilter(new StreetFilter("S 1650 E"));
		route.addStreetFilter(new StreetFilter("S 1710 E"));
		route.addStreetFilter(new StreetFilter("E 1350 S"));
		route.addStreetFilter(new StreetFilter("S 1590 E"));
		route.addStreetFilter(new StreetFilter("E 1320 S", 1550, 2000));
		return route;
	}

	public static Route createRoute8() {
		Route route = new Route("Slate Canyon & Nevada");
		route.addStreetFilter(new StreetFilter("NEVADA"));
		route.addStreetFilter(new StreetFilter("SLATE CANYON"));
		return route;
	}

}
