package utils;

import fhe.Column;
import fhe.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by seeleyn on 7/1/16.
 */
public class FileUtils {

	public static String readFileIntoString(String path) throws Exception {
		if (path == null) {
			throw new IllegalArgumentException("null input param:  path- "
					+ (path == null));
		}
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		try {
			String line = in.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = in.readLine();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
		return stringBuilder.toString();
	}
}
