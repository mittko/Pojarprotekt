package workingbook.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import javaprinters.print.PrintWithoutOpenPdf;
import models.Firm;
import pdf.brack.BrackPDF;
import workingbook.Brack;
import workingbook.WorkingBook;
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
	private final int startIndex;
	private final int endIndex;
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

			ps = ChoisePrinterDialog.showPrinters();
			if (ps != null) {


				GetClientService service = new GetClientService();
				service.getFirm(Brack.dtm_Scrab
						.getValueAt(0, 0) + "", new RequestCallback2() {
					@Override
					public <T> void callback(T t) {

						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						Firm firm = (Firm) t;
						if(firm != null) {
							String timeStamp = MyGetDate.getTimeStamp();
							String[] helpers = { "a", "b" };
							int[] copies = { 2 };
							for (int printing = 0; printing < 1; printing++) {
								BrackPDF b = new BrackPDF();

								pdf = b.createBrak(firm, Brack.dtm_Scrab,
										WorkingBook.reasons_map, timeStamp + helpers[printing],
										BRACK_NUMBER, startIndex, endIndex,
										MyGetDate.getReversedSystemDate()); // then create new

								if (pdf) {
									// runPDF.pdfRunner(MainPanel.BRACK_PDF_PATH+
									// "\\Брак-"+(timeStamp+helpers[printing])+"-" +
									// BRACK_NUMBER+".pdf");

									PrintWithoutOpenPdf.printWithoutDialog(
											MainPanel.BRACK_PDF_PATH, "\\Брак-"
													+ (timeStamp + helpers[printing]) + "-"
													+ BRACK_NUMBER + ".pdf", ps,
											copies[printing]);

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
					}
				});

			}

		return null;// BRACK_NUMBER;
	}

	// get list of reasons for write to protokol brack

	public String getKey(int row) {
		StringBuilder sb = new StringBuilder();
		sb.append(Brack.dtm_Scrab.getValueAt(row, 3));// barcod
		return sb.toString();

	}
}
