package NewExtinguisher.SwingWorkers;

import JPrinter.PrintSticker.PrintSticker;
import PDF.OpenPDFDocument;
import ThermalPrinters.CitizenPrinters.CitizenPrinterManager;
import generators.GenerateBarcod;
import utils.MainPanel;

import javax.swing.*;

public class StickerPrinterWorker extends SwingWorker {

	private String barcod = null;
	private final String nextDateTO;
	private final String nextDateP;
	private String nextDateHI = null;
	private final CitizenPrinterManager citizenPrinterManager =
			new CitizenPrinterManager();
	private final int numberOfPrints;
	public StickerPrinterWorker(String barcod,String nextDateTO, String nextDateP,String nextDateHI, int numberOfPrints) {
		this.barcod = barcod;
		this.nextDateTO = nextDateTO;
		this.nextDateP = nextDateP;
		this.nextDateHI = nextDateHI;
		this.numberOfPrints = numberOfPrints;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		// Едната дата не я принтира засега остава закоментирано

		System.out.println("("+ nextDateTO + ") (" + nextDateP + ") (" + nextDateHI+")");

			citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0,barcod.length()-1)
					, nextDateTO,nextDateP,nextDateHI, MainPanel.personName);


//	 	boolean pdf = GenerateBarcod.generateBarcodOnStickerAsPDF(barcod, barcod +
//				MainPanel.STICK_TO_BARCODE + "-стикер.pdf",nextDateTO);
//		 if(pdf) {
//		 	for(int i = 0;i < numberOfPrints;i++) {
//
//				OpenPDFDocument.pdfRunner(MainPanel.BARCODE_PDF_PATH + barcod +
//						MainPanel.STICK_TO_BARCODE + "-стикер.pdf");
////				PrintSticker.printPDF(MainPanel.BARCODE_PDF_PATH, barcod +
////						MainPanel.STICK_TO_BARCODE + "-стикер.pdf");
//			}
//		 }
	   	return null;
	}

}
