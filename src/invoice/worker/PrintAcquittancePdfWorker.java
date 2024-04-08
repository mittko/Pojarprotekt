package invoice.worker;

import JPrinter.Print.PrintWithoutOpenPdf;
import pdf.Aqcuittance.AcquittancePDFromInvoice;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PrintAcquittancePdfWorker extends SwingWorker {

	private ArrayList<String> clientInfo;
	private String currentClient;
	private String acquittanceNumber;
	private String datePdf;
	private double danOsnova;
	private JDialog jd;
	private DefaultTableModel dftm;
	private PrintService ps;

	public PrintAcquittancePdfWorker(DefaultTableModel dftm,
			String currentClient, String acquittanceNumber, String datePdf,
			double danOsnova, PrintService ps, JDialog jd) {
		this.dftm = dftm;
		this.currentClient = currentClient;
		this.acquittanceNumber = acquittanceNumber;
		this.datePdf = datePdf;
		this.danOsnova = danOsnova;
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
			String[] timeStamp3 = { MyGetDate.getTimeStamp() + "f",
					MyGetDate.getTimeStamp() + "g" };
			int[] copies = { 1 };// {2};
			for (int i = 0; i < 1; i++) {

				AcquittancePDFromInvoice pdf = new AcquittancePDFromInvoice();
				pdf.createAcquittancePDF2(clientInfo, dftm, timeStamp3[i],
						acquittanceNumber, datePdf, 0, dftm.getRowCount(), 4); // index
																				// of
																				// final
																				// sum

				/*
				 * runPDF.pdfRunner(MainPanel.ACQUITTANCE_PDF_PATH +
				 * "\\Стокова Разписка-"+ timeStamp + "-" + acquittanceNumber +
				 * ".pdf");
				 */
				PrintWithoutOpenPdf.printWithoutDialog(
						MainPanel.ACQUITTANCE_PDF_PATH, "\\Стокова Разписка-"
								+ timeStamp3[i] + "-" + acquittanceNumber
								+ ".pdf", ps, copies[i]);

			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// jd.dispose();
				}

			});
		}
		return null;
	}

}
