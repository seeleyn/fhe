package fhe;

import java.util.Map;

import net.sourceforge.jgeocoder.AddressComponent;
import net.sourceforge.jgeocoder.us.AddressParser;
import net.sourceforge.jgeocoder.us.AddressStandardizer;

import org.junit.*;
import static org.junit.Assert.*;

public class AddressTests {

	@Test
	public void testAddressParser() {

		System.out.println("Testing address parsing of jgeocoder...");
		// Map<AddressComponent, String> parsedAddr =
		// AddressParser.parseAddress("1343 S 1370 E, Provo, UT 84606");
		Map<AddressComponent, String> parsedAddr = AddressParser
				.parseAddress("2052 California Ave Apt 7 Provo, Utah 84606");
		System.out.println(parsedAddr);

		Map<AddressComponent, String> normalizedAddr = AddressStandardizer.normalizeParsedAddress(parsedAddr);
		System.out.println(normalizedAddr);

		assertEquals("1", "1");
	}
	
	@Test
	public void testEquals() {
		DeerhavenAddress addr1 = new DeerhavenAddress(2052,"California Ave","7","Provo","Utah","84606");
		assertTrue(addr1.equals(new DeerhavenAddress(2052,"California Ave","7","Provo","Utah","84606")));
		
		assertFalse(addr1.equals(null));
		assertFalse(addr1.equals("bogus"));		
		assertFalse(addr1.equals(new DeerhavenAddress(0,"California Ave","7","Provo","Utah","84606")));
		assertFalse(addr1.equals(new DeerhavenAddress(2052,"Bogus","7","Provo","Utah","84606")));
		assertFalse(addr1.equals(new DeerhavenAddress(2052,"California Ave","Bogus","Provo","Utah","84606")));
	}

	@Test
	public void testAddressConstructor() {
		DeerhavenAddress address = new DeerhavenAddress("2052 California Ave Apt 7 Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2052,"California","7","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1344 S 1500 E Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1344,"S 1500 E",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1436 S. State St. Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1436,"State",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1448 E 1370 S Provo, Utah");
		assertEquals(new DeerhavenAddress(1448,"E 1370 S",null,"Provo","Utah","84606"), address);
		
		//address = new Address2("1458 East 1320 South Provo, Utah 84606 ");
		//assertEquals(new Address2(1458,"E 1320 S",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1465 E 1370 S Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1465,"E 1370 S",null,"Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1747 Oregon Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1747,"Oregon",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1857 Nevada Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1857,"Nevada",null,"Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1968 Washington Ave. Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1968,"Washington",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("2022 Park Street Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2022,"Park",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("2052 California Ave #7 Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2052,"California","7","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("2052 California Ave Apt 7 Provo, Utah 84606 ");
		assertEquals(new DeerhavenAddress(2052,"California","7","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("2057 Arizona Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2057,"Arizona",null,"Provo","Utah","84606"), address);

		address = new DeerhavenAddress("2083 California Ave Apt C Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2083,"California","C","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("2144 Dakota Ave Apt A Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2144,"Dakota","A","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("2144 Nevada Ave #2 Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2144,"Nevada","2","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("2173 State St Apt 1 Provo, Utah 84606 ");
		assertEquals(new DeerhavenAddress(2173,"State","1","Provo","Utah","84606"), address); //TODO what about South state?

		address = new DeerhavenAddress("2416 Tennessee Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2416,"Tennessee",null,"Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1015 Canyon Meadow Dr #6");
		assertEquals(new DeerhavenAddress(1015,"Canyon Meadow","6","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1015 Canyon Meadow Dr#6");
		assertEquals(new DeerhavenAddress(1015,"Canyon Meadow","6","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1028 Canyon Vista Rd#7");
		assertEquals(new DeerhavenAddress(1028,"Canyon Vista","7","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1028 S Canyon Vista Dr #8"); //TODO Are Canyon Vista Rd and S. Canyon Vista Dr the same road?
		assertEquals(new DeerhavenAddress(1028,"Canyon Vista","8","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1034 S Canyon Meadow Dr #3"); // Compare with just Canyon Meadow or Canyon Meadow Drive
		assertEquals(new DeerhavenAddress(1034,"Canyon Meadow","3","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1034 S Canyon Meadow Dr #3");
		assertEquals(new DeerhavenAddress(1034,"Canyon Meadow","3","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1034 Canyon Meadow Dr #3");
		assertEquals(new DeerhavenAddress(1034,"Canyon Meadow","3","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1068 Canyon Meadow Dr. #6");
		assertEquals(new DeerhavenAddress(1068,"Canyon Meadow","6","Provo","Utah","84606"), address);	

		address = new DeerhavenAddress("1068 S. Canyon Meadow Dr #4");
		assertEquals(new DeerhavenAddress(1068,"Canyon Meadow","4","Provo","Utah","84606"), address);	

		address = new DeerhavenAddress("1068 South Canyon Meadow Drive #4");
		assertEquals(new DeerhavenAddress(1068,"Canyon Meadow","4","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1068 SOuTh Canyon Meadow DR. #4");
		assertEquals(new DeerhavenAddress(1068,"Canyon Meadow","4","Provo","Utah","84606"), address);	
		
		address = new DeerhavenAddress("1077 Canyon Meadow #4");
		assertEquals(new DeerhavenAddress(1077,"Canyon Meadow","4","Provo","Utah","84606"), address);	
		
		address = new DeerhavenAddress("1676 E 1080 S #6");
		assertEquals(new DeerhavenAddress(1676,"E 1080 S","6","Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1676 EasT 1080 SouTh #6");
		assertEquals(new DeerhavenAddress(1676,"E 1080 S","6","Provo","Utah","84606"), address);
		
		address = new DeerhavenAddress("1676 E. 1080 S. #6");
		assertEquals(new DeerhavenAddress(1676,"E 1080 S","6","Provo","Utah","84606"), address);	
	}

}
