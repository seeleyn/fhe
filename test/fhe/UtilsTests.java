package fhe;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTests {

	@Test
	public void stringEqualsTests() {
		assertTrue(Utils.stringEquals(null,null));
		assertTrue(Utils.stringEquals("1","1"));
		
		assertFalse(Utils.stringEquals(null,"1"));
		assertFalse(Utils.stringEquals("1",null));
		assertFalse(Utils.stringEquals("1","2"));

	}
	
}
