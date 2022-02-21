package ServiceOrder.ServiceOrderWorkers;

import JPrinter.Print.PrintWithoutOpenPdf;
import PDF.SO.ServiceOrderPDF;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utility.ChoisePrinterDialog;
import utility.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class PrinterServiceOrderWorker extends SwingWorker {

	private String serviceNumber = null;
	private String currentClient = null;
	private JDialog jd = null;
	private TreeMap<Object, Integer> soMap = null;
	private String timeStamp;

	public PrinterServiceOrderWorker(String serviceNumber,
			String currentClient, JDialog jd, TreeMap<Object, Integer> soMap) {
		this.serviceNumber = serviceNumber;
		this.currentClient = currentClient;
		this.jd = jd;
		this.soMap = soMap;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		// do request to db
		try {
			PrintService ps = ChoisePrinterDialog.showPrinters();

			if (ps != null) {

				ArrayList<String> clientDetails = ClientTable
						.getClientDetails(currentClient);

				String[] clientsData = new String[11];
				if (clientDetails == null) {
					return null;
				} else if (clientDetails.size() == 0) {
					JOptionPane.showMessageDialog(null,
							"Не е намерена информация за този клиент!");
					return null;
				} else if (clientDetails.size() == 4) {
					clientsData[0] = clientDetails.get(0); // firm
					clientsData[1] = "";// city
					clientsData[2] = ""; // address
					clientsData[3] = ""; // eik
					clientsData[4] = ""; // mol
					clientsData[5] = "";// e-mail
					clientsData[6] = ""; // perosn
					clientsData[7] = clientDetails.get(1); // tel of person
					clientsData[8] = "";// bank
					clientsData[9] = ""; // bic
					clientsData[10] = ""; // iban

				} else {
					clientsData[0] = clientDetails.get(0); // firm
					clientsData[1] = clientDetails.get(1);// city
					clientsData[2] = clientDetails.get(2); // address
					clientsData[3] = clientDetails.get(3); // eik
					clientsData[4] = clientDetails.get(4); // mol
					clientsData[5] = clientDetails.get(5);// e-mail
					clientsData[6] = clientDetails.get(6); // perosn
					clientsData[7] = clientDetails.get(7); // tel of person
					clientsData[8] = clientDetails.get(8);// bank
					clientsData[9] = clientDetails.get(9); // bic
					clientsData[10] = clientDetails.get(10); // iban

				}
				// String sNumber = so_Table.getSO_Number();

				// PDF_SO pso = new PDF_SO();

				timeStamp = MyGetDate.getTimeStamp();
				String[] helpers = { "a", "b" };
				int[] copies = { 2 };
				ServiceOrderPDF so = new ServiceOrderPDF();
				for (int printing = 0; printing < 1; printing++) {

					boolean pdf = so.processPDF(timeStamp + helpers[printing],
							serviceNumber, clientsData[0], // firm
							clientsData[1], // city
							clientsData[2], // address
							clientsData[3], // eik
							clientsData[3], // dds
							clientsData[4], // mol
							clientsData[8], // bank
							clientsData[9], // bic
							clientsData[10], // iban
							clientsData[7], // tel
							soMap);

					if (pdf) {

						/*
						 * runPDF.pdfRunner(MainPanel.SERVICE_ORDER_PDF_PATH +
						 * "\\СервизнаПоръчка-" + (timeStamp +
						 * helpers[printing])+ "-" + serviceNumber + ".pdf");
						 */

						// PrintWithoutOpenPdf.print(MainPanel.SERVICE_ORDER_PDF_PATH
						// , "\\СервизнаПоръчка-"
						// + (timeStamp+helpers[printing]) + "-" + serviceNumber
						// + ".pdf");

						PrintWithoutOpenPdf.printWithoutDialog(
								MainPanel.SERVICE_ORDER_PDF_PATH,
								"\\СервизнаПоръчка-"
										+ (timeStamp + helpers[printing]) + "-"
										+ serviceNumber + ".pdf", ps,
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
				}

			});
		}
		return null;
	}
}
