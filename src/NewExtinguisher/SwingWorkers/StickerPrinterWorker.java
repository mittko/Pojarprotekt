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
	private String nextDateTO;
	private String nextDateP;
	private String nextDateHI = null;
	private boolean isTo;
	private boolean isP;
	private boolean isHi;
	private final CitizenPrinterManager citizenPrinterManager =
			new CitizenPrinterManager();


	public StickerPrinterWorker(String barcod,String nextDateTO, String nextDateP,String nextDateHI,
								boolean isTo, boolean isP,boolean isHi) {
		this.barcod = barcod;
		this.nextDateTO = nextDateTO;
		this.nextDateP = nextDateP;
		this.nextDateHI = nextDateHI;
		this.isTo = isTo;
		this.isP = isP;
		this.isHi = isHi;
	}


	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		if(!enteredNumbers.contains(barcod)) {


			if(isTo/*!nextDateTO.isEmpty()*/) {
				citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0, barcod.length() - 1)
						, nextDateTO, "",
						"", MainPanel.personName,"1");
				Thread.sleep(5000L);
			}

			if(isP/*!nextDateP.isEmpty()*/) {

				citizenPrinterManager.printBarcodeAndCharacterPrinting(barcod.substring(0, barcod.length() - 1)
						, "", nextDateP,
						"", MainPanel.personName,"2");
				Thread.sleep(5000L);

			}

			if(isHi/*!nextDateHI.isEmpty()*/) {
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
