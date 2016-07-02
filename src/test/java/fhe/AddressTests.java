package fhe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;

public class AddressTests {

	@Test
	public void testCompareTo() {
		List<Address> addresses = new ArrayList<Address>();
		Address a0 = new Address("900 Main # D");
		Address a1 = new Address("968 S 1000 E");
		Address a2 = new Address("101 Elm Street, Springville, UT");
		Address a3 = new Address("961 Main # C");
		Address a4 = new Address("961 Main Apt A");
		Address a5 = new Address("100 Elm Street, Springville, UT");
		Address a6 = new Address("450 E 2000 S");
		Address a7 = new Address("961 Main Apt B");
		Address a8 = new Address("500 E 1000 S");

		addresses.add(a0);
		addresses.add(a1);
		addresses.add(a2);
		addresses.add(a3);
		addresses.add(a4);
		addresses.add(a5);
		addresses.add(a6);
		addresses.add(a7);
		addresses.add(a8);

		Collections.sort(addresses);
		assertEquals(addresses.indexOf(a8), 0);
		assertEquals(addresses.indexOf(a6), 1);
		assertEquals(addresses.indexOf(a0), 2);
		assertEquals(addresses.indexOf(a4), 3);
		assertEquals(addresses.indexOf(a7), 4);
		assertEquals(addresses.indexOf(a3), 5);
		assertEquals(addresses.indexOf(a1), 6);
		assertEquals(addresses.indexOf(a5), 7);
		assertEquals(addresses.indexOf(a2), 8);
	}

	@Test
	public void testCompareTo2() {
		Address a0 = new Address("900 Main");
		Address a1 = new Address("900 Main");
		assertEquals(0, a0.compareTo(a1));
	}

	@Test
	public void testCompareTo3() {
		List<Address> addresses = new ArrayList<Address>();

		Address a0 = new Address("1000 Center St");
		Address a1 = new Address("9 Center St");
		Address a2 = new Address("10000 Center St");
		Address a3 = new Address("99 Center St");
		Address a4 = new Address("100 Center St");
		Address a5 = new Address("9999 Center St");
		Address a6 = new Address("999 Center St");
		Address a7 = new Address("10 Center St");

		addresses.add(a0);
		addresses.add(a1);
		addresses.add(a2);
		addresses.add(a3);
		addresses.add(a4);
		addresses.add(a5);
		addresses.add(a6);
		addresses.add(a7);

		Collections.sort(addresses);
		assertEquals(addresses.indexOf(a1), 0);
		assertEquals(addresses.indexOf(a7), 1);
		assertEquals(addresses.indexOf(a3), 2);
		assertEquals(addresses.indexOf(a4), 3);
		assertEquals(addresses.indexOf(a6), 4);
		assertEquals(addresses.indexOf(a0), 5);
		assertEquals(addresses.indexOf(a5), 6);
		assertEquals(addresses.indexOf(a2), 7);
	}

	@Test
	public void testCompareTo4() {
		List<Address> addresses = new ArrayList<Address>();
		Address a0 = new Address(0, null, "A", City.PROVO);
		Address a1 = new Address(1, "Aardvark", "A", City.PROVO);
		Address a2 = new Address(0, null, null, null);
		Address a3 = new Address(0, "Aardvark", "A", City.PROVO);
		Address a4 = new Address(0, null, null, City.PROVO);
		Address a5 = new Address(0, null, null, null);
		Address a6 = new Address(0, "Aardvark", null, City.PROVO);

		addresses.add(a0);
		addresses.add(a1);
		addresses.add(a2);
		addresses.add(a3);
		addresses.add(a4);
		addresses.add(a5);
		addresses.add(a6);

		Collections.sort(addresses);
		assertEquals(true, addresses.indexOf(a5) == 0 || addresses.indexOf(a5) == 1);
		assertEquals(true, addresses.indexOf(a2) == 0 || addresses.indexOf(a2) == 1);
		assertEquals(addresses.indexOf(a4), 2);
		assertEquals(addresses.indexOf(a0), 3);
		assertEquals(addresses.indexOf(a6), 4);
		assertEquals(addresses.indexOf(a3), 5);
		assertEquals(addresses.indexOf(a1), 6);
	}

	@Test
	public void testEquals() {
		Address addr1 = new Address(2052, "California Ave", "7", City.PROVO);
		assertTrue(addr1.equals(new Address(2052, "California Ave", "7", City.PROVO)));

		assertFalse(addr1.equals(null));
		assertFalse(addr1.equals("bogus"));
		assertFalse(addr1.equals(new Address(0, "California Ave", "7", City.PROVO)));
		assertFalse(addr1.equals(new Address(2052, "Bogus", "7", City.PROVO)));
		assertFalse(addr1.equals(new Address(2052, "California Ave", "Bogus", City.PROVO)));
	}

	@Test
	public void testAddressConstructor() {
		Address address = new Address("2052 California Ave Apt 7 Provo, Utah 84606");
		assertEquals(new Address(2052, "California", "7", City.PROVO), address);

		address = new Address("1344 S 1500 E Provo, Utah 84606");
		assertEquals(new Address(1344, "S 1500 E", null, City.PROVO), address);

		address = new Address("1436 S. State St. Provo, Utah 84606");
		assertEquals(new Address(1436, "State", null, City.PROVO), address);

		address = new Address("1448 E 1370 S Provo, Utah");
		assertEquals(new Address(1448, "E 1370 S", null, City.PROVO), address);

		// address = new Address2("1458 East 1320 South Provo, Utah 84606 ");
		// assertEquals(new
		// Address2(1458,"E 1320 S",null,"Provo","Utah","84606"), address);

		address = new Address("1465 E 1370 S Provo, Utah 84606");
		assertEquals(new Address(1465, "E 1370 S", null, City.PROVO), address);

		address = new Address("1465 E. 1370 S. Provo, Utah 84606");
		assertEquals(new Address(1465, "E 1370 S", null, City.PROVO), address);

		address = new Address("1465 East 1370 South Provo, Utah 84606");
		assertEquals(new Address(1465, "E 1370 S", null, City.PROVO), address);

		address = new Address("1747 Oregon Ave Provo, Utah 84606");
		assertEquals(new Address(1747, "Oregon", null, City.PROVO), address);

		address = new Address("1857 Nevada Ave Provo, Utah 84606");
		assertEquals(new Address(1857, "Nevada", null, City.PROVO), address);

		address = new Address("1968 Washington Ave. Provo, Utah 84606");
		assertEquals(new Address(1968, "Washington", null, City.PROVO), address);

		address = new Address("2022 Park Street Provo, Utah 84606");
		assertEquals(new Address(2022, "Park", null, City.PROVO), address);

		address = new Address("2052 California Ave #7 Provo, Utah 84606");
		assertEquals(new Address(2052, "California", "7", City.PROVO), address);

		address = new Address("2052 California Ave Apt 7 Provo, Utah 84606 ");
		assertEquals(new Address(2052, "California", "7", City.PROVO), address);

		address = new Address("2057 Arizona Ave Provo, Utah 84606");
		assertEquals(new Address(2057, "Arizona", null, City.PROVO), address);

		address = new Address("2083 California Ave Apt C Provo, Utah 84606");
		assertEquals(new Address(2083, "California", "C", City.PROVO), address);

		address = new Address("2144 Dakota Ave Apt A Provo, Utah 84606");
		assertEquals(new Address(2144, "Dakota", "A", City.PROVO), address);

		address = new Address("2144 Nevada Ave #2 Provo, Utah 84606");
		assertEquals(new Address(2144, "Nevada", "2", City.PROVO), address);

		address = new Address("2173 State St Apt 1 Provo, Utah 84606 ");
		assertEquals(new Address(2173, "State", "1", City.PROVO), address); // TODO
																			// what
																			// about
																			// South
																			// state?

		address = new Address("2416 Tennessee Ave Provo, Utah 84606");
		assertEquals(new Address(2416, "Tennessee", null, City.PROVO), address);

		address = new Address("1015 Canyon Meadow Dr #6");
		assertEquals(new Address(1015, "Canyon Meadow", "6", City.PROVO), address);

		address = new Address("1015 Canyon Meadow Dr#6");
		assertEquals(new Address(1015, "Canyon Meadow", "6", City.PROVO), address);

		address = new Address("1028 Canyon Vista Rd#7");
		assertEquals(new Address(1028, "Canyon Vista", "7", City.PROVO), address);

		address = new Address("1028 S Canyon Vista Dr #8"); // TODO Are
															// Canyon
															// Vista
															// Rd
															// and
															// S.
															// Canyon
															// Vista
															// Dr
															// the
															// same
															// road?
		assertEquals(new Address(1028, "Canyon Vista", "8", City.PROVO), address);

		address = new Address("1034 S Canyon Meadow Dr #3"); // Compare
																// with
																// just
																// Canyon
																// Meadow
																// or
																// Canyon
																// Meadow
																// Drive
		assertEquals(new Address(1034, "Canyon Meadow", "3", City.PROVO), address);

		address = new Address("1034 S Canyon Meadow Dr #3");
		assertEquals(new Address(1034, "Canyon Meadow", "3", City.PROVO), address);

		address = new Address("1034 Canyon Meadow Dr #3");
		assertEquals(new Address(1034, "Canyon Meadow", "3", City.PROVO), address);

		address = new Address("1068 Canyon Meadow Dr. #6");
		assertEquals(new Address(1068, "Canyon Meadow", "6", City.PROVO), address);

		address = new Address("1068 S. Canyon Meadow Dr #4");
		assertEquals(new Address(1068, "Canyon Meadow", "4", City.PROVO), address);

		address = new Address("1068 South Canyon Meadow Drive #4");
		assertEquals(new Address(1068, "Canyon Meadow", "4", City.PROVO), address);

		address = new Address("1068 SOuTh Canyon Meadow DR. #4");
		assertEquals(new Address(1068, "Canyon Meadow", "4", City.PROVO), address);

		address = new Address("1077 Canyon Meadow #4");
		assertEquals(new Address(1077, "Canyon Meadow", "4", City.PROVO), address);

		address = new Address("1676 E 1080 S #6");
		assertEquals(new Address(1676, "E 1080 S", "6", City.PROVO), address);

		address = new Address("1676 EasT 1080 SouTh #6");
		assertEquals(new Address(1676, "E 1080 S", "6", City.PROVO), address);

		address = new Address("1676 E. 1080 S. #6");
		assertEquals(new Address(1676, "E 1080 S", "6", City.PROVO), address);

		address = new Address("1145 S Meadow Fork Rd #1");
		assertEquals(new Address(1145, "Meadow Fork", "1", City.PROVO), address);

		address = new Address("1145 South Meadow Fork Road#1");
		assertEquals(new Address(1145, "Meadow Fork", "1", City.PROVO), address);

		address = new Address("1145 S. Meadow Fork Rd.#1");
		assertEquals(new Address(1145, "Meadow Fork", "1", City.PROVO), address);

		address = new Address("1346 South 1440 North Provo, Utah 84606");
		assertEquals(new Address(1346, "S 1440 N", null, City.PROVO), address);

		address = new Address("1300 East 1440 North Provo, Utah 84606");
		assertEquals(new Address(1300, "E 1440 N", null, City.PROVO), address);
	}
}
