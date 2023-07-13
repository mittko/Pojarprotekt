package JPrinter.Print;

import Exceptions.InOutException;
import Log.IOErrorsWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;
import generators.GenerateBarcod;
import utils.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class PrintBarcod {

	// static final String target = "C:/Program1/BarcodeImage/";
	//
	// public static void printImage(String image) throws IOException,
	// javax.print.PrintException {
	// PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	// pras.add(new Copies(1));
	// PrintService pss[] = PrintServiceLookup.lookupPrintServices(
	// DocFlavor.INPUT_STREAM.GIF, pras);
	// if (pss.length == 0) {
	// System.out.println("printer not found");
	// throw new RuntimeException("No printer services available.");
	// }
	// PrintService ps = null;
	// for (PrintService p : pss) {
	// if (p.getName().contains("LPQ58")) {
	// ps = p;
	// break;
	// }
	// }
	// System.out.println("Printing to " + ps);
	// DocPrintJob job = ps.createPrintJob();
	// FileInputStream fin = new FileInputStream(image);
	// Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.GIF, null);
	// job.print(doc, pras);
	// fin.close();
	// }

	public static void printPDF(String target, String pdf,boolean documentRotated) {
		File f = new File(target + pdf);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
		}
		FileChannel fc = fis.getChannel();
		ByteBuffer bb = null;
		try {
			bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
		}
		PDFFile pdfFile = null;
		try {
			pdfFile = new PDFFile(bb);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
		} // Create PDF Print Page
		PDFPrintPage pages = new PDFPrintPage(pdfFile);

		// Create Print Job
		PrinterJob pjob = PrinterJob.getPrinterJob();

		PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

		Paper paper = new Paper();

		// This is to fix an error in PDF-Renderer
		// View
		// http://juixe.com/techknow/index.php/2008/01/17/print-a-pdf-document-in-java/
		// for details
		// Printing a PDF is also possible by sending the bytes directly to the
		// printer, but
		// the printer would have to support it.

		// cinfigure where to positioning image on label


		// paper.setSize() and paper.setImageableArea() are methods that
		// help to manipulate and manage label size and printing
		// typically after trial errors
		if(documentRotated) {
			paper.setSize(80,200);// this code fix problem with printing long paper gap after text !!!
			// this hardcoded values are due to trial errors
			paper.setImageableArea(0,0,90,210);//(0,0,180,70)
		} else {
			paper.setSize(180, 70);// this code fix problem with printing long paper gap after text !!!
			// this hardcoded values are due to trial errors
			paper.setImageableArea(0,0,180,70);//(0,0,180,70)
		}



		pf.setPaper(paper);

		pjob.setJobName(f.getName());

		PrintService ps = getPrintService();
		if (ps != null) {
			try {
				// System.out.println("Print Service = " + ps.getName());
				// JOptionPane.showMessageDialog(null,
				// "Print Service = " + ps.getName());
				pjob.setPrintService(ps);
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				// System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null,
						"Грешка с принтера свързан с тази улуга");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Няма наличен принтер свързан с тази улуга");
			// System.out.println("Няма наличен принтер свързан с тази улуга");
			return;
		}
		Book book = new Book();
		book.append(pages, pf, pdfFile.getNumPages());
		pjob.setPageable(book);



		try {
			pjob.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Грешка с принтера свързан с тази улуга");
			return;
		}

	}


	private static PrintService getPrintService() {
		PrintService ps[] = PrinterJob.lookupPrintServices();
		if (ps != null) {
			PrintService printService = null;
			for (PrintService p : ps) {
				if(p.getName().toLowerCase().contains("Brother QL-500".toLowerCase())) {//(MainPanel.LABEL_PRINTER1.toLowerCase())) {
					// System.out.println("Contains LPQ58");
					// JOptionPane.showMessageDialog(null, ps[i].getName());
					printService = p;
					return printService;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Не са налични принтер услуги");
			return null;
		}
		return null;
	}

	/*
	 * public void printBarcodes() {
	 * 
	 * File file = new File(target); if(file.exists()) { File[] files =
	 * file.listFiles(); for(File f : files) { if(f.getName().endsWith(".pdf"))
	 * { // System.out.println(f.getName()); printPDF(f.getName()); } } } else {
	 * System.out.println("file not found"); } }
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintBarcod pb = new PrintBarcod();
		 GenerateBarcod.generateBarcodAsPDF("1000000093018","Пожарпротект ЕООД", "1000000093018" +
		 ".pdf");
		 printPDF(MainPanel.BARCODE_PDF_PATH, "1000000093018.pdf",false);
		//getPrintService();
		// pb.printBarcodes();
	}

}
