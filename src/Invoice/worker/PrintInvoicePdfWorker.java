package Invoice.worker;

import JPrinter.Print.PrintWithoutOpenPdf;
import PDF.Invoice.InvoicePDF;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utility.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PrintInvoicePdfWorker extends SwingWorker {

	private ArrayList<String> clientInfo = null;
	private String invoiceNumber;
	private String currentClient;
	private String datePdf;
	// private double danOsnova;
	private String payment;
	private JDialog jd;
	boolean isCreated = false;
	private DefaultTableModel dftm;
	private PrintService ps;

	public PrintInvoicePdfWorker(DefaultTableModel dftm, String currentClient,
			String invoiceNumber, String datePdf, double danOsnova,
			String payment, PrintService ps, JDialog jd) {
		this.dftm = dftm;
		this.currentClient = currentClient;
		this.invoiceNumber = invoiceNumber;
		this.datePdf = datePdf;
		// this.danOsnova = danOsnova;
		this.payment = payment;
		this.ps = ps;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {
			// get client info
			clientInfo = ClientTable.getClientDetails(currentClient);

			// timeStamp = GetDate.getTimeStamp();

			String[] ORIGINAL = { "ОРИГИНАЛ", "", "" };

			String timeStamps[] = { MyGetDate.getTimeStamp() + "a",
					MyGetDate.getTimeStamp() + "b", MyGetDate.getTimeStamp() + "c" };
			int[] copies = { 1, 2 };

			for (int i = 0; i < 2; i++) {
				InvoicePDF pdf = new InvoicePDF();
				isCreated = pdf.createInvoicePDF(clientInfo, invoiceNumber,
						timeStamps[i], datePdf, payment, dftm,
						MainPanel.INVOICE_PDF_PATH + "\\Фактура-", "ФАКТУРА",
						ORIGINAL[i], 0, dftm.getRowCount(),
						MainPanel.personName);

				// update invoice number
				if (isCreated) {

					// OpenPDFDocument.pdfRunner(MainPanel.INVOICE_PDF_PATH +
					// "\\Фактура-"
					// + timeStamps[0] + "-" + invoiceNumber + ".pdf");

					PrintWithoutOpenPdf.printWithoutDialog(
							MainPanel.INVOICE_PDF_PATH, "\\Фактура-"
									+ timeStamps[i] + "-" + invoiceNumber
									+ ".pdf", ps, copies[i]);

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
					} else {
						// jd.dispose(); //?????????????

					}
				}
			});
		}

		return null;
	}

}
