package PDF;

import java.io.IOException;

import Exceptions.InOutException;
import Log.IOErrorsWriter;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class PdfCreator {

	// private String target = System.getProperty("user.home")+"/Desktop/";
	public Font cyrylic;
	private BaseFont bf = null;

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
			baseFont = BaseFont.createFont("Fonts/" + font + ".ttf", "cp1251",
					BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
		}
		return baseFont;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("*******");
	}

}
