package generators;

import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;
import Log.PdfErr;
import PDF.OpenPDFDocument;
import PDF.PdfCreator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import mydate.MyGetDate;
import utility.MainPanel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GenerateBarcod extends PdfCreator {
	Image img;
	/*
	 * private static String target = System.getProperty("user.home") +
	 * "/Desktop/";
	 */

	// private static String LOGO = "src/Images/logo.png";

	private static boolean LAST = false;

	public static boolean generateBarcodAsPDF(String code, String out) {

		File f = new File(MainPanel.BARCODE_PDF_PATH + out);
		if (f.exists()) {
			JOptionPane.showMessageDialog(null, "Този номер е използван !!!");
			return false;
		}

		Document document = new Document(new Rectangle(100f, 40f)); // 100 40
																	// work
																	// configure
																	// barcod
																	// paper
																	// size
																	// 100,45

		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					MainPanel.BARCODE_PDF_PATH + out));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}
		document.open();

		PdfContentByte cb = writer.getDirectContent();
		cb.setLineWidth(1f);
		BarcodeEAN codeEAN = new BarcodeEAN();
		float imgX = 5;
		float imgY = document.getPageSize().getHeight() - 35; // 40

		codeEAN.setCodeType(Barcode.EAN13);
		codeEAN.setCode(code);


		cb.beginText();
		cb.moveText(imgX+20,imgY-3);
		BaseFont bf = PdfCreator.getCyrilycBaseFont("arial");
		cb.setFontAndSize(bf,4);
		cb.showText(MyGetDate.getDate_Days_Hours());
		cb.endText();

		try {
			Image img = codeEAN.createImageWithBarcode(cb, null, null);

			img.setAbsolutePosition(imgX, imgY);// imgX imgY - 35/40

			document.add(img);


		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}
		document.close();

		return true;
	}

	private static void drawLine(int x1, int y1, int x2, int y2,
			PdfContentByte canvas, float lineWidth) {
		canvas.setLineWidth(lineWidth);
		canvas.moveTo(x1, y1);
		canvas.lineTo(x2, y2);
		canvas.closePathStroke();

	}

	private static void drawCircle(float x, float y, float radius,
			PdfContentByte cb) {
		cb.circle(x, y, radius);
		cb.fill();
		// cb.stroke();
	}

	private static PdfTemplate createText(String text, PdfContentByte cb,
										  BaseFont bf, int fontSize) {
		PdfTemplate template = cb.createTemplate(200, 200);
		template.beginText();
		template.setFontAndSize(bf, fontSize);
		template.showText(text);
		template.endText();
		return template;
	}

	public static boolean generateBarcodOnStickerAsPDF(String code, String out,
			String nextDate) {
		Document document = new Document(new Rectangle(120f, 360f)); // local
																		// new
																		// Document(new
																		// Rectangle(120f
																		// ,
																		// 720f)
																		// );

		/*File f = new File(MainPanel.BARCODE_PDF_PATH + out);
		if (f.exists()) {
			JOptionPane.showMessageDialog(null, "Този номер е използван !!!");
			return false;
		}*/

		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					MainPanel.BARCODE_PDF_PATH + out));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}
		document.open();

		PdfContentByte cb = writer.getDirectContent();

		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(Barcode.EAN13);
		codeEAN.setCode(code);

		try {
			Image img = codeEAN.createImageWithBarcode(cb, null, null);
			float imgX = 5;
			float imgY = document.getPageSize().getHeight() - 107;// 35 local
			img.setAbsolutePosition(imgX, imgY - 368);// 370);
			img.scaleToFit(70, 370);// local (150, 370);

			document.add(img);

			System.out.println("pos = " + (imgY - 370));// -117.0
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}
		// pasteText(writer.getDirectContent(), nextDate, 90);

		pasteText(writer.getDirectContent(), nextDate, 65);

		// pasteText(writer.getDirectContent(), nextDate, 50);

		// pasteText(writer.getDirectContent(), nextDate, 30);

		// pasteText(writer.getDirectContent(), nextDate, 0);
		document.close();

		return true;
	}

	private static void pasteText(PdfContentByte cb, String nextDate, float yy) {

		BaseFont bf = PdfCreator.getCyrilycBaseFont("arial");
		/*
		 * try { bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252",
		 * false); } catch (DocumentException | IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// rotated text at an absolute position
		PdfTemplate currDateT = createText(MyGetDate.getReversedSystemDate(), cb,
				bf, 8);// 14);

		PdfTemplate nextDateT = createText(nextDate, cb, bf, 8);// 14);vv

		PdfTemplate stickerText = createText("стикер за", cb, bf, 7);

		PdfTemplate TO_Text = createText("ТО", cb, bf, 14);
		PdfTemplate P_Text = createText("П", cb, bf, 14);
		PdfTemplate HI_Text = createText("ХИ", cb, bf, 14);
		PdfTemplate mechanic = createText("техник", cb, bf, 7);

		float rotate = 90;
		float x = 6f;// 17f;
		float y = yy;// rabotti -> 500;// 700;
		float angle = (float) (rotate * (Math.PI / 180));
		float xScale = (float) Math.cos(angle);
		float yScale = (float) Math.cos(angle);
		float xRot = (float) -Math.sin(angle);
		float yRot = (float) Math.sin(angle);

		// дата на следващо обслужване
		cb.addTemplate(nextDateT, xScale, xRot, yRot, yScale, 0f, y + 20);
		// дата на текущо обслужване
		cb.addTemplate(currDateT, xScale, xRot, yRot, yScale, x, y + 10);

		// cb.addTemplate(currDateT, xScale, xRot, yRot, yScale, x, y + 35);
		// cb.addTemplate(currDateT, xScale, xRot, yRot, yScale, x, y + 55);
		// права линия която подчертава целия текст
		// drawLine((int)x+50, (int)y+20,(int) x + 50 ,(int) y - 114,cb,
		// 0.4f);//

		// техническо обслужване
		/*
		 * cb.addTemplate(stickerText, xScale, xRot, yRot, yScale,x+35f,
		 * y+20);// стикер за drawCircle((float)x+25, (float)y+9, 9.0f, cb);//
		 * кръг за перфориране cb.addTemplate(TO_Text,xScale, xRot, yRot,
		 * yScale,x+20f, y + 2);// ТО drawLine((int)x+13, (int)y - 20,(int) x +
		 * 50,(int) y - 20,cb,1.0f);// разделителна линия
		 */// вътрешна разделителна линия
			// drawLine((int)x+20, (int)y + 6,(int) x + 50,(int) y + 6,cb,
			// 0.5f);// разделителна линия

		// презареждане
		/*
		 * y -= 40; cb.addTemplate(stickerText, xScale, xRot, yRot,
		 * yScale,x+50f, y+20);// стикер за drawCircle((float)x+35, (float)y+13,
		 * 6.0f, cb);//кръг за перфориране cb.addTemplate(P_Text,xScale, xRot,
		 * yRot, yScale,x+30f, y + 6);// П drawLine((int)x+20, (int)y - 14,(int)
		 * x + 50,(int) y - 14,cb);//разделителна линия
		 */
		// техническо обслужване
		/*
		 * y -= 40; cb.addTemplate(stickerText, xScale, xRot, yRot,
		 * yScale,x+50f, y+20);// стикер за drawCircle((float)x+35, (float)y+13,
		 * 6.0f, cb);//кръг за перфориране cb.addTemplate(HI_Text,xScale, xRot,
		 * yRot, yScale,x+30f, y + 6);// П drawLine((int)x+20, (int)y - 14,(int)
		 * x + 50,(int) y - 14,cb);//разделителна линия
		 */
		// техник
		/*
		 * y -= 40; cb.addTemplate(mechanic,xScale, xRot, yRot, yScale,x+50f, y
		 * + 20);// техник drawLine((int)x+20, (int)y - 14,(int) x + 50,(int) y
		 * - 14,cb);// разделителна линия
		 */

		// drawLine((int)x+20, (int)y - 15,(int) x + 50,(int) y - 15,cb);

		// drawLine((int)x+20, (int)y - 40,(int) x + 50,(int) y - 40,cb);

	}

	// zxing

	/*
	 * public void generateBarcodeImage(String code, String out) {
	 * 
	 * File file = new File(target); if (!file.exists()) { file.mkdirs(); }
	 * 
	 * Image img = null; Document document = new Document(new Rectangle(350,
	 * 350)); PdfWriter writer = null; try { writer =
	 * PdfWriter.getInstance(document, new FileOutputStream( target + out)); }
	 * catch (FileNotFoundException e) { // TODO Auto-generated catch block
	 * InOutException.showIOException(e); IOErrorsWriter.writeIO(e.toString());
	 * // e.printStackTrace(); } catch (DocumentException e) { // TODO
	 * Auto-generated catch block PDFException.showPDFException(e);
	 * PdfErr.pdfErros(e.toString()); // e.printStackTrace(); }
	 * 
	 * document.open(); URL url =
	 * this.getClass().getResource("/Images/logo2.png"); java.awt.Image awtImage
	 * = Toolkit.getDefaultToolkit().createImage(url); try { img =
	 * com.itextpdf.text.Image.getInstance(awtImage, null); document.add(img); }
	 * catch (BadElementException e) { // TODO Auto-generated catch block
	 * IOErrorsWriter.writeIO(e.toString()); // e.printStackTrace(); } catch
	 * (IOException e) { // TODO Auto-generated catch block
	 * InOutException.showIOException(e); IOErrorsWriter.writeIO(e.toString());
	 * // e.printStackTrace(); } catch (DocumentException e) { // TODO
	 * Auto-generated catch block PDFException.showPDFException(e);
	 * PdfErr.pdfErros(e.toString()); //e.printStackTrace(); } BarcodeEAN ean =
	 * new BarcodeEAN(); ean.setCodeType(Barcode.EAN13); Font fontbold =
	 * FontFactory.getFont("Times-Roman", 12, Font.BOLD);
	 * FontFactory.register("Fonts/FreeMono.ttf", "my_bold_font");
	 * 
	 * // Font myBoldFont = FontFactory.getFont("my_bold_font"); BaseFont bf =
	 * fontbold.getBaseFont(); ean.setFont(bf);
	 * 
	 * ean.setCode(code); img =
	 * ean.createImageWithBarcode(writer.getDirectContent(), null, null); try {
	 * document.add(img); } catch (DocumentException e) { // TODO Auto-generated
	 * catch block PDFException.showPDFException(e);
	 * PdfErr.pdfErros(e.toString()); // e.printStackTrace(); }
	 * 
	 * document.close(); }
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 generateBarcodAsPDF("4858484884122","test.pdf");
		 OpenPDFDocument.pdfRunner(MainPanel.BARCODE_PDF_PATH+"\\"+"test.pdf");
		//generateBarcodOnStickerAsPDF("4858484884122", "test1.pdf", "01.01.2019");
		//OpenPDFDocument.pdfRunner("C:/Program1/tmp/BarcodeImage/test1.pdf");
	}

}
