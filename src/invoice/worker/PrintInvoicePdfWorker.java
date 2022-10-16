package invoice.worker;

import JPrinter.Print.PrintWithoutOpenPdf;
import PDF.Invoice.InvoicePDF;
import PDF.Invoice.MaterialsPDFromInvocie;
import PDF.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utility.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintInvoicePdfWorker extends SwingWorker {

	private final String invoiceNumber;
	private final String currentClient;
	private final String datePdf;
	private final String invoiceName;

	private final double sum;
	private final String payment;
	boolean isCreated = false;
	private final DefaultTableModel dftm;

	public PrintInvoicePdfWorker(DefaultTableModel dftm, String currentClient,
			String invoiceNumber, String datePdf, String invoiceName, double sum,
			String payment, PrintService ps, JDialog jd) {
		this.dftm = dftm;
		this.currentClient = currentClient;
		this.invoiceNumber = invoiceNumber;
		this.datePdf = datePdf;
		this.invoiceName = invoiceName;
		this.sum = sum;
		this.payment = payment;
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

			String[] ORIGINAL = { "ОРИГИНАЛ", "", "" };

			String[] timeStamps = { MyGetDate.getTimeStamp() + "a",
					MyGetDate.getTimeStamp() + "b", MyGetDate.getTimeStamp() + "c" };
			int[] copies = { 1, 2 };
			if(!invoiceName.isEmpty()) {
				for (int i = 0; i < 1; i++) {
					InvoicePDF pdf = new InvoicePDF();
					isCreated = pdf.createInvoicePDF(clientInfo, invoiceNumber,
							timeStamps[i], datePdf, payment, invoiceName,
							MainPanel.INVOICE_PDF_PATH + "\\Фактура-", "ФАКТУРА",
							ORIGINAL[i],sum/1.2f,
							MainPanel.personName);

					// update invoice number
					if (isCreated) {

						 OpenPDFDocument.pdfRunner(MainPanel.INVOICE_PDF_PATH +
						 "\\Фактура-"
						 + timeStamps[0] + "-" + invoiceNumber + ".pdf");

//						PrintWithoutOpenPdf.printWithoutDialog(
//								MainPanel.INVOICE_PDF_PATH, "\\Фактура-"
//										+ timeStamps[i] + "-" + invoiceNumber
//										+ ".pdf", ps, copies[i]);

						// print materials

					}

					MaterialsPDFromInvocie materialsPDFromInvocie = new MaterialsPDFromInvocie();
					materialsPDFromInvocie.createMaterialsPDF(clientInfo,dftm,timeStamps[0],
							invoiceNumber,datePdf,0,dftm.getRowCount(),4);

					  OpenPDFDocument.pdfRunner(MainPanel.MATERIALS_PDF_PATH+
					  "\\Вложени Материали-"+ timeStamps[0] + "-" + invoiceNumber +
					  ".pdf");

				}
			} else {
				DefaultTableModel mergedTableModel = mergeArtikuls(dftm);
				for (int i = 0; i < 2; i++) {
					InvoicePDF pdf = new InvoicePDF();
					isCreated = pdf.createInvoicePDF(clientInfo, invoiceNumber,
							timeStamps[i], datePdf, payment, mergedTableModel,
							MainPanel.INVOICE_PDF_PATH + "\\Фактура-", "ФАКТУРА",
							ORIGINAL[i], 0, mergedTableModel.getRowCount(),
							MainPanel.personName);

					// update invoice number
					if (isCreated) {

						 OpenPDFDocument.pdfRunner(MainPanel.INVOICE_PDF_PATH +
						 "\\Фактура-"
						 + timeStamps[0] + "-" + invoiceNumber + ".pdf");

//						PrintWithoutOpenPdf.printWithoutDialog(
//								MainPanel.INVOICE_PDF_PATH, "\\Фактура-"
//										+ timeStamps[i] + "-" + invoiceNumber
//										+ ".pdf", ps, copies[i]);

					}
				}
			}


		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					if (!isCreated) {
						JOptionPane.showMessageDialog(null,
								"Грешка при създаването на документа!");
					}
				}
			});
		}

		return null;
	}

	private DefaultTableModel mergeArtikuls(DefaultTableModel dftm) {
		HashMap<String, Integer> map = new HashMap<>();
		DefaultTableModel mergedData = new DefaultTableModel();
		mergedData.setColumnCount(8);
		int artikulsRow = 0;

		for(int position = 0;position < dftm.getRowCount();position++) {
			String artikul = dftm.getValueAt(position, 0).toString();
			if(!map.containsKey(artikul)) {
				mergedData.addRow(new Object[]{
						dftm.getValueAt(position,0),
						dftm.getValueAt(position,1),
						dftm.getValueAt(position,2),
						dftm.getValueAt(position,3),
						dftm.getValueAt(position,4),
						dftm.getValueAt(position,5),
						dftm.getValueAt(position,6),
						dftm.getValueAt(position,7) });
				map.put(artikul, artikulsRow);
				artikulsRow++;
			} else {
				int savedPos = map.get(artikul);
				int newQuantity = 0;
				try {
					newQuantity = Integer.parseInt(dftm.getValueAt(position, 2).toString());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				double newValue = 0;
				try {
					newValue = Double.parseDouble(dftm.getValueAt(position, 3).toString());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				int savedQuantity = 0;
				try {
					savedQuantity = Integer.parseInt(mergedData.getValueAt(savedPos, 2).toString());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				double savedValue = 0;
				try {
					Double.parseDouble(mergedData.getValueAt(savedPos, 3).toString());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				int maxQuantity = savedQuantity + newQuantity;
				double maxValue = Math.max(savedValue, newValue);
				mergedData.setValueAt(maxQuantity+"", savedPos, 2);
				mergedData.setValueAt(maxValue+"",savedPos,3);
				mergedData.setValueAt((maxQuantity * maxValue)+"", savedPos,4);
				// тук се пресмята и общото к-во и крайната стойност на артикулите
				// понеже тя не се пресмята в метода
				// createInvoicePdf на класа InvoicePdf
			}
		}
		return mergedData;
	}

}
