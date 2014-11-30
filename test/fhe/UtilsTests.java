package fhe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import utils.StringUtils;
import org.junit.Test;

public class UtilsTests {

	@Test
	public void stringEqualsTests() {
		assertTrue(StringUtils.stringEquals(null, null));
		assertTrue(StringUtils.stringEquals("1", "1"));
		assertTrue(StringUtils.stringEquals("a", "a"));

		assertFalse(StringUtils.stringEquals(null, "1"));
		assertFalse(StringUtils.stringEquals("1", null));
		assertFalse(StringUtils.stringEquals("1", "2"));
		assertFalse(StringUtils.stringEquals("a", "A"));
	}

	@Test
	public void stringEqualsIgnoreCaseTests() {
		assertTrue(StringUtils.stringEqualsIgnoreCase(null, null));
		assertTrue(StringUtils.stringEqualsIgnoreCase("1", "1"));
		assertTrue(StringUtils.stringEqualsIgnoreCase("a", "a"));
		assertTrue(StringUtils.stringEqualsIgnoreCase("a", "A"));

		assertFalse(StringUtils.stringEqualsIgnoreCase(null, "1"));
		assertFalse(StringUtils.stringEqualsIgnoreCase("1", null));
		assertFalse(StringUtils.stringEqualsIgnoreCase("1", "2"));

	}

}
