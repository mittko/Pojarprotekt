package reports.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import models.Firm;
import pdf.OpenPDFDocument;
import pdf.serviceorder.ServiceOrderPDF;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

public class PrintSOWorker {

	private String serviceNumber = null;
	private String currentClient = null;
	private JDialog jd = null;
	private TreeMap<Object, Integer> soMap = null;

	public PrintSOWorker(String serviceNumber, String currentClient,
						 JDialog jd, TreeMap<Object, Integer> soMap) {
		this.serviceNumber = serviceNumber;
		this.currentClient = currentClient;
		this.jd = jd;
		this.soMap = soMap;
	}


	public Object print()  {
		// TODO Auto-generated method stub

		// do request to db
//		try {
			//	PrintService ps = ChoisePrinterDialog.showPrinters();
			//		if (ps != null) {

			GetClientService service = new GetClientService();
			service.getFirm(currentClient, new RequestCallback2() {
				@Override
				public <T> void callback(T t) {

					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					Firm firm = (Firm) t;
					if(firm != null) {

						String timeStamp = MyGetDate.getTimeStamp();

						ServiceOrderPDF so = new ServiceOrderPDF();
						String[] helpers = { "a", "b" };
						int[] copies = { 1 };
						for (int printing = 0; printing < 1; printing++) {
							boolean pdf = so.processPDF(timeStamp + helpers[printing],
									serviceNumber, firm.getFirm(), // firm
									firm.getCity(), // city
									firm.getAddress(), // address
									firm.getEik(), // eik
									firm.getEik(), // dds
									firm.getMol(), // mol
									firm.getBank(), // bank
									firm.getBic(), // bic
									firm.getIban(), // iban
									firm.getTelPerson(), // tel
									soMap);

							if (pdf) {
								OpenPDFDocument.pdfRunner(MainPanel.SERVICE_ORDER_PDF_PATH +
										"\\СервизнаПоръчка-"
										+ (timeStamp +helpers[printing]) + "-" + serviceNumber
										+ ".pdf");

                    /*	PrintWithoutOpenPdf.printWithoutDialog(
								MainPanel.SERVICE_ORDER_PDF_PATH,
								"\\СервизнаПоръчка-"
										+ (timeStamp + helpers[printing]) + "-"
										+ serviceNumber + ".pdf", ps,
								copies[printing]);*/
							}
						}
					}
				}
			});


//		} finally {
//			SwingUtilities.invokeLater(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//				}
//
//			});
//		}
		return null;
	}
}
