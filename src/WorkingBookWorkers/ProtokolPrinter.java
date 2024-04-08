package WorkingBookWorkers;

import JPrinter.Print.PrintWithoutOpenPdf;
import pdf.protokol.ProtokolPDF3;
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
			TreeMap<Object, Integer> partsMap, PrintService ps)
			throws Exception {
		// TODO Auto-generated method stub

		ArrayList<String> clientDetails = null;

		clientDetails = ClientTable.getClientDetails(dtm.getValueAt(startIndex,
				0) + "");
		/*
		 * for(int i = 0;i < clientDetails.size();i++) {
		 * System.out.println(clientDetails.get(i)+" xxx"); }
		 */
		String[] clientsData = new String[5];// {dtm.getValueAt(startIndex,
												// 0).toString()};

		if (clientDetails == null) {

			return false;
		} else if (clientDetails.size() == 0) { // client can be deleted !!!!
			clientsData[0] = dtm.getValueAt(startIndex, 0) + ""; // client
			clientsData[1] = "";
			clientsData[2] = ""; // tel of client
			clientsData[3] = ""; // tel of client
			clientsData[4] = "";// няма МОЛ
		} else if (clientDetails.size() == 4) {
			clientsData[0] = clientDetails.get(0); // client
			clientsData[1] = clientDetails.get(1); // tel of client
			clientsData[2] = "";
			clientsData[3] = "";
			clientsData[4] = dtm.getValueAt(startIndex, 0) + "";// няма МОЛ
																// затова
																// слагаме името
																// на лицето
		} else {
			clientsData[0] = clientDetails.get(0); // firm
			clientsData[1] = clientDetails.get(7); // tel
			clientsData[2] = clientDetails.get(1);// city
			clientsData[3] = clientDetails.get(2); // address
			clientsData[4] = clientDetails.get(4);// МОЛ
		}

		if (PROTOKOL_NUMBER == null) {
			return false;
		}
		// Protokol2815 p = new Protokol2815();
		String timeStamp = MyGetDate.getTimeStamp();
		String[] helpers = { "a", "b" };
		int[] copies = { 2 };
		// create pdf

		for (int printing = 0; printing < 1; printing++) {
			ProtokolPDF3 protokolPDF3 = new ProtokolPDF3();
			boolean pdf = protokolPDF3.processPdf(dtm, partsMap, clientsData,
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
		}

		return true;
	}

}
