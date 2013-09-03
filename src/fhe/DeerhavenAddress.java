/*
 * Address.java
 *
 * Created on April 29, 2007, 4:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author n8
 */
public class DeerhavenAddress implements Comparable<Address>, Address {

	private int number = 0;

	private String street = null;

	private String unit = null;

	private String city = "Provo";

	private String state = "UT";

	private String zipCode = "84606";

	private static String addCityStateZipIfNotPresent(String address_) {
		if (address_ == null) {
			return null;
		}

		String addr = address_.toUpperCase().trim();
		// More hackery.... :(
		Pattern pattern = Pattern.compile(".*PROVO.*UT.*");
		Matcher matcher = pattern.matcher(addr);
		if (!matcher.matches()) {
			return address_ + " Provo, Utah 84606";
		} else {
			return address_;
		}
	}

	/** Creates a new instance of Address */
	public DeerhavenAddress(String address_) {
		if (address_ == null) {
			throw new IllegalArgumentException("address parameter cannot be null");
		}
		address_ = addCityStateZipIfNotPresent(address_);

		Pattern pattern = Pattern.compile("^(\\d+)\\s+(.+)PROVO.*UT.*");
		Matcher matcher = pattern.matcher(address_.toUpperCase().trim());
		if (matcher.find()) {
			number = Utils.safeParseInt(matcher.group(1), 0);
			String streetRaw = matcher.group(2);
			Pattern aptPattern = Pattern.compile("^(.*)(APT\\s+\\w+|#\\s*\\w+)$");
			Matcher aptMatcher = aptPattern.matcher(streetRaw.trim());
			if (aptMatcher.find()) {
				street = aptMatcher.group(1);
				unit = parseApartmentNumber(aptMatcher.group(2));
			} else {
				street = streetRaw;
			}
			street = cleanUpStreet(street);

			city = "Provo";
			state = "Utah";
			zipCode = "84606";
		} else {
			throw new IllegalArgumentException("Cannot parse address " + address_);
		}
	}

	private static Matcher matcher(String regex, String input) {
		if (regex == null)
			return null;
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(input);
	}

	private static String cleanUpStreet(String streetName) {
		streetName = streetName.trim();
	
		Matcher matcher = matcher("(.*)\\s+ST[.]?", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}
		
		matcher = matcher("(.*)\\s+STREET", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}		

		matcher = matcher("(.*)\\s+AVE[.]?", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}
		
		matcher = matcher("(.*)\\s+AVENUE", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}		
		
		matcher = matcher("(.*)\\s+DR[.]?", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}
		
		matcher = matcher("(.*)\\s+DRIVE", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}		
		
		matcher = matcher("(.*)\\s+RD[.]?", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}
		
		matcher = matcher("(.*)\\s+ROAD", streetName);
		if (matcher.find()) {
			streetName = matcher.group(1);
		}
		
		matcher = matcher("^(S[.]?|SOUTH)(\\s+.+)", streetName);
		if (matcher.find()) {
			String streetSuffix = matcher.group(2).trim();
			Matcher innerMatcher = matcher("(\\d+)\\s+(E.?|EAST)$",streetSuffix);
			if (innerMatcher.find()) {				
				streetName = "S " + innerMatcher.group(1)+" E";			
			} else {
				streetName = streetSuffix;
			}

		}
		
		matcher = matcher("^(E[.]?|EAST)(\\s+.+)", streetName);
		if (matcher.find()) {
			String streetSuffix = matcher.group(2).trim();
			Matcher innerMatcher = matcher("(\\d+)\\s+(S.?|SOUTH)$",streetSuffix);
			if (innerMatcher.find()) {				
				streetName = "E " + innerMatcher.group(1)+" S";			
			} else {
				streetName = streetSuffix;
			}

		}		
				
		return streetName;
	}

	public DeerhavenAddress(int number_, String street_, String unit_, String city_, String state_, String zipCode_) {
		this.number = number_;
		this.street = street_;
		this.unit = unit_;
		this.city = city_;
		this.state = state_;
		this.zipCode = zipCode_;
	}

	/**
	 * 
	 * @param aptNumString
	 *            of the form "Apt 7" or "#7"
	 * @return
	 */
	private static String parseApartmentNumber(String aptNumString) {
		String aptNum = null;
		if (aptNumString != null) {

			Pattern pattern = Pattern.compile("^APT\\s+(\\w+)");
			Matcher matcher = pattern.matcher(aptNumString.toUpperCase().trim());
			if (matcher.find()) {
				aptNum = matcher.group(1);
			} else {
				pattern = Pattern.compile("^#\\s*(\\w+)");
				matcher = pattern.matcher(aptNumString.toUpperCase().trim());
				if (matcher.find()) {
					aptNum = matcher.group(1);
				}
			}
		}
		return aptNum;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DeerhavenAddress)) {
			return false;
		}
		Address other = (Address) obj;
		if (number != other.getNumber()) {
			System.out.println("Comparing '" + number + "' and '" + other.getNumber() + "'");
			return false;
		}

		if (!Utils.stringEqualsIgnoreCase(street, other.getStreet())) {
			System.out.println("Failed compare on '" + street + "' and '" + other.getStreet() + "'");
			return false;
		}

		if (!Utils.stringEqualsIgnoreCase(unit, other.getUnit())) {
			System.out.println("Comparing '" + unit + "' and '" + other.getUnit() + "'");
			return false;
		}

		/*
		 * if (!Utils.stringEqualsIgnoreCase(city, other.city)) {
		 * System.out.println("Comparing '"+city+"' and '"+other.city+"'");
		 * return false; }
		 * 
		 * 
		 * if (!Utils.stringEqualsIgnoreCase(state, other.state)) {
		 * System.out.println("Comparing '"+state+"' and '"+other.state+"'");
		 * return false; }
		 * 
		 * if (!Utils.stringEqualsIgnoreCase(zipCode, other.zipCode)) {
		 * System.out
		 * .println("Comparing '"+zipCode+"' and '"+other.zipCode+"'"); return
		 * false; }
		 */

		return true;
	}

	public int compareTo(Address obj) {
		return toString().compareTo(obj.toString());
	}

	public String toString() {
		String unitStr = (unit != null) ?  unit : "0";
		return "num='" + number + "' street='" + street + "', unit='" + unitStr + "'";
	}

	public int getNumber() {
		return number;
	}

	public String getStreet() {
		return street;
	}

	public String getUnit() {
		return unit;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

}
