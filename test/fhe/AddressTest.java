package fastoffering;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import fhe.Address;
import fhe.City;

public class AddressTest {

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
}
