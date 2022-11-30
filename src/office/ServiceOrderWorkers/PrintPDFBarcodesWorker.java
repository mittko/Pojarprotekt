package office.ServiceOrderWorkers;

import JPrinter.Print.PrintBarcod;
import PDF.OpenPDFDocument;
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
		
		if(GenerateBarcod.generateBarcodAsPDF(barcode, barcode + "-������.pdf") ) {

	//	PrintBarcod.printPDF(MainPanel.BARCODE_PDF_PATH,barcode + "-������.pdf");
					OpenPDFDocument.pdfRunner(MainPanel.BARCODE_PDF_PATH+"\\"+barcode + "-������.pdf");
		}

		return null;
	}

}
