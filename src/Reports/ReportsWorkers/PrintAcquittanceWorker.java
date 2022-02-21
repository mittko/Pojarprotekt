package Reports.ReportsWorkers;

import PDF.Aqcuittance.AcquittancePDFromReports;
import PDF.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utility.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PrintAcquittanceWorker extends SwingWorker {
	private ArrayList<String> clientInfo;
	private DefaultTableModel dftm;
	private String currentClient;
	private String acquittanceNumber;
	// private String date;
	private int startIndex;
	private int endIndex;
	private JDialog jd;

	public PrintAcquittanceWorker(DefaultTableModel dftm, String currentClient,
			String acquittanceNumber, String date, int startIndex,
			int endIndex, JDialog jd) {
		this.dftm = dftm;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.currentClient = dftm.getValueAt(this.startIndex, 2).toString();
		this.acquittanceNumber = dftm.getValueAt(this.startIndex, 0).toString();

		// this.date =dftm.getValueAt(0, 3).toString();

		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {
		//	PrintService ps = ChoisePrinterDialog.showPrinters();
		//	if (ps != null) {

				// get client info
				clientInfo = ClientTable.getClientDetails(currentClient);

				String[] timeStamps = { MyGetDate.getTimeStamp() + "a",
						MyGetDate.getTimeStamp() + "b" };
				int[] copies = { 1 };// {2};
				for (int i = 0; i < 1; i++) {
					AcquittancePDFromReports pdf = new AcquittancePDFromReports();
					pdf.createAcquittancePDF2(clientInfo, dftm, timeStamps[i],
							startIndex, endIndex);

					 OpenPDFDocument.pdfRunner(MainPanel.ACQUITTANCE_PDF_PATH
					 + "\\Стокова Разписка-" + timeStamps[i] + "-"
					 + acquittanceNumber + ".pdf ");

				/*	PrintWithoutOpenPdf
							.printWithoutDialog(MainPanel.ACQUITTANCE_PDF_PATH,
									"\\Стокова Разписка" + "-" + timeStamps[i]
											+ "-" + acquittanceNumber + ".pdf",
									ps, copies[i]);*/
				}
		//	}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

}
