package fastoffering;

import java.util.List;

import utils.ParsingUtils;
import fhe.Apartment;
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
		List<Person> persons = ParsingUtils.parseCsvFile(inputFile);
		System.out.println("read in " + persons.size() + " persons");
		List<Apartment> apts = ParsingUtils.putIntoApts(persons);
		Apartment.printApts(apts);
	}

}