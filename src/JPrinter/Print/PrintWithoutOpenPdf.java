package JPrinter.Print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
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

import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

public class PrintWithoutOpenPdf {
	public static void print(String target, String pdf) {
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
		PDFPrintPages pages = new PDFPrintPages(pdfFile);

		// Create Print Job
		PrinterJob pjob = PrinterJob.getPrinterJob();

		pjob.setCopies(2);

		PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

		Paper paper = new Paper();

		// This is to fix an error in PDF-Renderer
		// View
		// http://juixe.com/techknow/index.php/2008/01/17/print-a-pdf-document-in-java/
		// for details
		// Printing a PDF is also possible by sending the bytes directly to the
		// printer, but
		// the printer would have to support it.
		paper.setImageableArea(30, 5, paper.getWidth(), paper.getHeight());

		pf.setPaper(paper);

		pjob.setJobName(f.getName());

		Book book = new Book();
		book.append(pages, pf, pdfFile.getNumPages());
		pjob.setPageable(book);

		try {
			/*
			 * PrintService[] printServices =
			 * PrintServiceLookup.lookupPrintServices(null, null);
			 * System.out.println("Number of print services: " +
			 * printServices.length);
			 * 
			 * for (PrintService printer : printServices) {
			 * System.out.println("Printer: " + printer.getName());
			 * 
			 * }
			 */

			if (pjob.printDialog()) {
				pjob.print();
			}
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			e.printStackTrace();
		}

	}

	static FileInputStream fis;

	public static boolean printWithoutDialog(String target, String pdf,
			PrintService printService, int copies) {
		File f = new File(target + pdf);
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			closeInputStream();
			InOutException.showIOException(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
			return false;
		}
		FileChannel fc = fis.getChannel();
		ByteBuffer bb = null;
		try {
			bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			closeInputStream();
			InOutException.showIOException(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
			return false;
		}
		PDFFile pdfFile = null;
		try {
			pdfFile = new PDFFile(bb);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			closeInputStream();
			InOutException.showIOException(e1);
			IOErrorsWriter.writeIO(e1.toString());
			e1.printStackTrace();
			return false;
		} // Create PDF Print Page
		PDFPrintPages pages = new PDFPrintPages(pdfFile);

		// Create Print Job
		PrinterJob pjob = PrinterJob.getPrinterJob();
		// !!!! HOW COPIES TO BE PRINTED
		pjob.setCopies(copies);
		// //

		PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

		Paper paper = new Paper();
		paper.setSize(8.3 * 72, 11.7 * 72);// without set paper size work on old
											// printers !!!1
		paper.setImageableArea(0.0 * 72, 0.0 * 72, 7.5 * 72, 10.5 * 72);
		// This is to fix an error in PDF-Renderer
		// View
		// http://juixe.com/techknow/index.php/2008/01/17/print-a-pdf-document-in-java/
		// for details
		// Printing a PDF is also possible by sending the bytes directly to the
		// printer, but
		// the printer would have to support it.

		paper.setImageableArea(25, 5, paper.getWidth() - 20, paper.getHeight());

		pf.setPaper(paper);
		pf.setOrientation(PageFormat.PORTRAIT);

		pjob.setJobName(f.getName());

		Book book = new Book();
		book.append(pages, pf, pdfFile.getNumPages());
		pjob.setPageable(book);

		try {

			pjob.setPrintService(printService);
			pjob.print();

		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			closeInputStream();
			PDFException.showPDFException(e);
			e.printStackTrace();
			return false;
		}
		closeInputStream();
		return true;
	}

	private static void closeInputStream() {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class PDFPrintPages implements Printable {
	private PDFFile file;

	PDFPrintPages(PDFFile file) {
		this.file = file;
	}

	@Override
	public int print(Graphics g, PageFormat format, int index)
			throws PrinterException {
		int pagenum = index + 1;

		// don't bother if the page number is out of range.
		if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
			// fit the PDFPage into the printing area
			Graphics2D g2 = (Graphics2D) g;
			PDFPage page = file.getPage(pagenum);
			double pwidth = format.getImageableWidth();
			double pheight = format.getImageableHeight();

			double aspect = page.getAspectRatio();
			double paperaspect = pwidth / pheight;

			Rectangle imgbounds;

			if (aspect > paperaspect) {
				// paper is too tall / pdfpage is too wide
				int height = (int) (pwidth / aspect);
				imgbounds = new Rectangle(
						(int) format.getImageableX(),
						(int) (format.getImageableY() + ((pheight - height) / 2)),
						(int) pwidth, height);
			} else {
				// paper is too wide / pdfpage is too tall
				int width = (int) (pheight * aspect);
				imgbounds = new Rectangle(
						(int) (format.getImageableX() + ((pwidth - width) / 2)),
						(int) format.getImageableY(), width, (int) pheight);
			}

			// render the page
			PDFRenderer pgs = new PDFRenderer(page, g2, imgbounds, null, null);
			try {
				page.waitForFinish();
				pgs.run();
			} catch (InterruptedException ie) {
			}

			return PAGE_EXISTS;
		} else {
			return NO_SUCH_PAGE;
		}
	}

	public static void main(String args[]) throws PrintException, IOException {
		// PrintWithoutOpenPdf.print(MainPanel.SERVICE_ORDER_PDF_PATH,"test.pdf");

	}
}