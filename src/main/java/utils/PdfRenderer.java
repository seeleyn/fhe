package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fastoffering.ApartmentComparator;
import fhe.Apartment;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by nate on 7/2/16.
 */
public class PdfRenderer {

	private static final BaseColor TABLE_HEADER_COLOR = new GrayColor(.8f);

	public static String createReportForAllRoutes(String reportName, Map<String, List<Apartment>> apartmentGroupings) {
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
				out.append("| " + apt.getAddress() + "|" + StringUtils.join(apt.getResidents(), "; ") + "|"
						+ System.lineSeparator());
			}

		}
		return out.toString();
	}

	public static Document createRouteReport(String routeName, List<Apartment> apartments, String outputFile) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputFile));
		document.open();
		Paragraph title = new Paragraph(new Phrase(10f, routeName,FontFactory.getFont(FontFactory.TIMES_BOLD,18.0f)));
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);

		table.setHeaderRows(1);
		BaseColor tableHeaderColor = TABLE_HEADER_COLOR;
		PdfPCell addressHeader = new PdfPCell(new Phrase("Address"));
		addressHeader.setBackgroundColor(tableHeaderColor);
		table.addCell(addressHeader);

		PdfPCell membersHeader = new PdfPCell(new Phrase("Members"));
		membersHeader.setBackgroundColor(tableHeaderColor);
		table.addCell(membersHeader);

		Collections.sort(apartments, new ApartmentComparator());
		for (Apartment apt : apartments) {
			table.addCell(apt.getAddress().toString());
			table.addCell(StringUtils.join(apt.getResidents(), ";"));
		}
		document.add(table);
		document.close();
		return document;
	}
}
