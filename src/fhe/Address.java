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
import java.util.Scanner;

import net.sourceforge.jgeocoder.AddressComponent;
import net.sourceforge.jgeocoder.us.AddressParser;
import net.sourceforge.jgeocoder.us.AddressStandardizer;

/**
 *
 * @author n8
 */
public class Address implements Comparable<Address>
{

    private int number = 0;
    
    private Street street = null;
    
    private int unit = 0;
    
    /** Creates a new instance of Address */
    public Address(String address_) throws Exception
    {   
        Scanner scanner = new Scanner(address_);
        number = scanner.nextInt();
        street = parseStreet(address_);
        if (street != Street.ALPINE_LOOP && street != Street.ALPINE_WAY)
        {
            //parse the unit number unless this is an alpine street
            if (!address_.contains("#"))
                throw new Exception("no unit number in non-alpine street for '"+address_+"'");
            String unitStr = address_.substring(address_.lastIndexOf('#')+1,address_.length());
            unit = Integer.parseInt(unitStr);
        }
        if (street == Street._1080_S && address_.contains("1676"))
            number = 1676; //sometimes 1676 east 1080 south is written as 1080 south 1676 east
    }
    
    
    public static Street parseStreet(String addr) throws Exception
    {
        addr = addr.toLowerCase();
        if (addr.matches(".*meadow.*for.*"))
            return Street.MEADOW_F0RK;
        else if (addr.matches(".*alpine.*way.*"))
            return Street.ALPINE_WAY;
        else if (addr.matches(".*alpine.*loop.*"))
            return Street.ALPINE_LOOP;
        else if (addr.matches(".*canyon.*meadow.*") )
            return Street.CANYON_MEADOW;
        else if (addr.matches(".*canyon.*vista.*"))
            return Street.CANYON_VISTA;
        else if (addr.matches(".*1080.*s.*"))
            return Street._1080_S;
        System.out.println("cannot parse "+addr);
        return null;
    }
    
    
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Address))
        {
            return false;
        }
        Address other = (Address)obj;
        if (number == other.number && street==other.street && unit==other.unit)
        {
            return true;
        }
        else return false;
    }
    
    
    public int compareTo(Address obj)
    {
        return toString().compareTo(obj.toString());
    }
    
    public String toString()
    {
        String unitStr = (unit>0) ? "#"+unit : "";
        return number + " "+ street + " "+ unitStr;
    }

    public static void main(String[] args) {
    	System.out.println("Testing address parsing of jgeocoder...");
    	Map parsedAddr  = AddressParser.parseAddress("Google Inc, 1600 Amphitheatre Parkway, Mountain View, CA 94043");
        System.out.println(parsedAddr);
        
        Map<AddressComponent, String> normalizedAddr  = AddressStandardizer.normalizeParsedAddress(parsedAddr); 
        System.out.println(normalizedAddr);
    }
    
}
