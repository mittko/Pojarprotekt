package invoice.workers;

import javaprinters.print.PrintWithoutOpenPdf;
import pdf.invoice.InvoicePDF;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PrintProformPdfWorker extends SwingWorker {

	private final String proformNumber;
	private final String currentClient;
	private final String datePdf;
	private final String payment;
	boolean isCreated = false;

	private final DefaultTableModel dftm;
	private final PrintService ps;

	public PrintProformPdfWorker(DefaultTableModel dftm, String currentClient,
			String proformNumber, String datePdf, double danOsnova,
			String payment,PrintService ps, JDialog jd) {
		this.dftm = dftm;
		this.currentClient = currentClient;
		this.proformNumber = proformNumber;
		this.datePdf = datePdf;
		this.payment = payment;
		this.ps = ps;
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
			// System.out.printf("current client -> %s ",currentClient);
			String[] ORIGINAL2 = { "��������", "" };
			String timeStamps2[] = { MyGetDate.getTimeStamp() + "d",
					MyGetDate.getTimeStamp() + "e", };
			int[] copies = { 1, 1 };
			for (int i = 0; i < 2; i++) {
				InvoicePDF pdf = new InvoicePDF();
				isCreated = pdf.createInvoicePDF(clientInfo, proformNumber,
						timeStamps2[i], datePdf, payment, dftm,
						MainPanel.PROFORM_PDF_PATH + "\\��������-", "��������",
						ORIGINAL2[i], 0, dftm.getRowCount(),
						MainPanel.personName);

				// update invoice number
				if (isCreated) {

					/*
					 * runPDF.pdfRunner(MainPanel.PROFORM_PDF_PATH +
					 * "\\��������-" + timeStamp + "-" + proformNumber+".pdf");
					 */
					PrintWithoutOpenPdf.printWithoutDialog(
							MainPanel.PROFORM_PDF_PATH, "\\��������-"
									+ timeStamps2[i] + "-" + proformNumber
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
								"������ ��� ����������� �� ���������!");
					} else {
						// jd.dispose();
					}
				}
			});
		}
		return null;
	}

}
