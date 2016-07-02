package utils;

import java.util.Collection;

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

	public static <T extends Object> String join(Collection<T> values, String delimiter) {
		StringBuilder sb = new StringBuilder();
		if (values != null) {
			int index = 0;
			for (T obj : values) {
				sb.append(String.valueOf(obj));
				if (index < values.size() - 1)
					sb.append(delimiter);
				index++;
			}
		}
		return sb.toString();
	}

}
