/*
 * Main.java
 *
 * Created on April 29, 2007, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.io.*;
import java.util.*;

import utils.ParsingUtils;

/**
 * 
 * @author n8
 */
public class Main {

	/** Creates a new instance of Main */
	public Main() {
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: <input csv> <num of fhe groups>");
			System.err.println("Example  ./fhe sampleInput/wardList.csv 6");
			System.exit(1);
		}
		String inputFile = args[0];
		int numOfGroups = Integer.parseInt(args[1]);
		List<Person> persons = ParsingUtils.parseCsvFile(inputFile);
		System.out.println("read in " + persons.size() + " persons");
		List<Apartment> apts = ParsingUtils.putIntoApts(persons);
		Collections.sort(apts);
		Apartment.printApts(apts);
		ArrayList<Group> groups = assignAptsToGroups(apts, numOfGroups);
		printGroups(groups);
		// createCSVFile(groups,"output/output.csv");
		outputGroupReports(groups);
		// String emails = outputEmails(persons, false);
		// PrintWriter out = new PrintWriter(new
		// FileOutputStream("output/wardEmails2.txt"));
		// out.print(emails);
		// out.close();
	}

	private static void outputGroupReports(Collection<Group> groups) throws Exception {
		int i = 0;
		for (Group group : groups) {
			File outputDir = new File("output");
			if (!outputDir.exists()) {
				if (!outputDir.mkdir()) {
					throw new IllegalStateException("Cannot create output directory");
				}
			}
			PrintWriter out = new PrintWriter(new FileOutputStream("output/group" + (i++) + ".txt"));
			out.print(group.toReportString());
			out.close();
		}
	}

	public static String outputEmails(Collection<Person> persons, boolean leadersOnly) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (Person persn : persons) {
			if (persn.email != null && persn.email.length() > 0 && persn.email.contains("@")) {
				if (persn.email.matches(".*@.*@.*"))
					System.out.println("Too many @'s in email for " + persn.fullName + ", which is '" + persn.email
							+ "'");
				else if (!leadersOnly)
					sb.append(persn.email + ",");
				else {
					if (persn.isLeader)
						sb.append(persn.email + ",");
				}
			}
			// else
			// {
			// System.out.println("Invalid email '"+persn.email+"' for "+persn.preferredName);
			// }
		}
		sb.append("\r\n");
		return sb.toString();
	}

	static void createCSVFile(List<Group> groups, String fileName) throws Exception {
		PrintWriter out = new PrintWriter(new FileOutputStream(fileName));
		out.println(Column.toHeaderString());
		for (int i = 0; i < groups.size(); i++) {
			Group gr = groups.get(i);
			List<Person> persons = gr.getPersons();
			for (Person p : persons) {
				String leaderMark = p.isLeader ? "*" : "";
				out.println(p.toCSVString() + ",\"" + i + leaderMark + "\"");
			}
		}
		out.close();
	}

	private static void printGroups(final ArrayList<Group> groups) {
		for (int i = 0; i < groups.size(); i++) {
			System.out.println("GROUP " + i);
			groups.get(i).print();
			System.out.println("\n\n");
		}
	}

	private static ArrayList<Group> assignAptsToGroups(final List<Apartment> apts, int numOfGroups) {
		if (numOfGroups < 1)
			throw new IllegalArgumentException("There must be at least 1 group, not " + numOfGroups);
		ArrayList<Group> groups = new ArrayList<Group>();
		for (int i = 0; i < numOfGroups; i++)
			groups.add(new Group());

		Random random = new Random(System.currentTimeMillis());
		// for (Apartment apt : apts)
		while (apts.size() > 0) {
			int index = random.nextInt(apts.size());
			Apartment apt = apts.get(index);
			apts.remove(apt);
			if (apt.getPreAssignedGroupNum() >= 0) {
				if (apt.getPreAssignedGroupNum() >= groups.size())
					throw new IllegalStateException("Pre assignment to group " + apt.getPreAssignedGroupNum()
							+ " is not possible because there are only " + groups.size() + " groups");
				Group preAssignedGroup = groups.get(apt.getPreAssignedGroupNum());
				preAssignedGroup.add(apt);
				System.out.println("pre-assiging " + apt.address + " to " + apt.getPreAssignedGroupNum());
			} else {
				Gender aptGender = apt.gender;
				Group grp = findGroupWithLowestGender(aptGender, groups);
				grp.add(apt);
			}
		}
		return groups;
	}

	private static Group findGroupWithLowestGender(Gender gender, List<Group> groups) {
		if (groups == null || groups.size() == 0 || gender == null)
			throw new IllegalArgumentException();
		Group low = groups.get(0);
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getNum(gender) < low.getNum(gender))
				low = groups.get(i);
		}
		return low;
	}

}
