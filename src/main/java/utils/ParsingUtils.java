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
	public static List<Person> parseCsvFile(Map<Column,Integer> columnToIndex, String path) throws Exception {
		if (columnToIndex== null || path == null) {
			throw new IllegalArgumentException("null input param: columnsInCsv-" + (columnToIndex == null) + ", path- "
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
					Person person = parsePerson(columnToIndex, tokens);
					persons.add(person);
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
	
	public static String[] padTokensToSize(String[] tokens, int minSize) {
		String[] tokens2 = new String[minSize];
		for (int i =0; i<minSize; i++) {
			if (i<tokens.length) {
				tokens2[i] = tokens[i];
			} else {
				tokens2[i] = "";
			}
		}
		return tokens2;		
	}

	public static Person parsePerson(Map<Column,Integer> columnToIndex, String[] tokens) throws Exception {
		if (columnToIndex == null || tokens == null)
			throw new IllegalArgumentException("Null input parameter: columns-" + (columnToIndex == null) + ", tokens-"
					+ (tokens == null));
		if (tokens.length < columnToIndex.size()) {
			tokens = padTokensToSize(tokens, columnToIndex.size());
		}

		String fullName = null;
		if (columnToIndex.containsKey(Column.FULL_NAME)) {
			fullName = tokens[columnToIndex.get(Column.FULL_NAME)];
			fullName = fullName.replace(';', ',');
		}

		String phone = null;
		if (columnToIndex.containsKey(Column.PHONE)) {
			phone = tokens[columnToIndex.get(Column.PHONE)];
		}

		String email = null;
		if (columnToIndex.containsKey(Column.EMAIL)) {
			email = tokens[columnToIndex.get(Column.EMAIL)];
		}

		String addressStr = null;
		if (columnToIndex.containsKey(Column.ADDRESS)) {
			addressStr = tokens[columnToIndex.get(Column.ADDRESS)];
		}

		String gender = null;
		if (columnToIndex.containsKey(Column.SEX)) {
			gender = tokens[columnToIndex.get(Column.SEX)];
		}

		String groupCode = null;
		if (columnToIndex.containsKey(Column.GROUP)) {
			groupCode = tokens[columnToIndex.get(Column.GROUP)];
		}

		Person p;
		// if there is a preAssigned group, put it in the constructor.
		// Otherwise, ignore it
		if (groupCode != null)
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
