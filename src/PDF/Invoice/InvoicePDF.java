package PDF.Invoice;

import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;
import Log.PdfErr;
import PDF.MyPDFEventHandler;
import PDF.PdfCreator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mydate.MyGetDate;
import utils.MainPanel;
import utils.MyMath;
import utils.convertFromDoubleToText;

import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class InvoicePDF extends PdfCreator {

	private final String targett = MainPanel.INVOICE_PDF_PATH;
	// private final String target =
	// System.getProperty("user.home")+"/Desktop/";
	private Document document;
	private PdfWriter pdfWriter;
	private PdfContentByte pcb;
	private float endX = 15;// ???
	private float endY = 820;
	private float sumOfRows = 0;
	private int from = 0;
	private float tablePos = 0;
	private float nextTablePos = 0;
	private double danak = 0.0f;

	private String name = "";
	private String city = "";
	private String address = "";
	private String EIK = "";
	private String DDS = "";
	private String MOL = "";
	private String TEL = "";
	private String BANK = "";
	private String BIC = "";
	private String IBAN = "";
	private int startIndex;
	private int endIndex;
	private PdfPCell rowCell;
	private PdfPCell partCell;
	private PdfPCell measureCell;
	private PdfPCell quantityCell;
	private PdfPCell priceCell;
	private PdfPCell valueCell;

	private float ДДС = 1.2f;// 20 %
	private String invoiceDate;
	final float nWidth = 5;
	final float doingWidth = 75;
	final float measureWidth = 8;
	final float quantityWidth = 5;;
	final float priceWidth = 8;
	final float finalSumWidth = 12;

	public boolean createInvoicePDF(ArrayList<String> clientInfo, String num,
			String timeStamp, String invoiceDate, String payment,
			DefaultTableModel dftm, String target, String TITLE, String TITLE2,
			int startIndex, int endIndex, String saller) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.invoiceDate = invoiceDate;


		/*
		 * for(int i = 0;i < clientInfo.size();i++) {
		 * System.out.printf("clientInfo.get(%d) = %s\n",i,clientInfo.get(i)); }
		 */
		if (clientInfo != null && clientInfo.size() != 0) {
			name = clientInfo.get(0); // name or firm
			if (clientInfo.size() != 4) {
				city = clientInfo.get(1); // 1 -> city
				address = clientInfo.get(2);// 2 -> address
				String registraciaDDS = extractOnlyDigit(clientInfo.get(3));// 3

				String isVatRegistered = clientInfo.get(clientInfo.size()-1);															// ->
				if(isVatRegistered.equals("да")) {
					DDS = "BG" + registraciaDDS;
					EIK =  registraciaDDS;
				} else {
					DDS = "";
					EIK =  registraciaDDS;
				}

				MOL = clientInfo.get(4);// name (MOL)
				// 5 -> tel of firm
				// 6 -> email
				TEL = clientInfo.get(7);// 7 -> person
				// 8 -> tel of person
				BANK = clientInfo.get(8); // bank
				BIC = clientInfo.get(9); // Bic
				IBAN = clientInfo.get(10); // iban
				// 11 -> discount
			} else {
				TEL = clientInfo.get(1); // 1 -> city
			}
		}
		// Rectangle rect = new Rectangle(210,297);
		document = new Document(PageSize.A4, 50, 50, 50, 50);

		try {
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
					target + timeStamp + "-" + num + ".pdf"));

			pdfWriter.setBoxSize("art", new Rectangle(36, 44, 559, 788));// (36,
																			// 54,
																			// 559,
																			// 788));

			// set num of each sheet
			MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
			pdfWriter.setPageEvent(pdfEventHandler);

			document.open();

			pcb = pdfWriter.getDirectContent();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		Font arial10 = getFontAndSize("arial", 10);// 10
		Font arial11 = getFontAndSize("arial", 11); // 11

		PdfPTable leftUPTable = new PdfPTable(2);

		PdfPCell clientInfoCell = new PdfPCell();
		clientInfoCell.addElement(new Phrase("Получател:  " + name, arial10));
		clientInfoCell.addElement(new Phrase("Град: " + city, arial10));
		clientInfoCell.addElement(new Phrase("Адрес: " + address, arial10));
		clientInfoCell.addElement(new Phrase("ЕИК: " + EIK, arial10));
		clientInfoCell.addElement(new Phrase("ДДС No: " + DDS, arial10));
		clientInfoCell.addElement(new Phrase("Мол: " + MOL, arial10));
		clientInfoCell.addElement(new Phrase(
				("Банка: " + (BANK.equals("-") ? "" : BANK)), arial10));
		clientInfoCell.addElement(new Phrase(("BIC: " + (BIC.equals("-") ? ""
				: BIC)), arial10));
		clientInfoCell.addElement(new Phrase(("IBAN: " + (IBAN.equals("-") ? ""
				: IBAN)), arial10));

		PdfPCell sailerInfoCell = new PdfPCell();
		sailerInfoCell.addElement(new Phrase("Доставчик: "
				+ MainPanel.SALLER_NAME, arial10));
		sailerInfoCell.addElement(new Phrase("Град: " + MainPanel.SALLER_CITY,
				arial10));
		sailerInfoCell.addElement(new Phrase("Адрес: "
				+ MainPanel.SALLER_ADDRESS, arial10));
		sailerInfoCell.addElement(new Phrase("ЕИК: " + MainPanel.SALLER_EIK,
				arial10));
		sailerInfoCell.addElement(new Phrase("ДДС \u2116 BG"
				+ MainPanel.SALLER_EIK, arial10));
		sailerInfoCell.addElement(new Phrase("Мол: " + MainPanel.SALLER_MOL,
				arial10));
		sailerInfoCell.addElement(new Phrase("Банка: " + MainPanel.SALLER_BANK,
				arial10));
		sailerInfoCell.addElement(new Phrase("BIC: " + MainPanel.SALLER_BIC,
				arial10));
		sailerInfoCell.addElement(new Phrase("IBAN: " + MainPanel.SALLER_IBAN,
				arial10));

		leftUPTable.addCell(clientInfoCell);
		leftUPTable.addCell(sailerInfoCell);
		/*
		 * try { leftUPTable.setWidths(new float[]{90,90}); } catch
		 * (DocumentException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		leftUPTable.setTotalWidth(550);

		leftUPTable.writeSelectedRows(0, -1, endX, endY,
				pdfWriter.getDirectContent());

		setTextWithSpacing(TITLE, 6.5f, 220,
				endY -= (leftUPTable.getTotalHeight() + 25), "arialbd", 13);

		PdfPTable numTable = new PdfPTable(1);

		Phrase phrase1 = new Phrase();
		phrase1.add(new Phrase("Място : Дупница", arial11));

		// Font arial11bditalic = getFontAndSize("italicbd",11);
		Font arialbd11 = getFontAndSize("arialbd", 11);

		phrase1.add(new Phrase(
				"                                               Дата : ",
				arial11));
		phrase1.add(new Phrase(invoiceDate, arialbd11));

		phrase1.add(new Phrase("                                 Номер :    ",
				arial11));
		phrase1.add(new Phrase(num, arialbd11));

		PdfPCell numCell = new PdfPCell();
		numCell.setPaddingBottom(7);
		numCell.addElement(phrase1);

		numTable.addCell(numCell);
		numTable.setTotalWidth(550);
		numTable.writeSelectedRows(0, -1, endX,
				endY -= (numTable.getTotalHeight() - 10),
				pdfWriter.getDirectContent());

		setTextWithSpacing(TITLE2, 6.5f, 220, endY - 50, "italicbd", 12);

		PdfPTable mainTable = new PdfPTable(6);

		mainTable.setTotalWidth(550);
		try {
			mainTable.setWidths(new float[] { nWidth, doingWidth, measureWidth,
					quantityWidth, priceWidth, finalSumWidth });// No|Вид на
																// стоката(услугата)|Мярка|К-во|Ед.Цена|Стойност
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			// e.printStackTrace();
			return false;
		}

		Font bdItalic9 = getFontAndSize("italicbd", 9);
		PdfPCell No = new PdfPCell(new Phrase("\u2116", bdItalic9));
		No.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell parts = new PdfPCell(new Phrase("Вида на стоката (услугата)",
				bdItalic9));
		parts.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell measure = new PdfPCell(new Phrase("Мярка", bdItalic9));
		measure.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell quantity = new PdfPCell(new Phrase("К-во", bdItalic9));
		quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell price = new PdfPCell(new Phrase("Ед.цена", bdItalic9));
		price.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell value = new PdfPCell(new Phrase("Стойност", bdItalic9));
		value.setHorizontalAlignment(Element.ALIGN_CENTER);

		mainTable.addCell(No);
		mainTable.addCell(parts);
		mainTable.addCell(measure);
		mainTable.addCell(quantity);
		mainTable.addCell(price);
		mainTable.addCell(value);

		sumOfRows += mainTable.getRowHeight(0);
		tablePos = endY - 65;
		nextTablePos = tablePos;
		int stop = endIndex;
		int row = 0;
		float danOsnova = 0;
		for (row = 0; dftm != null && row < stop; row++) {

			if (tablePos - sumOfRows < document.bottom()) {
				sumOfRows = 0;
				mainTable.writeSelectedRows(0, -1, from, row + 1, 30, tablePos,
						pdfWriter.getDirectContent());
				nextTablePos = 820;
				from = row + 1;
				document.newPage();
			}

			rowCell = new PdfPCell(new Phrase(row + 1 + "", arial10)); // row
			rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			String obslujvane = dftm.getValueAt(row + startIndex, 0).toString();
			/*
			 * if(obslujvane.contains("( Нов )")) { obslujvane =
			 * obslujvane.replace("( Нов )", ""); }
			 */
			if (obslujvane.contains("литра")) {
				obslujvane = obslujvane.replace("литра", "л");
			}
			partCell = new PdfPCell(new Phrase(obslujvane, arial10)); // uslugi
																		// //
																		// dftm.getValueAt(row+startIndex,column).toString()
			partCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			measureCell = new PdfPCell(new Phrase(dftm.getValueAt(
					row + startIndex, 1).toString(), arial10));// measure
			measureCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			quantityCell = new PdfPCell(new Phrase(dftm.getValueAt(
					row + startIndex, 2).toString(), arial10));
			quantityCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			double doublePrice = Double.parseDouble(dftm.getValueAt(
					row + startIndex, 3).toString());
			priceCell = new PdfPCell(new Phrase(String.format(Locale.ROOT,
					"%.2f", doublePrice), arial10));
			priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			double doubleValue = Double.parseDouble(dftm.getValueAt(
					row + startIndex, 4).toString());
			valueCell = new PdfPCell(new Phrase(String.format(Locale.ROOT,
					"%.2f", doubleValue), arial10));
			valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			danOsnova += doubleValue;// Double.parseDouble(dftm.getValueAt(row+startIndex,4).toString());

			mainTable.addCell(rowCell);
			mainTable.addCell(partCell);
			mainTable.addCell(measureCell);
			mainTable.addCell(quantityCell);
			mainTable.addCell(priceCell);
			mainTable.addCell(valueCell);

			sumOfRows += mainTable.getRowHeight(row + 1);

		}
		mainTable.setTotalWidth(550);
		try {
			mainTable.setWidths(new float[] { nWidth, doingWidth, measureWidth,
					quantityWidth, priceWidth, finalSumWidth });// No|Вид на
																// стоката(услугата)|Мярка|К-во|Ед.Цена|Стойност
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			// e.printStackTrace();
			return false;
		}
		mainTable.writeSelectedRows(0, -1, from, -1, endX, nextTablePos,
				pdfWriter.getDirectContent());

		endY = nextTablePos - (sumOfRows + 5);//

		PdfPTable ddsTable = new PdfPTable(2);

		// set ДДС
		danOsnova *= ДДС;
		// get sum in text
		danak = (((danOsnova / 1.2) * 20) / 100.0);

		String slovom = new convertFromDoubleToText().convertToText(String
				.format("%.2f", MyMath.round(danOsnova, 2))); // without LOCALE
																// because
																// convertFromDoubleToText()
																// work only
																// with comma

		Font arialbd10 = getFontAndSize("arialbd", 10);
		Phrase phrase2 = new Phrase();
		phrase2.add(new Phrase("Словом : ", arialbd10));
		Font bditalic10 = getFontAndSize("italicbd", 10);
		phrase2.add(new Phrase(slovom, bditalic10));
		PdfPCell slovomCell = new PdfPCell(phrase2);

		PdfPCell one = new PdfPCell(new Phrase("Основание за нулева ставка:",
				arial10));
		PdfPCell two = new PdfPCell(new Phrase(
				"Основание за не начислявяне на ДДС:", arial10));
		PdfPCell three = new PdfPCell(new Phrase(
				"Обст. определящи стоката ново ПС:", arial10));

		one.setColspan(2);
		two.setColspan(2);
		three.setColspan(2);

		ddsTable.addCell(slovomCell);

		PdfPTable leftDDSTable = new PdfPTable(1);

		leftDDSTable.addCell(new Phrase("Данъчна основа: "
				+ String.format(Locale.ROOT, "%.2f",
						MyMath.round(danOsnova / 1.2, 2)), arial10));
		leftDDSTable.addCell(new Phrase("Данъчна ставка (ДДС): 20%", arial10));
		leftDDSTable.addCell(new Phrase("Размер на данъка: "
				+ String.format(Locale.ROOT, "%.2f", danak), arial10));

		leftDDSTable.addCell(new Phrase(
				"Сума за плащане: "
						+ String.format(Locale.ROOT, "%.2f",
								MyMath.round(danOsnova, 2)), arialbd10));

		ddsTable.addCell(leftDDSTable);

		ddsTable.addCell(one);
		ddsTable.addCell(two);
		ddsTable.addCell(three);

		ddsTable.setTotalWidth(550);
		try {
			ddsTable.setWidths(new float[] { 250, 100 });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			// e.printStackTrace();
			return false;
		}
		if (endY - ddsTable.getTotalHeight() < document.bottom()) {
			endY = 820;
			document.newPage();
		}

		ddsTable.writeSelectedRows(0, -1, endX, endY,
				pdfWriter.getDirectContent());

		// increase endY
		endY -= (ddsTable.getTotalHeight() + 5);

		PdfPTable lastTable = new PdfPTable(2);
		PdfPCell leftCell = new PdfPCell(new Phrase("Дата на данъчно събитие: "
				+ invoiceDate + "\n" + "Стоката е получена от: " + "\n" + "Л.К"
				+ "\n" + "Подпис:", arial10));
		PdfPCell rightCell = new PdfPCell(new Phrase("Плащане : " + payment
				+ "\n" + "Съставил: " + saller + "\n\n" + "Подпис:", arial10));
		lastTable.addCell(leftCell);
		lastTable.addCell(rightCell);

		lastTable.setTotalWidth(550);
		try {
			lastTable.setWidths(new float[] { 250, 250 });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			// e.printStackTrace();
			return false;
		}
		if (endY - lastTable.getTotalHeight() < document.bottom()) {
			endY = 820;
			document.newPage();
		}

		lastTable.writeSelectedRows(0, -1, endX, endY,
				pdfWriter.getDirectContent());

		setTextWithSpacing("Съгласно чл.6 ал.1 от Закона на счетоводството, чл.114 от ЗДДС и чл.78 от" +
						" ППЗДДС печатът и подписът не са задължителни реквизити на фактура.", 0f, 20,
				endY - 60, "arial", 7);

		document.close();

		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InvoicePDF inv = new InvoicePDF();
		inv.createInvoicePDF(new ArrayList(), "0000000",
				MyGetDate.getTimeStamp(), MyGetDate.getReversedSystemDate(),
				"банков път", new DefaultTableModel(), inv.targett, "ФАКТУРА",
				"ОРИГИНАЛ", 0, 0, MainPanel.personName);

	}

	/*
	 * private void setText(String text,float x, float y,String font, float
	 * size) { pcb.beginText(); pcb.moveText(x,y);
	 * pcb.setFontAndSize(getBaseFont(font), size); pcb.showText(text);
	 * pcb.endText(); }
	 */
	private void setTextWithSpacing(String text, float space, float x, float y,
			String font, float size) {
		pcb.beginText();
		pcb.moveText(x, y);
		pcb.setFontAndSize(getBaseFont(font), size);
		float characterSpacing = pcb.getCharacterSpacing();
		pcb.setCharacterSpacing(space);
		pcb.showText(text);
		pcb.setCharacterSpacing(characterSpacing);
		pcb.endText();
	}

	private String extractOnlyDigit(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}
}
