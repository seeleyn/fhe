/*
 * Person.java
 *
 * Created on April 29, 2007, 4:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.util.Scanner;

/**
 * 
 * @author n8
 */
public class Person {
	String fullName;

	String phone;

	String email;

	Address address;

	Gender gender;

	int preAssignedGroupNum = -1;

	boolean isLeader = false;

	/** Creates a new instance of Person */
	public Person(String fullName_, String phone_, String email_, String address_, String gender_, String groupCode)
			throws Exception {
		this(fullName_, phone_, email_, address_, gender_);
		Scanner scanner = new Scanner(groupCode);
		scanner.useDelimiter("\\*");
		preAssignedGroupNum = scanner.nextInt();
		if (groupCode.contains("*"))
			isLeader = true;
	}

	/** Creates a new instance of Person */
	public Person(String fullName_, String phone_, String email_, String address_, String gender_) throws Exception {
		fullName = fullName_;
		phone = phone_;
		email = email_.trim();
		address = new Address(address_);

		if (gender_.trim().equalsIgnoreCase("female") || gender_.trim().equalsIgnoreCase("f"))
			gender = Gender.FEMALE;
		else if (gender_.trim().equalsIgnoreCase("male") || gender_.trim().equalsIgnoreCase("m"))
			gender = Gender.MALE;
		else
			throw new IllegalArgumentException("invalid gender " + gender_);
	}

	public String toString() {
		return fullName;
	}

	public String toFullString() {
		return fullName + "," + address + "," + phone + "," + email + "," + gender;
	}

	public void print() {
		System.out.println("name: " + fullName);
		System.out.println("address: " + address);
		System.out.println("phone: " + phone);
		System.out.println("email: " + email);
		System.out.println("gender: " + gender);
	}

	private String getColumnValue(Column col) {
		switch (col) {
		case FULL_NAME:
			return fullName;
		case PHONE:
			return phone;
		case EMAIL:
			return email;
		case ADDRESS:
			return address.toString();
		case SEX:
			return gender.toString();
		default:
			throw new IllegalStateException(col + " not handled for this method");
		}
	}

	public String toCSVString() {
		StringBuffer sb = new StringBuffer();
		Column cols[] = Column.values();
		for (Column col : cols) {
			if (col != Column.GROUP) {
				if (col.ordinal() != 0)
					sb.append(",");
				sb.append("\"" + getColumnValue(col) + "\"");
			}
		}
		return sb.toString();
	}

	public int getPreAssignedGroupNum() {
		return preAssignedGroupNum;
	}
}
