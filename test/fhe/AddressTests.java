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
		Address2 addr1 = new Address2(2052,"California Ave","7","Provo","Utah","84606");
		assertTrue(addr1.equals(new Address2(2052,"California Ave","7","Provo","Utah","84606")));
		
		assertFalse(addr1.equals(null));
		assertFalse(addr1.equals("bogus"));		
		assertFalse(addr1.equals(new Address2(0,"California Ave","7","Provo","Utah","84606")));
		assertFalse(addr1.equals(new Address2(2052,"Bogus","7","Provo","Utah","84606")));
		assertFalse(addr1.equals(new Address2(2052,"California Ave","Bogus","Provo","Utah","84606")));
		assertFalse(addr1.equals(new Address2(2052,"California Ave","7","Bogus","Utah","84606")));
		assertFalse(addr1.equals(new Address2(2052,"California Ave","7","Provo","Bogus","84606")));
		assertFalse(addr1.equals(new Address2(2052,"California Ave","7","Provo","Utah","Bogus")));
	}

	@Test
	public void testAddressConstructor() {
		Address2 address = new Address2("2052 California Ave Apt 7 Provo, Utah 84606");
		assertEquals(new Address2(2052,"California Ave","7","Provo","Utah","84606"), address);
		
		address = new Address2("1344 S 1500 E Provo, Utah 84606");
		assertEquals(new Address2(1344,"S 1500 E",null,"Provo","Utah","84606"), address);
		
		address = new Address2("1436 S. State St. Provo, Utah 84606");
		assertEquals(new Address2(1436,"S State St",null,"Provo","Utah","84606"), address);
		
		address = new Address2("1448 E 1370 S Provo, Utah");
		assertEquals(new Address2(1448,"E 1370 S",null,"Provo","Utah","84606"), address);
		
		//address = new Address2("1458 East 1320 South Provo, Utah 84606 ");
		//assertEquals(new Address2(1458,"E 1320 S",null,"Provo","Utah","84606"), address);
		
		address = new Address2("1465 E 1370 S Provo, Utah 84606");
		assertEquals(new Address2(1465,"E 1370 S",null,"Provo","Utah","84606"), address);

		address = new Address2("1747 Oregon Ave Provo, Utah 84606");
		assertEquals(new Address2(1747,"Oregon Ave",null,"Provo","Utah","84606"), address);
		
		address = new Address2("1857 Nevada Ave Provo, Utah 84606");
		assertEquals(new Address2(1857,"Nevada Ave",null,"Provo","Utah","84606"), address);

		address = new Address2("1968 Washington Ave. Provo, Utah 84606");
		assertEquals(new Address2(1968,"Washington Ave",null,"Provo","Utah","84606"), address);
		
		address = new Address2("2022 Park Street Provo, Utah 84601");
		assertEquals(new Address2(2022,"Park Street",null,"Provo","Utah","84601"), address);
		
		address = new Address2("2052 California Ave #7 Provo, Utah 84606");
		assertEquals(new Address2(2052,"California Ave","7","Provo","Utah","84606"), address);
		
		address = new Address2("2052 California Ave Apt 7 Provo, Utah 84606 ");
		assertEquals(new Address2(2052,"California Ave","7","Provo","Utah","84606"), address);

		address = new Address2("2057 Arizona Ave Provo, Utah 84606");
		assertEquals(new Address2(2057,"Arizona Ave",null,"Provo","Utah","84606"), address);

		address = new Address2("2083 California Ave Apt C Provo, Utah 84606");
		assertEquals(new Address2(2083,"California Ave","C","Provo","Utah","84606"), address);

		address = new Address2("2144 Dakota Ave Apt A Provo, Utah 84606");
		assertEquals(new Address2(2144,"Dakota Ave","A","Provo","Utah","84606"), address);
		
		address = new Address2("2144 Nevada Ave #2 Provo, Utah 84606");
		assertEquals(new Address2(2144,"Nevada Ave","2","Provo","Utah","84606"), address);

		address = new Address2("2173 State St Apt 1 Provo, Utah 84606 ");
		assertEquals(new Address2(2173,"State St","1","Provo","Utah","84606"), address);

		address = new Address2("2416 Tennessee Ave Provo, Utah 84606");
		assertEquals(new Address2(2416,"Tennessee Ave",null,"Provo","Utah","84606"), address);
		
		address = new Address2("1015 Canyon Meadow Dr #6");
		assertEquals(new Address2(1015,"Canyon Meadow Dr","6","Provo","Utah","84606"), address);

		//address = new Address2("1015 Canyon Meadow Dr#6");
		//assertEquals(new Address2(1015,"Canyon Meadow Dr","6","Provo","Utah","84606"), address);
		



	}

}
