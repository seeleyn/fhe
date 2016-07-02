package fastoffering;

import org.pegdown.PegDownProcessor;

/**
 * Created by seeleyn on 7/1/16.
 */
public class PdfPractice {

	public static void main(String[] args) {

		String markdown =
				"# Header 1\n" +
				"## Header2\n" +
				"\n" +
				"| Address | Members |\n" +
				"| ------- | ------- |\n" +
				"| 131 Main, PROVO|Granger, Hermione|\n"+
				"| 131 Elm, PROVO|Potter, Harry|\n";
		System.out.println(markdown);
		PegDownProcessor pegDownProcessor = new PegDownProcessor();
		String html = pegDownProcessor.markdownToHtml(markdown);
		System.out.println(html);
	}
}
