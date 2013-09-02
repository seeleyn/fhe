package fhe;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTests {

	@Test
	public void stringEqualsTests() {
		assertTrue(Utils.stringEquals(null,null));
		assertTrue(Utils.stringEquals("1","1"));
		assertTrue(Utils.stringEquals("a","a"));
		
		assertFalse(Utils.stringEquals(null,"1"));
		assertFalse(Utils.stringEquals("1",null));
		assertFalse(Utils.stringEquals("1","2"));
		assertFalse(Utils.stringEquals("a", "A"));
	}
	
	@Test
	public void stringEqualsIgnoreCaseTests() {
		assertTrue(Utils.stringEqualsIgnoreCase(null,null));
		assertTrue(Utils.stringEqualsIgnoreCase("1","1"));
		assertTrue(Utils.stringEqualsIgnoreCase("a","a"));
		assertTrue(Utils.stringEqualsIgnoreCase("a", "A"));
		
		assertFalse(Utils.stringEqualsIgnoreCase(null,"1"));
		assertFalse(Utils.stringEqualsIgnoreCase("1",null));
		assertFalse(Utils.stringEqualsIgnoreCase("1","2"));

	}
	
}
