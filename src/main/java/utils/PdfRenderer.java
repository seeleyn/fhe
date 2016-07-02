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

	public static void createReportForAllRoutes(String reportName, Map<String, List<Apartment>> apartmentGroupings,
			String outputFile) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputFile));
		document.open();
		Paragraph title = new Paragraph(
				new Phrase(10f, reportName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18.0f)));
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		for (String routeLabel : apartmentGroupings.keySet()) {
			Paragraph routeTitle = new Paragraph(new Phrase(30f, routeLabel, FontFactory.getFont(FontFactory.TIMES_BOLD, 14.0f)));
			routeTitle.setAlignment(Element.ALIGN_LEFT);
			document.add(routeTitle);
			List<Apartment> apartments = apartmentGroupings.get(routeLabel);
			PdfPTable routeTable = createTable(apartments);
			document.add(routeTable);
		}
		document.close();
		return;
	}

	public static void createRouteReport(String routeName, List<Apartment> apartments, String outputFile)
			throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputFile));
		document.open();
		Paragraph title = new Paragraph(new Phrase(10f, routeName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18.0f)));
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		PdfPTable table = createTable(apartments);
		document.add(table);
		document.close();
		return;
	}

	private static PdfPTable createTable(List<Apartment> apartments) {
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
		return table;
	}
}
