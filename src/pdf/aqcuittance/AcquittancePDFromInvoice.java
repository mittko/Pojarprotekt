package pdf.aqcuittance;

import exceptions.PDFException;
import log.PdfErr;
import pdf.OpenPDFDocument;
import pdf.PdfCreator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mydate.MyGetDate;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

public class AcquittancePDFromInvoice extends PdfCreator {

	private String name = "";
	private String city = "";
	private String address = "";
	private String EIK = "";
	private String MOL = "";
	private String BANK = "";
	private String BIC = "";
	private String IBAN = "";
	private float nextAcquittanceTableY;
	private final int fontSize = 12;
	private String date;

	public void createAcquittancePDF2(ArrayList<String> clientInfo2,
			DefaultTableModel dftm, String timeStamp, String acquittanceNumber,
			String date, int startIndex, int endIndex, int finalSumIndex) {

		this.date = date;

		if (clientInfo2.size() > 0) {
			name = clientInfo2.get(0); // name or firm
			String TEL = "";
			if (clientInfo2.size() != 4) {
				city = clientInfo2.get(1); // 1 -> city
				address = clientInfo2.get(2);// 2 -> address
				EIK = extractOnlyDigit(clientInfo2.get(3));// 3 -> EIK
				MOL = clientInfo2.get(4);// name (MOL)
				// 5 -> tel of firm
				// 6 -> email
				// 7 -> person
				// 8 -> tel of person
				TEL = clientInfo2.get(7);// tel
				BANK = clientInfo2.get(8); // bank
				BIC = clientInfo2.get(9); // Bic
				IBAN = clientInfo2.get(10); // iban
				// 12 -> discount
			} else {
				TEL = clientInfo2.get(1); // 1 -> city
			}
		}
		// set document
		super.init(MainPanel.ACQUITTANCE_PDF_PATH + "\\������� ��������-"
				,timeStamp , acquittanceNumber );

		// arial10 = new Font(getBaseFont("arial"),fontSize);
		// set title
		float titleX = (document.right() / 2) - 100; // ??????
		float titleY = 800;


		setText("������� �������� \u2116 " + acquittanceNumber, titleX, titleY,
				"arialbd", 12);

		// set up table
		PdfPTable upTable = new PdfPTable(2);

		try {
			Image logo = Image.getInstance(MainPanel.LOGO_PATH);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Phrase sallerPhrase = new Phrase(new Chunk(logo,0,-35));

		// sallerPhrase.add(new Chunk(sallerText,arial10));

		// PdfPCell sallerInfo2Cell = new PdfPCell(sallerPhrase);

		PdfPCell sallerInfo2Cell = new PdfPCell();
		Phrase sallerPhrase = new Phrase(
				"��������� : " + MainPanel.SALLER_NAME, arial10);
		sallerInfo2Cell.addElement(sallerPhrase);
		Phrase cityPhrase = new Phrase("���� : " + MainPanel.SALLER_CITY,
				arial10);
		sallerInfo2Cell.addElement(cityPhrase);
		Phrase addressPhrase = new Phrase("�����: " + MainPanel.SALLER_ADDRESS,
				arial10);
		sallerInfo2Cell.addElement(addressPhrase);
		Phrase eikPhrase = new Phrase("���: " + MainPanel.SALLER_EIK, arial10);
		sallerInfo2Cell.addElement(eikPhrase);
		Phrase ddsPhrase = new Phrase("��� \u2116 BG" + MainPanel.SALLER_EIK,
				arial10);
		sallerInfo2Cell.addElement(ddsPhrase);
		Phrase molPhrase = new Phrase("���: " + MainPanel.SALLER_MOL, arial10);
		sallerInfo2Cell.addElement(molPhrase);
		Phrase bankPhrase = new Phrase("�����: " + MainPanel.SALLER_BANK,
				arial10);
		sallerInfo2Cell.addElement(bankPhrase);
		Phrase bicPhrase = new Phrase("BIC : " + MainPanel.SALLER_BIC, arial10);
		sallerInfo2Cell.addElement(bicPhrase);
		Phrase ibanPhrase = new Phrase("IBAN: " + MainPanel.SALLER_IBAN,
				arial10);
		sallerInfo2Cell.addElement(ibanPhrase);
		// set client cell

		PdfPCell clientInfo2Cell = new PdfPCell();
		Phrase namePhrase = new Phrase("���������:  "
				+ (!name.equals("-") ? name : ""), arial10);
		clientInfo2Cell.addElement(namePhrase);
		Phrase cityPhrase2 = new Phrase("����: "
				+ (!city.equals("-") ? city : ""), arial10);
		clientInfo2Cell.addElement(cityPhrase2);
		Phrase addressPhrase2 = new Phrase("�����: "
				+ (!address.equals("-") ? address : ""), arial10);
		clientInfo2Cell.addElement(addressPhrase2);
		Phrase eikPhrase2 = new Phrase("���: " + (!EIK.equals("-") ? EIK : ""),
				arial10);
		clientInfo2Cell.addElement(eikPhrase2);
		Phrase ddsPhrase2 = new Phrase("��� \u2116 BG"
				+ (!EIK.equals("-") ? EIK : ""), arial10);
		clientInfo2Cell.addElement(ddsPhrase2);
		Phrase molPhrase2 = new Phrase("���: " + (!MOL.equals("-") ? MOL : ""),
				arial10);
		clientInfo2Cell.addElement(molPhrase2);
		Phrase bankPhrase2 = new Phrase("�����: "
				+ (!BANK.equals("-") ? BANK : ""), arial10);
		clientInfo2Cell.addElement(bankPhrase2);
		Phrase bicPhrase2 = new Phrase("BIC " + (!BIC.equals("-") ? BIC : ""),
				arial10);
		clientInfo2Cell.addElement(bicPhrase2);
		Phrase ibanPhrase2 = new Phrase("IBAN: "
				+ (!IBAN.equals("-") ? IBAN : ""), arial10);
		clientInfo2Cell.addElement(ibanPhrase2);

		upTable.addCell(clientInfo2Cell);
		upTable.addCell(sallerInfo2Cell);

		upTable.setTotalWidth(500);

		float upTableX = 50;
		float upTableY = 790;
		upTable.writeSelectedRows(0, -1, upTableX, upTableY,
				writer.getDirectContent());

		//upTable.getTotalHeight();

		// set main table
		setMainTable(dftm, upTableX,
				upTableY - (upTable.getTotalHeight() + 15), startIndex,
				endIndex, finalSumIndex, writer);

		float x2 = upTableX;
		float y2 = nextAcquittanceTableY - 60;

		// set separator
		drawLine(writer.getDirectContent(), 0, 600.0f, y2 + 20);

		// repeat same table
		setText("������� �������� \u2116 " + acquittanceNumber, titleX,
				y2 - 10, "arialbd", 12);

		if(y2 - 20  - upTable.getTotalHeight() <= document.bottom()) {
			y2 = document.top();
			document.newPage();
		} else {
			y2 -= 20;
		}
		upTable.writeSelectedRows(0, -1, x2, y2,
				writer.getDirectContent());


		if(y2 - (upTable.getTotalHeight() + 30) <= document.bottom()) {
			y2 = document.top();
			document.newPage();
		} else {
			y2 -= (upTable.getTotalHeight() + 30);
		}
		setMainTable(dftm, x2, y2,
				startIndex, endIndex, finalSumIndex, writer);

		document.close();

	}

	private void setMainTable(DefaultTableModel dftm, float mainTableX,
			float mainTableY, int startIndex, int endIndex, int finalSumIndex,
			PdfWriter pdfWriter) {

		PdfPTable mainTable = new PdfPTable(6);

		mainTable.setTotalWidth(500);

		try {
			mainTable.setWidths(new int[] { 20, 300, 80, 80, 80, 80 });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}

		PdfPCell numCell = new PdfPCell(new Phrase("\u2116", arial10));
		numCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell artikulCell = new PdfPCell(new Phrase("�������", arial10));
		artikulCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell medCell = new PdfPCell(new Phrase("����� �-��", arial10));
		medCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell quantityCell = new PdfPCell(new Phrase("�-��", arial10));
		quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell priceCell = new PdfPCell(new Phrase("��.��-��", arial10));
		priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell valueCell = new PdfPCell(new Phrase("���� ��-��", arial10));
		valueCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		mainTable.addCell(numCell);
		mainTable.addCell(artikulCell);
		mainTable.addCell(medCell);
		mainTable.addCell(quantityCell);
		mainTable.addCell(priceCell);
		mainTable.addCell(valueCell);

		float bottomTextX = mainTableX + 20;
		// float bottomTextY = mainTableY;

		// bottomTextY -= mainTable.getRowHeight(0);
		float sumOfRows = mainTable.getRowHeight(0);
		int from = 0;
		int RANGE = 0;
		float mainTableNextY = mainTableY;
		float finalSum = 0;
		boolean go = false;

	//	 for(int i = 0;i < 10;i++) { // test cycle

		for (int row = 0; row < endIndex; row++) {

			RANGE++;

			finalSum += Float.parseFloat(dftm.getValueAt(row + startIndex, 4)
					.toString());


			PdfPCell rowCell = new PdfPCell(new Phrase(RANGE + "", arial10));
			rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			mainTable.addCell(rowCell);

			// System.out.println(sumOfRows + " " + document.bottom());

			String doing = dftm.getValueAt(row + startIndex, 0).toString();
			if (doing.contains("( ��� )")) {
				doing = doing.replace("( ��� )", "");
			}
			if (doing.contains("�����")) {
				doing = doing.replace("�����", "�");
			}
			PdfPCell doingCell = new PdfPCell(new Phrase(doing, arial10));
			doingCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			mainTable.addCell(doingCell);

			PdfPCell measureCell = new PdfPCell(new Phrase(dftm.getValueAt(
					row + startIndex, 1).toString(), arial10));
			measureCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			mainTable.addCell(measureCell);

			String quantity = (dftm.getValueAt(row + startIndex, 2).toString());

			PdfPCell quantCell = new PdfPCell(
					new Phrase(quantity + "", arial10));
			quantCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			mainTable.addCell(quantCell);

			PdfPCell pricCell = new PdfPCell(new Phrase(dftm.getValueAt(
					row + startIndex, 3).toString(), arial10));
			pricCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			mainTable.addCell(pricCell);

			PdfPCell valCell = new PdfPCell(new Phrase(dftm.getValueAt(
					row + startIndex, 4).toString(), arial10));
			valCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			mainTable.addCell(valCell);

			sumOfRows += mainTable.getRowHeight(row + 1); // row
			//
			if (mainTableNextY - (sumOfRows) < document.bottom()) {
				sumOfRows = 0;
				mainTable.writeSelectedRows(0, -1, from, RANGE + 1, mainTableX,
						mainTableNextY, pdfWriter.getDirectContent());
				mainTableNextY = document.top();
				from = RANGE + 1;
				document.newPage();
				go = true;
			}

		}

	// } // end of test cycle
		try {
			mainTable.setWidths(new int[] { 20, 300, 80, 80, 80, 80 });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}
		mainTable.writeSelectedRows(0, -1, from, RANGE + 1, mainTableX,
				mainTableNextY, pdfWriter.getDirectContent());

		float dateBottomX = bottomTextX - 10;
		float dateBottomY = (go ? document.top() - 15 : mainTableNextY - 15);
		for (int i = from; i < mainTable.getRows().size(); i++) {
			dateBottomY -= mainTable.getRowHeight(i);
		}

		setText(date, dateBottomX, dateBottomY, "arialbd", 10);

		float signatureX = dateBottomX;
		float signatureY = dateBottomY - 25;

		setText("������:", signatureX, signatureY, "arial", 10);

		float finalSumX = dateBottomX + 400;
		float finalSumY = dateBottomY;
		// double danak = (20 * finalSum) / 100;

		// ������ ���
		// 20 % // ������� �������� ��� ��� !!!111
		float ��� = 1.0f;
		finalSum *= ���;

		nextAcquittanceTableY = finalSumY;

		setText("���� : " + String.format("%.2f", MyMath.round(finalSum, 2)),
				finalSumX, finalSumY, "arialbd", 10);

	}

	public static void main(String[] args) {
		AcquittancePDFromInvoice pdf = new AcquittancePDFromInvoice();
		String timeStamp2 = MyGetDate.getTimeStamp();
		pdf.createAcquittancePDF2(new ArrayList<String>(),
				new DefaultTableModel(), timeStamp2, "1",
				MyGetDate.getReversedSystemDate(), 0, 0, 0);
		OpenPDFDocument.pdfRunner(MainPanel.ACQUITTANCE_PDF_PATH
				+ "\\������� ��������-" + timeStamp2 + "-" + "1.pdf");
	}

	private void drawLine(PdfContentByte cb, float startX, float endX,
			float startY) {

		cb.setLineWidth(0.5f); // Make a bit thicker than 1.0 default
		cb.setGrayStroke(0);// (0.95f); // 1 = black, 0 = white
		cb.moveTo(startX, startY);
		cb.lineTo(endX, startY);
		cb.stroke();
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
