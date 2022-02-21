package ServiceOrder.ServiceOrderWorkers;

import JPrinter.Print.PrintBarcod;
import generators.GenerateBarcod;
import utility.MainPanel;

import javax.swing.*;

public class PrintPDFBarcodesWorker extends SwingWorker {

	private String barcode;
	
	public PrintPDFBarcodesWorker(String barcode) {
		this.barcode = barcode;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		if(GenerateBarcod.generateBarcodAsPDF(barcode, barcode + "-етикет.pdf") ) {

		PrintBarcod.printPDF(MainPanel.BARCODE_PDF_PATH,barcode + "-етикет.pdf");
			//		runPDF.pdfRunner("C:/Program1/BarcodeImage/" + number
			//			+ ".pdf");
		}

		return null;
	}

}
