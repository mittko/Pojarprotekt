package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class MyPDFEventHandler extends PdfPageEventHelper {
    public int pageNumber;
    
	public MyPDFEventHandler() {
	   pageNumber = 0;
	 }
	@Override
	public void onStartPage(PdfWriter writer,Document document) {
		pageNumber++;
		Rectangle rect = writer.getBoxSize("art");
		  ColumnText.showTextAligned(writer.getDirectContent(),
                  Element.ALIGN_CENTER, new Phrase(String.format("страница %d", pageNumber),
                		  new PdfCreator().getFontAndSize("arial",9)),
                  (rect.getLeft() + rect.getRight()) / 2, rect.getBottom()-35, 0);
     }
}
