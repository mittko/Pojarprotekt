package newextinguisher.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import javaprinters.print.PrintWithoutOpenPdf;
import models.Firm;
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

			ps = ChoisePrinterDialog.showPrinters();
			if (ps != null) {


				protokolNumber = "-1";//ProtokolNumber.getProtokolNumber();
				if (protokolNumber == null) {
					return null;
				}

				GetClientService service = new GetClientService();
				service.getFirm(CURRENT_CLIENT, new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						Firm firm = (Firm) t;
						if(firm != null) {
							timeStamp = MyGetDate.getTimeStamp();
							String[] helpers = { "a", "b" };
							int[] copies = { 2 };
							for (int printing = 0; printing < 1; printing++) {
								NewExtinguisherProtokolPDF p = new NewExtinguisherProtokolPDF();
								// create pdf

								boolean pdf = p.processPdf(dftm, partsMap, firm,
										protokolNumber, timeStamp + helpers[printing], 0,
										dftm.getRowCount(),MyGetDate.getReversedSystemDate());

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
					}
				});

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
