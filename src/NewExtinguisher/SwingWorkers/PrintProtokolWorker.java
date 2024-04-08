package NewExtinguisher.SwingWorkers;

import JPrinter.Print.PrintWithoutOpenPdf;
import pdf.protokol.NewExtinguisherProtokolPDF;
import db.Client.ClientTable;
import db.Protokol.ProtokolNumber;
import mydate.MyGetDate;
import utils.ChoisePrinterDialog;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class PrintProtokolWorker extends SwingWorker {

	private TreeMap<Object, Integer> partsMap = null;
	private JDialog jd = null;
	private String CURRENT_CLIENT = null;
	private String protokolNumber = null;
	private String timeStamp;
	private DefaultTableModel dftm;
	private PrintService ps;

	public PrintProtokolWorker(DefaultTableModel dftm, String client,
			TreeMap<Object, Integer> partsMap, JDialog jd) {
		this.dftm = dftm;
		this.CURRENT_CLIENT = client;
		this.partsMap = partsMap;
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			ArrayList<String> clientDetails = null;
			ps = ChoisePrinterDialog.showPrinters();
			if (ps != null) {

				clientDetails = ClientTable.getClientDetails(CURRENT_CLIENT);

				String[] clientsData = new String[5];

				if (clientDetails == null) {
					return null;
				} else if (clientDetails.size() == 0) {// client can be deleted
														// !!!

				} else if (clientDetails.size() == 4) {
					clientsData[0] = clientDetails.get(0);// client
					clientsData[1] = clientDetails.get(1);// tel
					clientsData[2] = "";
					clientsData[3] = "";
					clientsData[4] = "                                                            ";// םÿלא
																									// ÌÎË
				} else {
					clientsData[0] = clientDetails.get(0); // firm
					clientsData[1] = clientDetails.get(7); // tel of firm
					clientsData[2] = clientDetails.get(1); // city
					clientsData[3] = clientDetails.get(2);// address
					clientsData[4] = clientDetails.get(4);// ÌÎË
				}

				protokolNumber = ProtokolNumber.getProtokolNumber();
				if (protokolNumber == null) {
					return null;
				}

				timeStamp = MyGetDate.getTimeStamp();
				String[] helpers = { "a", "b" };
				int[] copies = { 2 };
				for (int printing = 0; printing < 1; printing++) {
					NewExtinguisherProtokolPDF p = new NewExtinguisherProtokolPDF();
					// create pdf

					boolean pdf = p.processPdf2(dftm, partsMap, clientsData,
							protokolNumber, timeStamp + helpers[printing], 0,
							dftm.getRowCount());
					if (pdf) {

						// run pdf
						/*
						 * runPDF.pdfRunner(MainPanel.PROTOKOL_PDF_PATH+
						 * "\\Protokol2815-"+(timeStamp+helpers[printing]) + "-"
						 * + protokolNumber +".pdf");
						 */
						PrintWithoutOpenPdf.printWithoutDialog(
								MainPanel.PROTOKOL_PDF_PATH, "\\Protokol2815-"
										+ (timeStamp + helpers[printing]) + "-"
										+ protokolNumber + ".pdf", ps,
								copies[printing]);
					}
				}
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (ps != null) {
						// NewExtinguisher2.dftm.setRowCount(0);
						// NewExtinguisher2.updateQuantityOfExtinguisherModel.setRowCount(0);
						// NewExtinguisher2.printerButton.setEnabled(false);
					}
				}

			});
		}

		return null;
	}
}
