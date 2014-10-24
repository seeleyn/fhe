package utils;

public class StringUtils {

	public static boolean stringEquals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		} else {
			return str1.equals(str2);
		}
	}
	
	public static boolean stringEqualsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		} else {
			return str1.equalsIgnoreCase(str2);
		}
	}
	
	public static int safeParseInt(String input, int defaultValue) {
		if (input != null) {
			try {
				return Integer.parseInt(input);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
		return defaultValue;
	}
	
}
