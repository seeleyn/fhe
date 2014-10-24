package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fhe.Address;
import fhe.Apartment;
import fhe.Column;
import fhe.Person;

public class ParsingUtils {
	public static List<Person> parseCsvFile(String path) throws Exception {
		ArrayList<Person> persons = new ArrayList<Person>();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		try {
			String line = in.readLine();
			// skip the first line
			line = in.readLine();
			while (line != null) {
				if (line.length() > 0) {
					line = preprocess(line);
					String[] tokens = line.split(",");
					// the last column is the group num, which is optional
					if (tokens.length < Column.values().length - 1) {
						throw new Exception("not enough fields (" + tokens.length + ")in '" + line + "'");
					}
					persons.add(parsePerson(tokens));
				}
				line = in.readLine();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
		return persons;
	}

	/**
	 * if a comma is between two double quotes, it is changed into a ; This is
	 * so we can tokenize on commas
	 */
	public static String preprocess(String line) throws Exception {
		StringBuffer sb = new StringBuffer();
		boolean inTag = false;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '"') {
				inTag = !inTag;
				// sb.append(c); remove the double quotes
			} else {
				if (inTag && c == ',')
					sb.append(';');
				else
					sb.append(c);
			}
		}
		if (inTag)
			throw new Exception("unbalanced double quotes on line " + line);
		return sb.toString();
	}

	public static Person parsePerson(String[] tokens) throws Exception {
		String fullName = tokens[Column.FULL_NAME.ordinal()];
		fullName = fullName.replace(';', ',');
		String phone = tokens[Column.PHONE.ordinal()];

		String email = tokens[Column.EMAIL.ordinal()];
		String addressStr = tokens[Column.ADDRESS.ordinal()];

		String gender = tokens[Column.SEX.ordinal()];

		Person p;
		// if there is a preAssigned group, put it in the constructor.
		// Otherwise, ignore it
		if (tokens.length == Column.values().length)
			p = new Person(fullName, phone, email, addressStr, gender, tokens[Column.values().length - 1]);
		else
			p = new Person(fullName, phone, email, addressStr, gender);
		return p;
	}

	public static List<Apartment> putIntoApts(final List<Person> persons) {
		HashMap<String, ArrayList<Person>> map = new HashMap<String, ArrayList<Person>>();
		for (Person person : persons) {
			Address addr = person.getAddress();
			ArrayList<Person> apt = map.get(addr.toString());
			if (apt == null)
				apt = new ArrayList<Person>();
			apt.add(person);
			map.put(addr.toString(), apt);
		}
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(map.keySet());
		Collections.sort(keys);
		ArrayList<Apartment> apts = new ArrayList<Apartment>();

		for (String addr : keys) {
			ArrayList<Person> personsList = map.get(addr);
			apts.add(new Apartment(personsList));
		}
		return apts;
	}

}
