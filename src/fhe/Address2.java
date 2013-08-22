/*
 * Address.java
 *
 * Created on April 29, 2007, 4:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.jgeocoder.AddressComponent;
import net.sourceforge.jgeocoder.us.AddressParser;
import net.sourceforge.jgeocoder.us.AddressStandardizer;

/**
 * 
 * @author n8
 */
public class Address2 implements Comparable<Address> {

	private int number = 0;

	private String street = null;

	private String unit = null;

	private String city = "Provo";

	private String state = "UT";

	private String zipCode = "84606";

	/** Creates a new instance of Address */
	public Address2(String address_) {
		if (address_ == null) {
			throw new IllegalArgumentException(
					"address parameter cannot be null");
		}
		Map<AddressComponent, String> parsedAddr = AddressParser
				.parseAddress(address_);
		number = Integer.parseInt(parsedAddr.get(AddressComponent.NUMBER));
		StringBuilder streetBuilder = new StringBuilder();
		if (parsedAddr.get(AddressComponent.PREDIR) != null) {
			streetBuilder.append(parsedAddr.get(AddressComponent.PREDIR) + " ");
		}
		streetBuilder.append(parsedAddr.get(AddressComponent.STREET));
		if (parsedAddr.get(AddressComponent.POSTDIR) != null) {
			streetBuilder
					.append(" " + parsedAddr.get(AddressComponent.POSTDIR));
		}
		if (parsedAddr.get(AddressComponent.TYPE) != null) {
			streetBuilder
					.append(" " + parsedAddr.get(AddressComponent.TYPE));
		}
		
		street = streetBuilder.toString();

		String line2 = parsedAddr.get(AddressComponent.LINE2);
		if (line2 != null) {

			Pattern pattern = Pattern.compile("^APT\\s+(\\w*)$");
			Matcher matcher = pattern.matcher(line2.toUpperCase().trim());
			if (matcher.find()) {
				unit = matcher.group(1);
			} else {
				pattern = Pattern.compile("^#\\s+(\\w*)$");
				matcher = pattern.matcher(line2.toUpperCase().trim());
				if (matcher.find()) {
					unit = matcher.group(1);
				}
			}
		}

		city = parsedAddr.get(AddressComponent.CITY);
		state = parsedAddr.get(AddressComponent.STATE);
		zipCode = parsedAddr.get(AddressComponent.ZIP);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Address)) {
			return false;
		}
		Address2 other = (Address2) obj;
		if (number != other.number)
			return false;

		if (street != null) {
			return street.equals(other.street);
		} else if (other.street != null) {
			return false;
		}

		if (unit != null) {
			return unit.equals(other.unit);
		} else if (other.unit != null) {
			return false;
		}

		if (city != null) {
			return city.equals(other.city);
		} else if (other.city != null) {
			return false;
		}

		if (state != null) {
			return state.equals(other.state);
		} else if (other.state != null) {
			return false;
		}

		if (zipCode != null) {
			return zipCode.equals(other.zipCode);
		} else if (other.zipCode != null) {
			return false;
		}

		return false;
	}

	public int compareTo(Address obj) {
		return toString().compareTo(obj.toString());
	}

	public String toString() {
		String unitStr = (unit != null) ? "#" + unit : "";
		return number + " " + street + " " + unitStr;
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

	public static void main(String[] args) {
		System.out.println("Testing address parsing of jgeocoder...");
		Map parsedAddr = AddressParser
				.parseAddress("Google Inc, 1600 Amphitheatre Parkway, Mountain View, CA 94043");
		System.out.println(parsedAddr);

		Map<AddressComponent, String> normalizedAddr = AddressStandardizer
				.normalizeParsedAddress(parsedAddr);
		System.out.println(normalizedAddr);
	}

}
