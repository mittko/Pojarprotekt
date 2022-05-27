package Reports.ReportsWorkers;

import PDF.Brack.BrackPDF;
import PDF.OpenPDFDocument;
import db.Client.ClientTable;
import mydate.MyGetDate;
import utility.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintProtokolBrackWorker extends SwingWorker {

	private JDialog jd = null;
	private String BRACK_NUMBER = null;
	private HashMap<String,ArrayList<Object>> allReasons = null;
	private DefaultTableModel dftm = null;
	private int startIndex;
	private int endIndex;
	private String timeStamp;
	private String brackDate;
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
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
	//	ArrayList<String> clientDetails = null;
		try {
			//PrintService ps = ChoisePrinterDialog.showPrinters();
		// if(ps != null) {

		ArrayList<String> clientDetails =  ClientTable.getClientDetails(dftm.getValueAt(startIndex, 0).toString());
        String[] clientsData = new String[5];

			if (clientDetails == null) {
				return false;
			} else if (clientDetails.size() == 0) { // client can be deleted !!!!
				clientsData[0] = dftm.getValueAt(startIndex, 0) + ""; // client
				clientsData[1] = "";
				clientsData[2] = ""; // tel of client
				clientsData[3] = ""; // tel of client
				clientsData[4] = "";// няма МОЛ
			} else if (clientDetails.size() == 4) {
				clientsData[0] = clientDetails.get(0); // client
				clientsData[1] = clientDetails.get(1); // tel of client
				clientsData[2] = "";
				clientsData[3] = "";
				clientsData[4] = dftm.getValueAt(startIndex, 0) + "";// няма МОЛ
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

	           timeStamp = MyGetDate.getTimeStamp();
	           String[] helpers = {"a","b"};
	           int[] copies = {1};
	           for(int printing = 0;printing < 1;printing++) {
	    	   BrackPDF b = new BrackPDF();
	    	  
	            b.createBrak(dftm, clientsData, allReasons,
	            		timeStamp+helpers[printing],BRACK_NUMBER,startIndex,endIndex, brackDate); // then create new

				   OpenPDFDocument.pdfRunner(MainPanel.BRACK_PDF_PATH+"\\Брак-"+(timeStamp+helpers[printing])+"-"+BRACK_NUMBER+".pdf");
	  
	        	/* PrintWithoutOpenPdf.printWithoutDialog(MainPanel.BRACK_PDF_PATH,
	        					 "\\Брак-" + (timeStamp+helpers[printing]) + "-"+BRACK_NUMBER+".pdf",
	        					 ps, copies[printing]);*/
	           }

	   //      }
	            
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
