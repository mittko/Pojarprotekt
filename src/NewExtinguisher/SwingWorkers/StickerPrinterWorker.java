package NewExtinguisher.SwingWorkers;

import Exceptions.ErrorDialog;
import JPrinter.PrintSticker.PrintSticker;
import PDF.OpenPDFDocument;
import ThermalPrinters.CitizenPrinters.CitizenPrinterManager;
import generators.GenerateBarcod;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

public class StickerPrinterWorker extends SwingWorker {

	private static HashSet<String> enteredNumbers = new HashSet<>();
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

		if(!enteredNumbers.contains(barcod)) {


			if(!nextDateTO.isEmpty()) {
				citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0, barcod.length() - 1)
						, nextDateTO, "",
						"", MainPanel.personName,"1");

				System.out.println("TO");
				Thread.sleep(5000L);


			}

			if(!nextDateP.isEmpty()) {

				citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0, barcod.length() - 1)
						, "", nextDateP,
						"", MainPanel.personName,"2");
				System.out.println("P");
				Thread.sleep(5000L);

			}

			if(!nextDateHI.isEmpty()) {
				System.out.println("HI");
				citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0,barcod.length()-1)
						, "","",
						nextDateHI, MainPanel.personName,"3");
			}
			//	enteredNumbers.add(barcod);
		} else {
			ErrorDialog.showErrorMessage("Този номер вече е въведен !");
		}
//			if(!enteredNumbers.contains(barcod)) {
//
//		     boolean pdf = GenerateBarcod.generateBarcodOnStickerAsPDF(barcod, barcod +
//				MainPanel.STICK_TO_BARCODE + "-стикер.pdf", nextDateTO);
//
//			if (pdf) {
//				for (int i = 0; i < numberOfPrints; i++) {
//
//					OpenPDFDocument.pdfRunner(MainPanel.BARCODE_PDF_PATH + barcod +
//							MainPanel.STICK_TO_BARCODE + "-стикер.pdf");
//
//					PrintSticker.printPDF(MainPanel.BARCODE_PDF_PATH, barcod +
//							MainPanel.STICK_TO_BARCODE + "-стикер.pdf");
//				}
//			}
//		     enteredNumbers.add(barcod);
//		} else {
//			ErrorDialog.showErrorMessage("Този номер вече е въведен !");
//		}
		return null;
	}

	public static void clearEnteredNumbers() {
		if(enteredNumbers != null) {
			enteredNumbers.clear();
		}
	}
}
