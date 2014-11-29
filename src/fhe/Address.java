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

import utils.StringUtils;

/**
 * 
 * @author n8
 */
public class Address implements Comparable<Address> {

	private int number = 0;

	private String street = null;

	private String unit = null;

	private City city = null;

	/** Creates a new instance of Address */
	public Address(String address_) {
		if (address_ == null) {
			throw new IllegalArgumentException("address parameter cannot be null");
		}
		String addressString = address_.toUpperCase();

		String cityStreetApt = null;
		for (City cityCandidate : City.values()) {
			String cityName = cityCandidate.getName().toUpperCase();
			String stateName = cityCandidate.getState().getName().toUpperCase();
			String stateAbbrev = cityCandidate.getState().getAbbreviation().toUpperCase();
			Pattern pn = Pattern.compile("^(.*)(" + cityName + ").*(" + stateName + "|" + stateAbbrev + ").*$");
			Matcher matcher = pn.matcher(addressString);
			if (matcher.find()) {
				this.city = cityCandidate;
				cityStreetApt = matcher.group(1);
				break;
			}
		}

		if (cityStreetApt == null) {
			// If no city is specified, assume Provo
			cityStreetApt = addressString;
			city = City.PROVO;
		}

		Pattern pattern = Pattern.compile("^(\\d+)\\s+(.+)");
		Matcher matcher = pattern.matcher(cityStreetApt.toUpperCase().trim());
		if (matcher.find()) {
			number = StringUtils.safeParseInt(matcher.group(1), 0);
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
		} else {
			System.err.println("Cannot parse address '" + address_ + "'. Using defaults");
			number = 0;
			street = null;
			unit = null;
			city = null;
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

		matcher = matcher("^(S[.]?|SO[.]?|SOUTH)(\\s+.+)", streetName);
		if (matcher.find()) {
			String streetSuffix = matcher.group(2).trim();
			Matcher innerMatcher = matcher("(\\d+)\\s+(E.?|EAST)$", streetSuffix);
			if (innerMatcher.find()) {
				streetName = "S " + innerMatcher.group(1) + " E";
			} else {
				innerMatcher = matcher("(\\d+)\\s+(N.?|NORTH)$", streetSuffix);
				if (innerMatcher.find()) {
					streetName = "S " + innerMatcher.group(1) + " N";
				} else {
					streetName = streetSuffix;
				}
			}

		}

		matcher = matcher("^(E[.]?|EAST)(\\s+.+)", streetName);
		if (matcher.find()) {
			String streetSuffix = matcher.group(2).trim();
			Matcher innerMatcher = matcher("(\\d+)\\s+(S.?|SOUTH)$", streetSuffix);
			if (innerMatcher.find()) {
				streetName = "E " + innerMatcher.group(1) + " S";
			} else {
				innerMatcher = matcher("(\\d+)\\s+(N.?|NORTH)$", streetSuffix);
				if (innerMatcher.find()) {
					streetName = "E " + innerMatcher.group(1) + " N";
				} else {
					streetName = streetSuffix;
				}
			}

		}

		return streetName;
	}

	public Address(int number_, String street_, String unit_, City city_) {
		this.number = number_;
		this.street = street_;
		this.unit = unit_;
		this.city = city_;
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
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		if (number != other.getNumber()) {
			System.out.println("Comparing '" + number + "' and '" + other.getNumber() + "'");
			return false;
		}

		if (!StringUtils.stringEqualsIgnoreCase(street, other.getStreet())) {
			System.out.println("Failed compare on '" + street + "' and '" + other.getStreet() + "'");
			return false;
		}

		if (!StringUtils.stringEqualsIgnoreCase(unit, other.getUnit())) {
			System.out.println("Comparing '" + unit + "' and '" + other.getUnit() + "'");
			return false;
		}

		if (this.city != other.getCity()) {
			System.out.println("Comparing '" + city + "' and '" + other.getCity() + "'");
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
		return toUniqueString().compareTo(obj.toUniqueString());
	}

	public String toString() {

		if (number == 0 && street == null && unit == null && city == null) {
			return "Unparseable address";
		} else {
			String unitStr = (unit != null) ? " Apt. " + unit : "";
			return number + " " + street + unitStr + ", " + city;
		}

	}

	public String toUniqueString() {
		return "number=" + number + ", street='" + street + "', apt='" + unit + "', city=" + city;
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

	public City getCity() {
		return city;
	}

}
