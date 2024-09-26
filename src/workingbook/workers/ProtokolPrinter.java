package workingbook.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import javaprinters.print.PrintWithoutOpenPdf;
import models.Firm;
import pdf.protokol.ProtokolPDF;
import pdf.protokol.ProtokolPDFBase;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProtokolPrinter {

	private String PROTOKOL_NUMBER = null;
	private int startIndex = 0;
	private int endIndex = 0;
	private final String protokolDate;

	public ProtokolPrinter(String protokolNumber, int startIndex, int endIndex,
						   String protokolDate) {
		this.PROTOKOL_NUMBER = protokolNumber;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.protokolDate = protokolDate;
	}

	public boolean printProtokol2815(DefaultTableModel dtm,
									 TreeMap<Object, Integer> partsMap, PrintService ps, RequestCallback2 callback)
			throws Exception {
		// TODO Auto-generated method stub

		if (PROTOKOL_NUMBER == null) {
			return false;
		}

		GetClientService service = new GetClientService();
		service.getFirm(dtm.getValueAt(startIndex, 0).toString(), new RequestCallback2() {
			@Override
			public <T> void callback(T t) {
				Firm firm = (Firm)t;
				if(firm != null) {

					String timeStamp = MyGetDate.getTimeStamp();
					String[] helpers = { "a", "b" };
					int[] copies = { 2 };
					// create pdf

					for (int printing = 0; printing < 1; printing++) {
						ProtokolPDF protokolPDF3 = new ProtokolPDF();
						boolean pdf = protokolPDF3.processPdf(dtm, partsMap, firm,
								PROTOKOL_NUMBER, timeStamp + helpers[printing], startIndex,
								endIndex, protokolDate);

						if (pdf) {
							// run pdf
								//				 OpenPDFDocument.pdfRunner(MainPanel.PROTOKOL_PDF_PATH
								//				 + "\\Protokol2815-" + (timeStamp + helpers[printing])
								//				 + "-" + PROTOKOL_NUMBER + ".pdf");

							PrintWithoutOpenPdf.printWithoutDialog(
									MainPanel.PROTOKOL_PDF_PATH, "\\Protokol2815-"
											+ (timeStamp + helpers[printing]) + "-"
											+ PROTOKOL_NUMBER + ".pdf", ps,
									copies[printing]);
						}

						callback.callback(pdf);
					}
				}
			}
		});

		return true;
	}

}
