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
			throw new IllegalArgumentException("address parameter cannot be null");
		}
		Map<AddressComponent, String> parsedAddr = AddressParser.parseAddress(address_);
		if (parsedAddr == null) {
			throw new IllegalArgumentException("Cannot parse address '"+address_+"'");
		}
		number = Utils.safeParseInt(parsedAddr.get(AddressComponent.NUMBER), 0);
		StringBuilder streetBuilder = new StringBuilder();
		if (parsedAddr.get(AddressComponent.PREDIR) != null) {
			streetBuilder.append(parsedAddr.get(AddressComponent.PREDIR) + " ");
		}
		streetBuilder.append(parsedAddr.get(AddressComponent.STREET));
		if (parsedAddr.get(AddressComponent.POSTDIR) != null) {
			streetBuilder.append(" " + parsedAddr.get(AddressComponent.POSTDIR));
		}
		if (parsedAddr.get(AddressComponent.TYPE) != null) {
			streetBuilder.append(" " + parsedAddr.get(AddressComponent.TYPE));
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

		if (parsedAddr.get(AddressComponent.CITY) != null) 
			city = parsedAddr.get(AddressComponent.CITY);
		
		if (parsedAddr.get(AddressComponent.STATE) != null) 
			state = parsedAddr.get(AddressComponent.STATE);		
			
		if (parsedAddr.get(AddressComponent.ZIP) != null)
			zipCode = parsedAddr.get(AddressComponent.ZIP);
	}

	public Address2(int number_, String street_, String unit_, String city_, String state_, String zipCode_) {
		this.number = number_;
		this.street = street_;
		this.unit = unit_;
		this.city = city_;
		this.state = state_;
		this.zipCode = zipCode_;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Address2)) {
			return false;
		}
		Address2 other = (Address2) obj;
		if (number != other.number)
			return false;

		if (!Utils.stringEquals(street, other.street)) {
			return false;
		}

		if (!Utils.stringEquals(unit, other.unit)) {
			return false;
		}

		if (!Utils.stringEquals(city, other.city)) {
			return false;
		}

		if (!Utils.stringEquals(state, other.state)) {
			return false;
		}

		if (!Utils.stringEquals(zipCode, other.zipCode)) {
			return false;
		}

		return true;
	}

	public int compareTo(Address obj) {
		return toString().compareTo(obj.toString());
	}

	public String toString() {
		String unitStr = (unit != null) ? "#" + unit : "";
		return number + " " + street + " " + unitStr + ", " + city + ", " + state + " " + zipCode;
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
