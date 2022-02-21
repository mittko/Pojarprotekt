package NewExtinguisher.SwingWorkers;

import ThermalPrinters.CitizenPrinters.CitizenPrinterManager;
import utility.MainPanel;

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
		for(int i = 0;i < numberOfPrints;i++) {
			citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0,barcod.length()-1),
					nextDateTO,nextDateP,nextDateHI, MainPanel.personName);
		}

//	 	boolean pdf = GenerateBarcod.generateBarcodOnStickerAsPDF(barcod, barcod + doing + "-стикер.pdf",nextDate);
//		 if(pdf) {
//		 	for(int i = 0;i < numberOfPrints;i++) {
//				PrintSticker.printPDF(MainPanel.BARCODE_PDF_PATH, barcod + doing + "-стикер.pdf");
//			}
//		 }
	   	return null;
	}

}
