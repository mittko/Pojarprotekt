package WorkingBookWorkers;

import JPrinter.Print.PrintWithoutOpenPdf;
import pdf.Brack.BrackPDF;
import WorkingBook.Brack;
import WorkingBook.WorkingBook;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.ChoisePrinterDialog;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrintProtokolBrackWorker extends SwingWorker {
	private boolean pdf = false;
	private JDialog jd = null;
	private String BRACK_NUMBER = null;
	private int startIndex;
	private int endIndex;
	private String timeStamp;
	private PrintService ps;

	public PrintProtokolBrackWorker(JDialog jd, String brackNumber,
			int startIndex, int endIndex) {
		this.jd = jd;
		this.BRACK_NUMBER = brackNumber;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub

		ArrayList<String> clientDetails = null;
		try {

			ps = ChoisePrinterDialog.showPrinters();
			if (ps != null) {

				clientDetails = ClientTable.getClientDetails(Brack.dtm_Scrab
						.getValueAt(0, 0) + "");// client

				String[] clientsData = new String[5];
				if (clientDetails == null) {
					return null;
				} else if (clientDetails.size() == 0) { // client can be deleted
														// !!!!
					clientsData[0] = Brack.dtm_Scrab.getValueAt(0, 0) + ""; // client
					clientsData[1] = "";
					clientsData[2] = ""; // tel of client
					clientsData[3] = ""; // tel of client
					clientsData[4] = "";// няма МОЛ
				} else if (clientDetails.size() == 4) {
					clientsData[0] = clientDetails.get(0); // client
					clientsData[1] = clientDetails.get(1); // tel of client
					clientsData[2] = "";
					clientsData[3] = "";
					clientsData[4] = Brack.dtm_Scrab.getValueAt(0, 0) + "";// няма
																			// МОЛ
																			// затова
																			// слагаме
																			// името
																			// на
																			// лицето
				} else {
					clientsData[0] = clientDetails.get(0); // firm
					clientsData[1] = clientDetails.get(7); // tel
					clientsData[2] = clientDetails.get(1);// city
					clientsData[3] = clientDetails.get(2); // address
					clientsData[4] = clientDetails.get(4);// МОЛ
				}

				/*
				 * for(int i = 0;i < clientsData.length;i++) {
				 * System.out.printf("(%s)\n",clientsData[i]); }
				 */

				// старото - String[] clientsData = new
				// String[]{Brack.dtm_Scrab.getValueAt(0, 0)+""};
				// System.out.printf("%s\n",clientsData[0]);

				timeStamp = MyGetDate.getTimeStamp();
				String[] helpers = { "a", "b" };
				int[] copies = { 2 };
				for (int printing = 0; printing < 1; printing++) {
					BrackPDF b = new BrackPDF();

					pdf = b.createBrak(Brack.dtm_Scrab, clientsData,
							WorkingBook.reasons_map, timeStamp + helpers[printing],
							BRACK_NUMBER, startIndex, endIndex,
							MyGetDate.getReversedSystemDate()); // then create new

					// runPDF.pdfRunner(MainPanel.BRACK_PDF_PATH+
					// "\\Брак-"+(timeStamp+helpers[printing])+"-" +
					// BRACK_NUMBER+".pdf");

					PrintWithoutOpenPdf.printWithoutDialog(
							MainPanel.BRACK_PDF_PATH, "\\Брак-"
									+ (timeStamp + helpers[printing]) + "-"
									+ BRACK_NUMBER + ".pdf", ps,
							copies[printing]);
				}
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (ps != null) {

						if (pdf) {
							Brack.dtm_Scrab.setRowCount(0);
							if (Brack.reasonsList.getModel().getSize() > 0) {
								DefaultListModel listModel = (DefaultListModel) Brack.reasonsList
										.getModel();
								listModel.removeAllElements();
							}
							Brack.printServiceButton.setEnabled(false);
						} else {
							JOptionPane.showMessageDialog(null,
									"Грешка при създаването на документа");
						}
					}
				}

			});
		}
		return null;// BRACK_NUMBER;
	}

	// get list of reasons for write to protokol brack
	/*
	 * private HashSet<Object> allReasons() { HashSet<Object> all = new
	 * HashSet<Object>(); for(int row = 0;row <
	 * Brack.dtm_Scrab.getRowCount();row++) { ArrayList<Object> value =
	 * Worker.reasons_map.get(getKey(row)); all.addAll(value); } return all; }
	 */
	public String getKey(int row) {
		StringBuilder sb = new StringBuilder();
		sb.append(Brack.dtm_Scrab.getValueAt(row, 3));// barcod
		return sb.toString();

	}
}
