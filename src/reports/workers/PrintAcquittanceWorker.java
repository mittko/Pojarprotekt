package reports.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import models.Firm;
import pdf.aqcuittance.AcquittancePDFromReports;
import pdf.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PrintAcquittanceWorker extends SwingWorker {
	private final DefaultTableModel dftm;
	private final String currentClient;
	private final String acquittanceNumber;
	private final int startIndex;
	private final int endIndex;
	private final JDialog jd;

	public PrintAcquittanceWorker(DefaultTableModel dftm,
								  int startIndex,
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
			GetClientService service = new GetClientService();
			service.getFirm(currentClient, new RequestCallback2() {
				@Override
				public <T> void callback(T t) {

					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					Firm firm = (Firm) t;
					if(firm != null) {
						String[] timeStamps = { MyGetDate.getTimeStamp() + "a",
								MyGetDate.getTimeStamp() + "b" };
						int[] copies = { 1 };// {2};
						for (int i = 0; i < 1; i++) {
							AcquittancePDFromReports pdf = new AcquittancePDFromReports();
							pdf.createAcquittancePDF2(firm, dftm, timeStamps[i],
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
					} else {

					}
				}
			});


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
