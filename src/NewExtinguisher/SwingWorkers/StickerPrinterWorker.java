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
		// Едната дата не я принтира засега остава закоментирано

		System.out.println(""+ nextDateTO + ") (" + nextDateP + ") (" + nextDateHI+")");

		ArrayList<String> dates = new ArrayList<>();



		if(!enteredNumbers.contains(barcod)) {

						citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0, barcod.length() - 1)
								, nextDateTO, nextDateP,
								nextDateHI, MainPanel.personName);

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
