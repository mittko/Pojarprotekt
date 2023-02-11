package office.ServiceOrderWorkers;

import JPrinter.Print.PrintBarcod;
import generators.GenerateBarcod;
import utils.MainPanel;

import javax.swing.*;

public class PrintPDFBarcodesWorker extends SwingWorker {

	private String barcode;
	private String clientName;
	public PrintPDFBarcodesWorker(String barcode, String clientName) {
		this.barcode = barcode;
		this.clientName = clientName;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		if(GenerateBarcod.generateBarcodAsPDF(barcode, clientName,barcode + "-етикет.pdf") ) {

		PrintBarcod.printPDF(MainPanel.BARCODE_PDF_PATH,barcode + "-етикет.pdf");
			//		runPDF.pdfRunner("C:/Program1/BarcodeImage/" + number
			//			+ ".pdf");
		}

		return null;
	}

}
