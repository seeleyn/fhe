package utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import fhe.Apartment;

public class MarkdownRenderer {

	public static String toMarkdown(String reportName, Map<String, List<Apartment>> apartmentGroupings) {
		StringBuilder out = new StringBuilder();
		out.append("# " + reportName + System.lineSeparator());
		out.append(System.lineSeparator());
		for (String label : apartmentGroupings.keySet()) {
			out.append("## " + label + System.lineSeparator());
			out.append(System.lineSeparator());
			List<Apartment> apartments = apartmentGroupings.get(label);
			Collections.sort(apartments);
			out.append("| Address | Members |" + System.lineSeparator());
			out.append("| ------- | ------- |" + System.lineSeparator());
			for (Apartment apt : apartments) {
				out.append("| " + apt.getAddress() + "|" + StringUtils.join(apt.getResidents(),"; ") + "|" + System.lineSeparator());
			}

		}
		return out.toString();
	}

}
