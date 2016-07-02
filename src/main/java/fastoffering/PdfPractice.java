package fastoffering;

//import org.pegdown.Extensions;
//import org.pegdown.PegDownProcessor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import utils.FileUtils;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * Created by seeleyn on 7/1/16.
 */
public class PdfPractice {

	public static void main(String[] args) throws Exception {
/*
		String documentation = FileUtils.readFileIntoString(args[0]);

		System.out.println(documentation);
		PegDownProcessor processor = new PegDownProcessor(
				Extensions.ALL);
		String html = processor.markdownToHtml(documentation);
		System.out.println("\n\n\n");
		System.out.println(html);
*/
		Document document = new Document();
		PdfWriter.getInstance(document,
				new FileOutputStream("/tmp/HelloWorld.pdf"));

		document.open();
		document.add(new Paragraph("A Hello World PDF document."));
		PdfPTable table = new PdfPTable(2);
		table.addCell("Address");
		table.addCell("Members");
		table.addCell("321 Main St");
		table.addCell("Potter, Harry");
		table.addCell("529 Elm");
		table.addCell("Granger, Hermione");
		document.add(table);
		document.close(); // no need to close PDFwriter?

	}
}
