package javaprinters.printsticker;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.print.PrintException;
import javax.print.PrintService;
import javax.swing.JOptionPane;

import exceptions.InOutException;
import log.IOErrorsWriter;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;

public class PrintSticker {

	/*
	 * public static void printImage(String image) throws IOException,
	 * javax.print.PrintException { PrintRequestAttributeSet pras = new
	 * HashPrintRequestAttributeSet(); pras.add(new Copies(1)); PrintService
	 * pss[] =
	 * PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.GIF, pras);
	 * if (pss.length == 0) throw new
	 * RuntimeException("No printer services available."); PrintService ps =
	 * null; for(PrintService p : pss) {
	 * if(p.getName().contains("Samsung M267")) { ps = p; break; } }
	 * System.out.println("Printing to " + ps); DocPrintJob job =
	 * ps.createPrintJob(); FileInputStream fin = new FileInputStream(image);
	 * Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.GIF, null);
	 * job.print(doc, pras); fin.close(); }
	 */
	public static void printPDF(String target, String pdf) {

		File f = new File(target + pdf);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			InOutException.showErrorMessage(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
		}
		FileChannel fc = fis.getChannel();
		ByteBuffer bb = null;
		try {
			bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			InOutException.showErrorMessage(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
		}
		PDFFile pdfFile = null;
		try {
			pdfFile = new PDFFile(bb);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			InOutException.showErrorMessage(e1);
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

		// configure where to positioning image on label
		double margin = 23;
		paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2,
				paper.getHeight() - margin * 2); // paper.setImageableArea(15,
													// 290, 400, 60);

		pf.setPaper(paper);

		pjob.setJobName(f.getName());

		PrintService ps = getPrintService();

		if (ps != null) {
			try {
				pjob.setPrintService(ps);
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Няма наличен принтер свързан с тази улуга");
			return;
		}
		Book book = new Book();
		book.append(pages, pf, pdfFile.getNumPages());
		pjob.setPageable(book);

		System.out.println("pdf pages = " + pdfFile.getNumPages());
		try {
			pjob.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static PrintService getPrintService() {
		PrintService ps[] = PrinterJob.lookupPrintServices();
		PrintService printService = null;
		for (int i = 0; i < ps.length; i++) {
			if (ps[i].getName().contains("Citizen CL")) {
				printService = ps[i];
				return printService;
			}
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

	public static void main(String[] args) throws IOException, PrintException {
		// TODO Auto-generated method stub
		PrintSticker pb = new PrintSticker();
		// printImage("C:\\Program1\\Images\\test.jpg");
		String num = "1000000229080";
		// GenerateBarcod.generateBarcodOnStickerAsPDF(num, num + ".pdf",
		// GetDate.getYearAfterYears(1));
		// printPDF(MainPanel.BARCODE_PDF_PATH,num + ".pdf");

	}

}
