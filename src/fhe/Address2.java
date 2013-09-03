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
public class Address2 implements Comparable<Address> {

	private int number = 0;

	private String street = null;

	private String unit = null;

	private String city = "Provo";

	private String state = "UT";

	private String zipCode = "84606";
	
	private static String addCityStateZipIfNotPresent(String address_) {
		if (address_==null) {
			return null;
		}
		
		String addr = address_.toUpperCase().trim();
		//More hackery.... :(
		Pattern pattern = Pattern.compile(".*PROVO.*UT.*");
		Matcher matcher = pattern.matcher(addr);
		if (!matcher.matches()) {
			return address_ +" Provo, Utah 84606";
		} else {
			return address_;
		}
	}

	/** Creates a new instance of Address */
	public Address2(String address_) {
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
			throw new IllegalArgumentException("Cannot parse address "+address_);
		}
	}
	
	private static Matcher matcher(String regex,String input) {
		if (regex == null)
			return null;
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(input);
	}

	private static String cleanUpStreet(String rawStreet) {
		rawStreet = rawStreet.trim();
		Matcher matcher = matcher("^S[.](\\s+.*)",rawStreet);
		if (matcher.find()) {
			rawStreet = "S"+matcher.group(1);
		}
		
		matcher = matcher("(.*)ST[.]",rawStreet);
		if (matcher.find()) {
			rawStreet = matcher.group(1) + "ST";
		}
		
		matcher = matcher("(.*)AVE[.]",rawStreet);
		if (matcher.find()) {
			rawStreet = matcher.group(1) + "AVE";
		}
		return rawStreet;
	}
	
	public Address2(int number_, String street_, String unit_, String city_, String state_, String zipCode_) {
		this.number = number_;
		this.street = street_;
		this.unit = unit_;
		this.city = city_;
		this.state = state_;
		this.zipCode = zipCode_;
	}
	
	/**
	 * 
	 * @param aptNumString of the form "Apt 7" or "#7"
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
		if (!(obj instanceof Address2)) {
			return false;
		}
		Address2 other = (Address2) obj;
		if (number != other.number) {
			System.out.println("Comparing '"+number+"' and '"+other.number+"'");	
			return false;
		}
		
		if (!Utils.stringEqualsIgnoreCase(street, other.street)) {
			System.out.println("Failed compare on '"+street+"' and '"+other.street+"'");
			return false;
		}

		if (!Utils.stringEqualsIgnoreCase(unit, other.unit)) {
			System.out.println("Comparing '"+unit+"' and '"+other.unit+"'");	
			return false;
		}

		if (!Utils.stringEqualsIgnoreCase(city, other.city)) {
			System.out.println("Comparing '"+city+"' and '"+other.city+"'");
			return false;
		}

		
		if (!Utils.stringEqualsIgnoreCase(state, other.state)) {
			System.out.println("Comparing '"+state+"' and '"+other.state+"'");	
			return false;
		}

		if (!Utils.stringEqualsIgnoreCase(zipCode, other.zipCode)) {
			System.out.println("Comparing '"+zipCode+"' and '"+other.zipCode+"'");	
			return false;
		}

		return true;
	}

	public int compareTo(Address obj) {
		return toString().compareTo(obj.toString());
	}

	public String toString() {
		String unitStr = (unit != null) ? "#" + unit : "";
		return "num='"+number + "' street='" +  street + "', unit='" + unitStr + "', city='" + city + "', state='" + state + "', zip='" + zipCode+"'";
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
