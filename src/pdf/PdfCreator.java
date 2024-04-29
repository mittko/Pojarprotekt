package pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import exceptions.InOutException;
import exceptions.PDFException;
import log.IOErrorsWriter;

import log.PdfErr;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreator {

	// private String target = System.getProperty("user.home")+"/Desktop/";
	public Font cyrylic;
	private BaseFont bf = null;
	public final String arial = "arial";
	public String arialbd = "arialbd";
	public final String italic = "italic";
	public final Font arial10 = getFontAndSize(arial, 10);

	public final Font arial11 = getFontAndSize(arial,11);
	public final Font arial9 = getFontAndSize(arial, 9);
	public final Font arial8 = getFontAndSize(arial, 8);
	public final Font arial7 = getFontAndSize(arial, 7);

	public PdfCreator() {
	}

	public Font getFontAndSize(String font, int size) {
		Font f = new Font(getBaseFont(font));
		f.setSize(size);
		return f;
	}

	protected void setBaseFont(String font) {

		// try {

		this.bf = getCyrilycBaseFont(font);// "arial" // BaseFont.createFont(
		// "Fonts/" + font + ".ttf"
		// ,"cp1251",BaseFont.EMBEDDED);

		/*
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * InOutException.showIOException(e);
		 * IOErrorsWriter.writeIO(e.toString()); e.printStackTrace(); } catch
		 * (DocumentException e) { // TODO Auto-generated catch block
		 * InOutException.showIOException(e);
		 * IOErrorsWriter.writeIO(e.toString()); e.printStackTrace(); }
		 */

	}

	public BaseFont getBaseFont(String font) {
		this.setBaseFont(font);
		return this.bf;
	}

	public static BaseFont getCyrilycBaseFont(String font) {
		BaseFont baseFont = null;
		try {
			baseFont = BaseFont.createFont("fonts/" + font + ".ttf", "cp1251",
					BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			InOutException.showErrorMessage(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
		}
		return baseFont;
	}


	public Document document;
	public PdfWriter writer;
	public PdfContentByte content;
	public String workingDir;
	public FileOutputStream fos;
	public boolean init(String target, String timeStamp, String num) {
		document = new Document(PageSize.A4, 50.f, 50.f, 50.f, 50.f);

		try {
			fos = new FileOutputStream(target
					+ timeStamp + "-" + num + ".pdf");

			writer = PdfWriter.getInstance(document, fos);

			writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));

			// set num of pages handler
			MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
			writer.setPageEvent(pdfEventHandler);

			document.open();

			content = writer.getDirectContent();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InOutException.showErrorMessage(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}
		workingDir = System.getProperty("user.dir");
		return true;
	}
	public Image getItextImage(String path) {
		Image image = null;
		try {
			image = Image.getInstance(path);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	public void setText(String text, float x, float y, String font, float size) {
		content.beginText();
		content.moveText(x, y);
		content.setFontAndSize(getBaseFont(font), size);
		content.showText(text);
		content.endText();
	}

	public void finish() {
		document.close();
		// System.out.println("done!");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("*******");
	}

}
