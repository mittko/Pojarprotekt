package Reports.ReportsWorkers;

import PDF.Invoice.InvoicePDFFromReports;
import PDF.Invoice.MaterialsPDFromReports;
import PDF.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utility.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintInvoiceAndProformWorker extends SwingWorker {

	private final DefaultTableModel dftm;
	private final String currentClient;
	private final String invoiceNumber;
	private final String payment;
	private final String datePdf;
	private final JDialog jd;
	private boolean isCreated;
	private final int startIndex;
	private final int endIndex;
	private final String TITLE;
	private String TITLE2;
	private final String PATH;
	private final String saller;

	private final String invoiceName;

	private final double danOsnova;
	public PrintInvoiceAndProformWorker(PrintService ps,
			DefaultTableModel dftm, JDialog jd, int startIndex, int endIndex,
			String TITLE, String pdf_path) {
		this.dftm = dftm;
		this.currentClient = dftm.getValueAt(startIndex, 4).toString();
		this.invoiceNumber = dftm.getValueAt(startIndex, 0).toString();
		danOsnova = Double.parseDouble(dftm.getValueAt(startIndex, 3)
				.toString());
		this.payment = dftm.getValueAt(startIndex, 1).toString();
		this.datePdf = dftm.getValueAt(startIndex, 6).toString();
		this.invoiceName = dftm.getValueAt(startIndex,8).toString();
		this.saller = dftm.getValueAt(startIndex, 5).toString();
		this.jd = jd;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.TITLE = TITLE;
		this.PATH = pdf_path;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			// get client info
			ArrayList<String> clientInfo = ClientTable.getClientDetails(currentClient);
			String[] ДУБЛИКАТ = { MainPanel.personName.trim().equals("Администратор") ? "ОРИГИНАЛ"
					: " ДУБЛИКАТ", "", "" };
			String[] timeStamps = { MyGetDate.getTimeStamp() + "a",
					MyGetDate.getTimeStamp() + "b", MyGetDate.getTimeStamp() + "c" };
			int[] copies = { 1, 1 };
			if(!invoiceName.isEmpty()) {
				for (int i = 0; i < 1; i++) {
					InvoicePDFFromReports pdf = new InvoicePDFFromReports();

					isCreated = pdf.createInvoicePDF(clientInfo, invoiceNumber,
							timeStamps[i], datePdf, payment, invoiceName, PATH + "\\"
									+ TITLE + "-", TITLE, ДУБЛИКАТ[i],
							danOsnova, saller);

					// update invoice number
					if (isCreated) {

						OpenPDFDocument.pdfRunner(PATH + "\\" + TITLE + "-"
								+ timeStamps[i] + "-" + invoiceNumber + ".pdf");

						MaterialsPDFromReports materialsPDFromInvocie = new MaterialsPDFromReports();
						materialsPDFromInvocie.createMaterialsPDF(clientInfo,dftm,timeStamps[0],
								startIndex, endIndex);

						OpenPDFDocument.pdfRunner(MainPanel.MATERIALS_PDF_PATH+
								"\\Вложени Материали-"+ timeStamps[0] + "-" + invoiceNumber +
								".pdf");
				/*	PrintWithoutOpenPdf.printWithoutDialog(PATH, "\\" + TITLE
							+ "-" + timeStamps[i] + "-" + Number + ".pdf", ps,
							copies[i]);*/

					}


				}
			} else {
				System.out.println("default document");
				for (int i = 0; i < 2; i++) {
					InvoicePDFFromReports pdf = new InvoicePDFFromReports();
					DefaultTableModel mergedTableModel = mergeArtikuls(dftm, startIndex, endIndex);
					isCreated = pdf.createInvoicePDF(clientInfo, invoiceNumber,
							timeStamps[i], datePdf, payment, mergedTableModel, PATH + "\\"
									+ TITLE + "-", TITLE, ДУБЛИКАТ[i], 0,
							mergedTableModel.getRowCount()/*endIndex*/, saller);

					// update invoice number
					if (isCreated) {

						OpenPDFDocument.pdfRunner(PATH + "\\" + TITLE + "-"
								+ timeStamps[i] + "-" + invoiceNumber + ".pdf");

				/*	PrintWithoutOpenPdf.printWithoutDialog(PATH, "\\" + TITLE
							+ "-" + timeStamps[i] + "-" + Number + ".pdf", ps,
							copies[i]);*/

					}
				}
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (!isCreated) {
						JOptionPane.showMessageDialog(null,
								"Грешка при създаването на документа!");
					}
				}
			});
		}
		return null;

	}
	private DefaultTableModel mergeArtikuls(DefaultTableModel dftm, int startIndex, int stop) {
		HashMap<String, Integer> map = new HashMap<>();
		DefaultTableModel mergedData = new DefaultTableModel();
		mergedData.setColumnCount(16);
		int artikulsRow = 0;
		for(int i = 0;i < stop;i++) {
			int position = i + startIndex;
			String artikul = dftm.getValueAt(position, 10).toString();
			if(!map.containsKey(artikul)) {
				mergedData.addRow(new Object[]{
						dftm.getValueAt(position,0),
						dftm.getValueAt(position,1),
						dftm.getValueAt(position,2),
						dftm.getValueAt(position,3),
						dftm.getValueAt(position,4),
						dftm.getValueAt(position,5),
						dftm.getValueAt(position,6),
						dftm.getValueAt(position,7),
						dftm.getValueAt(position,8),
						dftm.getValueAt(position,9),
						dftm.getValueAt(position,10),
						dftm.getValueAt(position,11),
						dftm.getValueAt(position,12),
						dftm.getValueAt(position,13),
						dftm.getValueAt(position,14),
						dftm.getValueAt(position,15)
				});
				map.put(artikul, artikulsRow++);
			} else {
				int savedPos = map.get(artikul);
				int newQuantity = Integer.parseInt(dftm.getValueAt(position, 12).toString());
				int savedQuantity = Integer.parseInt(mergedData.getValueAt(savedPos, 12).toString());
				mergedData.setValueAt((savedQuantity + newQuantity)+"", savedPos, 12);
				// в този метод се пресмята само общото к-во на артикулите
				// но не се пресмята крайната стойност
				// защото тя се смята в метода createInvoicePdf на класа InvoicePdfFromReports
				//System.out.println(savedQuantity + newQuantity);
			}
		}
		return mergedData;
	}
}
