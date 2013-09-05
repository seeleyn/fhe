package fhe;

import org.junit.*;
import static org.junit.Assert.*;

public class AddressTests {

	@Test
	public void testEquals() {
		DeerhavenAddress addr1 = new DeerhavenAddress(2052, "California Ave", "7", City.PROVO);
		assertTrue(addr1.equals(new DeerhavenAddress(2052, "California Ave", "7", City.PROVO)));

		assertFalse(addr1.equals(null));
		assertFalse(addr1.equals("bogus"));
		assertFalse(addr1.equals(new DeerhavenAddress(0, "California Ave", "7", City.PROVO)));
		assertFalse(addr1.equals(new DeerhavenAddress(2052, "Bogus", "7", City.PROVO)));
		assertFalse(addr1.equals(new DeerhavenAddress(2052, "California Ave", "Bogus", City.PROVO)));
	}

	@Test
	public void testAddressConstructor() {
		DeerhavenAddress address = new DeerhavenAddress("2052 California Ave Apt 7 Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2052, "California", "7", City.PROVO), address);

		address = new DeerhavenAddress("1344 S 1500 E Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1344, "S 1500 E", null, City.PROVO), address);

		address = new DeerhavenAddress("1436 S. State St. Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1436, "State", null, City.PROVO), address);

		address = new DeerhavenAddress("1448 E 1370 S Provo, Utah");
		assertEquals(new DeerhavenAddress(1448, "E 1370 S", null, City.PROVO), address);

		// address = new Address2("1458 East 1320 South Provo, Utah 84606 ");
		// assertEquals(new
		// Address2(1458,"E 1320 S",null,"Provo","Utah","84606"), address);

		address = new DeerhavenAddress("1465 E 1370 S Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1465, "E 1370 S", null, City.PROVO), address);

		address = new DeerhavenAddress("1465 E. 1370 S. Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1465, "E 1370 S", null, City.PROVO), address);

		address = new DeerhavenAddress("1465 East 1370 South Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1465, "E 1370 S", null, City.PROVO), address);

		address = new DeerhavenAddress("1747 Oregon Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1747, "Oregon", null, City.PROVO), address);

		address = new DeerhavenAddress("1857 Nevada Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1857, "Nevada", null, City.PROVO), address);

		address = new DeerhavenAddress("1968 Washington Ave. Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(1968, "Washington", null, City.PROVO), address);

		address = new DeerhavenAddress("2022 Park Street Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2022, "Park", null, City.PROVO), address);

		address = new DeerhavenAddress("2052 California Ave #7 Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2052, "California", "7", City.PROVO), address);

		address = new DeerhavenAddress("2052 California Ave Apt 7 Provo, Utah 84606 ");
		assertEquals(new DeerhavenAddress(2052, "California", "7", City.PROVO), address);

		address = new DeerhavenAddress("2057 Arizona Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2057, "Arizona", null, City.PROVO), address);

		address = new DeerhavenAddress("2083 California Ave Apt C Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2083, "California", "C", City.PROVO), address);

		address = new DeerhavenAddress("2144 Dakota Ave Apt A Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2144, "Dakota", "A", City.PROVO), address);

		address = new DeerhavenAddress("2144 Nevada Ave #2 Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2144, "Nevada", "2", City.PROVO), address);

		address = new DeerhavenAddress("2173 State St Apt 1 Provo, Utah 84606 ");
		assertEquals(new DeerhavenAddress(2173, "State", "1", City.PROVO), address); // TODO
																					// what
																					// about
																					// South
																					// state?

		address = new DeerhavenAddress("2416 Tennessee Ave Provo, Utah 84606");
		assertEquals(new DeerhavenAddress(2416, "Tennessee", null, City.PROVO), address);

		address = new DeerhavenAddress("1015 Canyon Meadow Dr #6");
		assertEquals(new DeerhavenAddress(1015, "Canyon Meadow", "6", City.PROVO), address);

		address = new DeerhavenAddress("1015 Canyon Meadow Dr#6");
		assertEquals(new DeerhavenAddress(1015, "Canyon Meadow", "6", City.PROVO), address);

		address = new DeerhavenAddress("1028 Canyon Vista Rd#7");
		assertEquals(new DeerhavenAddress(1028, "Canyon Vista", "7", City.PROVO), address);

		address = new DeerhavenAddress("1028 S Canyon Vista Dr #8"); // TODO Are
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
		assertEquals(new DeerhavenAddress(1028, "Canyon Vista", "8", City.PROVO), address);

		address = new DeerhavenAddress("1034 S Canyon Meadow Dr #3"); // Compare
																		// with
																		// just
																		// Canyon
																		// Meadow
																		// or
																		// Canyon
																		// Meadow
																		// Drive
		assertEquals(new DeerhavenAddress(1034, "Canyon Meadow", "3", City.PROVO), address);

		address = new DeerhavenAddress("1034 S Canyon Meadow Dr #3");
		assertEquals(new DeerhavenAddress(1034, "Canyon Meadow", "3", City.PROVO), address);

		address = new DeerhavenAddress("1034 Canyon Meadow Dr #3");
		assertEquals(new DeerhavenAddress(1034, "Canyon Meadow", "3", City.PROVO), address);

		address = new DeerhavenAddress("1068 Canyon Meadow Dr. #6");
		assertEquals(new DeerhavenAddress(1068, "Canyon Meadow", "6", City.PROVO), address);

		address = new DeerhavenAddress("1068 S. Canyon Meadow Dr #4");
		assertEquals(new DeerhavenAddress(1068, "Canyon Meadow", "4", City.PROVO), address);

		address = new DeerhavenAddress("1068 South Canyon Meadow Drive #4");
		assertEquals(new DeerhavenAddress(1068, "Canyon Meadow", "4", City.PROVO), address);

		address = new DeerhavenAddress("1068 SOuTh Canyon Meadow DR. #4");
		assertEquals(new DeerhavenAddress(1068, "Canyon Meadow", "4", City.PROVO), address);

		address = new DeerhavenAddress("1077 Canyon Meadow #4");
		assertEquals(new DeerhavenAddress(1077, "Canyon Meadow", "4", City.PROVO), address);

		address = new DeerhavenAddress("1676 E 1080 S #6");
		assertEquals(new DeerhavenAddress(1676, "E 1080 S", "6", City.PROVO), address);

		address = new DeerhavenAddress("1676 EasT 1080 SouTh #6");
		assertEquals(new DeerhavenAddress(1676, "E 1080 S", "6", City.PROVO), address);

		address = new DeerhavenAddress("1676 E. 1080 S. #6");
		assertEquals(new DeerhavenAddress(1676, "E 1080 S", "6", City.PROVO), address);

		address = new DeerhavenAddress("1145 S Meadow Fork Rd #1");
		assertEquals(new DeerhavenAddress(1145, "Meadow Fork", "1", City.PROVO), address);

		address = new DeerhavenAddress("1145 South Meadow Fork Road#1");
		assertEquals(new DeerhavenAddress(1145, "Meadow Fork", "1", City.PROVO), address);

		address = new DeerhavenAddress("1145 S. Meadow Fork Rd.#1");
		assertEquals(new DeerhavenAddress(1145, "Meadow Fork", "1", City.PROVO), address);
	}
}
