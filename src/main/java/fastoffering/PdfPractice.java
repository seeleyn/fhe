package fastoffering;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import utils.FileUtils;

/**
 * Created by seeleyn on 7/1/16.
 */
public class PdfPractice {

	public static void main(String[] args) throws Exception {

		String documentation = FileUtils.readFileIntoString(args[0]);

		System.out.println(documentation);
		PegDownProcessor processor = new PegDownProcessor(
				Extensions.ALL);
		String html = processor.markdownToHtml(documentation);
		System.out.println("\n\n\n");
		System.out.println(html);
	}
}
