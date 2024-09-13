package reports.workers;

import http.RequestCallback2;
import http.client.GetClientService;
import models.Firm;
import pdf.brack.BrackPDF;
import pdf.OpenPDFDocument;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintProtokolBrackWorker {

	private JDialog jd = null;
	private String BRACK_NUMBER = null;
	private HashMap<String,ArrayList<Object>> allReasons = null;
	private DefaultTableModel dftm = null;
	private final int startIndex;
	private final int endIndex;
	private final String brackDate;
	public PrintProtokolBrackWorker(JDialog jd,String brackNumber,HashMap<String,ArrayList<Object>> allReasons,
			DefaultTableModel dftm,int startIndex,int endIndex, String brackDate) {
		this.jd = jd;
		this.BRACK_NUMBER = brackNumber;
		this.allReasons = allReasons;
		this.dftm = dftm;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.brackDate = brackDate;
	}

	public Object print() {
		// TODO Auto-generated method stub

		GetClientService service = new GetClientService();
		service.getFirm(dftm.getValueAt(startIndex, 0).toString(), new RequestCallback2() {
			@Override
			public <T> void callback(T t) {

				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				Firm firm = (Firm)t;
				if(firm == null) {
					return;
				}
				String timeStamp = MyGetDate.getTimeStamp();
				String[] helpers = {"a","b"};
				int[] copies = {1};

				String[] clientsData = new String[5];

				clientsData[0] = firm.getFirm() != null ? firm.getFirm() : ""; // firm
				clientsData[1] = firm.getTelPerson() != null ? firm.getTelPerson() : ""; // tel
				clientsData[2] = firm.getCity() != null ? firm.getCity() : "";// city
				clientsData[3] = firm.getAddress() != null ? firm.getAddress() : ""; // address
				clientsData[4] = firm.getMol() != null ? firm.getMol() : "";// ÌÎË

				for(int printing = 0;printing < 1;printing++) {
					BrackPDF b = new BrackPDF();

					b.createBrak(dftm, clientsData, allReasons,
							timeStamp +helpers[printing],BRACK_NUMBER,startIndex,endIndex, brackDate); // then create new

					OpenPDFDocument.pdfRunner(MainPanel.BRACK_PDF_PATH+"\\Áðàê-"+(timeStamp +helpers[printing])+"-"+BRACK_NUMBER+".pdf");

	        	/* PrintWithoutOpenPdf.printWithoutDialog(MainPanel.BRACK_PDF_PATH,
	        					 "\\Áðàê-" + (timeStamp+helpers[printing]) + "-"+BRACK_NUMBER+".pdf",
	        					 ps, copies[printing]);*/
				}
			}
		});

	
		return null;
	}

}
