package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fhe.Address;
import fhe.Apartment;
import fhe.Column;
import fhe.Person;

public class ParsingUtils {
	public static List<Person> parseCsvFile(List<Column> columnsInCsv, String path) throws Exception {
		if (columnsInCsv == null || path == null) {
			throw new IllegalArgumentException("null input param: columnsInCsv-" + (columnsInCsv == null) + ", path- "
					+ (path == null));
		}
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
					persons.add(parsePerson(columnsInCsv, tokens));
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

	public static Person parsePerson(List<Column> columns, String[] tokens) throws Exception {
		if (columns == null || tokens == null)
			throw new IllegalArgumentException("Null input parameter: columns-" + (columns == null) + ", tokens-"
					+ (tokens == null));
		Column[] requiredColumns = { Column.FULL_NAME, Column.PHONE, Column.EMAIL, Column.ADDRESS, Column.SEX };
		for (Column requiredColumn : requiredColumns) {
			if (!columns.contains(requiredColumn))
				throw new IllegalArgumentException("columns input parameter must contain " + requiredColumn);
		}
		if (tokens.length < columns.size()) {
			throw new IllegalArgumentException("At least " + columns.size() + " columns are required. "
					+ Arrays.asList(tokens) + " only has " + tokens.length);
		}

		Map<Column, Integer> columnToIndex = new HashMap<Column, Integer>();
		for (int i = 0; i < columns.size(); i++) {
			columnToIndex.put(columns.get(i), i);
		}

		String fullName = tokens[columnToIndex.get(Column.FULL_NAME)];
		fullName = fullName.replace(';', ',');
		String phone = tokens[columnToIndex.get(Column.PHONE)];

		String email = tokens[columnToIndex.get(Column.EMAIL)];
		String addressStr = tokens[columnToIndex.get(Column.ADDRESS)];

		String gender = tokens[columnToIndex.get(Column.SEX)];

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
