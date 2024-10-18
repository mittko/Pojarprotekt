package office.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import javaprinters.print.PrintWithoutOpenPdf;
import models.Firm;
import pdf.serviceorder.ServiceOrderPDF;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utils.ChoisePrinterDialog;
import utils.MainPanel;

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

			PrintService ps = ChoisePrinterDialog.showPrinters();

			if (ps != null) {

				GetClientService service = new GetClientService();
				service.getFirm(currentClient, new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						Firm firm = (Firm) t;
						if(firm != null) {

//								clientsData[0] = clientDetails.get(0); // firm
//								clientsData[1] = clientDetails.get(1);// city
//								clientsData[2] = clientDetails.get(2); // address
//								clientsData[3] = clientDetails.get(3); // eik
//								clientsData[4] = clientDetails.get(4); // mol
//								clientsData[5] = clientDetails.get(5);// e-mail
//								clientsData[6] = clientDetails.get(6); // perosn
//								clientsData[7] = clientDetails.get(7); // tel of person
//								clientsData[8] = clientDetails.get(8);// bank
//								clientsData[9] = clientDetails.get(9); // bic
//								clientsData[10] = clientDetails.get(10); // iban


							// String sNumber = so_Table.getSO_Number();

							// PDF_SO pso = new PDF_SO();

							String timeStamp = MyGetDate.getTimeStamp();
							String[] helpers = { "a", "b" };
							int[] copies = { 2 };
							ServiceOrderPDF so = new ServiceOrderPDF();
							for (int printing = 0; printing < 1; printing++) {

								boolean pdf = so.processPDF(timeStamp + helpers[printing],
										serviceNumber, firm.getFirm() != null ? firm.getFirm() : "", // firm
										firm.getCity() != null ? firm.getCity() : "", // city
										firm.getAddress() != null ? firm.getAddress() : "", // address
										firm.getEik() != null ? firm.getEik() : "", // eik
										firm.getEik() != null ? firm.getEik() : "", // dds
										firm.getMol() != null ? firm.getMol() : "", // mol
										firm.getBank() != null ? firm.getBank() : "", // bank
										firm.getBic() != null ? firm.getBic() : "", // bic
										firm.getIban() != null ? firm.getIban() : "", // iban
										firm.getTelPerson() != null ? firm.getTelPerson() : "", // tel
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
					}
				});

			}

		return null;
	}
}
